package com.totlc.levels;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.Array;
import com.totlc.Actors.TotlcObject;
import com.totlc.Actors.Player;
import com.totlc.Actors.UI.Bar;
import com.totlc.Actors.UI.Inventory;
import com.totlc.Actors.UI.LevelInfo;
import com.totlc.Actors.UI.LifeGauge;
import com.totlc.Actors.weapons.Whip;
import com.totlc.Directionality;
import com.totlc.TradersOfTheLastCarp;
import com.totlc.audio.MusicPlayer;

import java.util.HashSet;

public abstract class ALevel extends Stage {

    private Player player;
    private MusicPlayer musicPlayer;
    private AssetManager assetManager;
    private Whip whip;

    // Level information strings.
    private String nameString, infoString;

    // Level objective types.
    public enum objectives {
        SURVIVE(0),
        UNLOCK(1),
        DESTROY(2);

        private int id;

        objectives(int id) {
            this.id = id;
        }

        public int getID() {
            return this.id;
        }
    }

    private objectives objective;

    public ALevel(Player player, AssetManager assetManager){
        setPlayer(player);
        this.assetManager = assetManager;

        whip = new Whip(assetManager, player);

        setMusicPlayer(new MusicPlayer());
    }

    @Override
    public void act(float delta) {
        //First let actors update themselves
        for (Actor a: getActors()) {
            a.act(delta);
        }

        //Now check for collisions
        Array<Actor> allActors = getActors();
        HashSet<Actor> toBeRemoved = new HashSet<Actor>();
        for (int aCounter = 0; aCounter < allActors.size; aCounter++) {
            //Ignore if not an interactable object or being removed
            Actor a = allActors.get(aCounter);
//            System.out.println(a.getClass());
            if (!(a instanceof TotlcObject) || toBeRemoved.contains(a)) {
                continue;
            }

            TotlcObject objA = (TotlcObject) a;
            for (int bCounter = aCounter; bCounter < allActors.size; bCounter++) {
                //Ignore again, except add that we ignore references to ourselves, or objects being removed
                Actor b = allActors.get(bCounter);
                if (!(b instanceof TotlcObject) || a == b || toBeRemoved.contains(b)) { // || toBeRemoved.contains(b)
                    continue;
                }

                TotlcObject objB = (TotlcObject) b;
                if (Intersector.overlapConvexPolygons(objA.getHitBox(), objB.getHitBox())) {
                    if (objA.collidesWith(objB)) {
                        toBeRemoved.add(objA);
                    }
                    if (objB.collidesWith(objA)) {
                        toBeRemoved.add(objB);
                    }
                } else {
                    objA.endCollidesWith(objB);
                    objB.endCollidesWith(objA);
                }
            }
        }

        //Remove any actors that need to disappear
        for (Actor beingRemoved: toBeRemoved) {
            beingRemoved.remove();
        }
    }

//    public void draw() {
//        if (assetManager.update()) {
//            getBatch().begin();
//            getBatch().draw((Texture)assetManager.get(AssetList.DEFAULT_TILESET.toString()), 0f, 0f, (float) TradersOfTheLastCarp.CONFIG_WIDTH, (float) TradersOfTheLastCarp.CONFIG_HEIGHT);
//            getBatch().end();
//        }
//    }

    @Override
    public boolean keyDown(int keycode) {
        if (keycode == Input.Keys.LEFT) {
            player.setMovingLeft(true);
            player.setIsFacing(Directionality.LEFT);
            return true;
        }
        if (keycode == Input.Keys.RIGHT) {
            player.setMovingRight(true);
            player.setIsFacing(Directionality.RIGHT);
            return true;
        }
        if (keycode == Input.Keys.UP) {
            player.setMovingUp(true);
            player.setIsFacing(Directionality.UP);
            return true;
        }
        if (keycode == Input.Keys.DOWN) {
            player.setMovingDown(true);
            player.setIsFacing(Directionality.DOWN);
            return true;
        }

        if (keycode == Input.Keys.SPACE) {
            this.addActor(whip);
            player.setWeapon(whip);
            player.setAttacking(true);
            return true;
        }

        return false;
    }

    @Override
    public boolean keyUp(int keycode) {
        if (keycode == Input.Keys.LEFT){
            player.setMovingLeft(false);
            return true;
        }
        if (keycode == Input.Keys.RIGHT){
            player.setMovingRight(false);
            return true;
        }
        if (keycode == Input.Keys.UP){
            player.setMovingUp(false);
            return true;
        }
        if (keycode == Input.Keys.DOWN){
            player.setMovingDown(false);
            return true;
        }
        return false;
    }

    public void initUI(){
        int xOffset = 16;
        int yOffset = 16;
        // Add health bar, inventory bar, and level information bar.
        LifeGauge hpBar = new LifeGauge(player, 0, TradersOfTheLastCarp.CONFIG_HEIGHT);
        Inventory inventory = new Inventory(player, 0, TradersOfTheLastCarp.CONFIG_HEIGHT);
        Bar bar0 = new Bar(0, TradersOfTheLastCarp.CONFIG_HEIGHT, (int)(hpBar.getWidth() + inventory.getWidth() + 16), 64);
        LevelInfo info = new LevelInfo(this, TradersOfTheLastCarp.CONFIG_WIDTH, TradersOfTheLastCarp.CONFIG_HEIGHT);

        bar0.moveBy(-xOffset, -(bar0.getHeight() + yOffset));
        hpBar.moveBy(inventory.getWidth(), -(hpBar.getHeight() + 3));
        inventory.moveBy(0, -(inventory.getHeight() - 3));
        info.moveBy(-(info.getWidth() - xOffset), -(info.getHeight() + yOffset));

        addActor(bar0);
        addActor(hpBar);
        addActor(inventory);
        addActor(info);
    }

    @Override
    public boolean keyTyped(char key) {
        return false;
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        return false;
    }

    @Override
    public boolean mouseMoved(int screenX, int screenY) {
        return false;
    }

    @Override
    public boolean scrolled(int amount) {
        return false;
    }

    //Getters and setters
    public void setPlayer(Player p) { player = p; }
    public Player getPlayer() {
        return player;
    }

    public void setMusicPlayer(MusicPlayer mp) { musicPlayer = mp; }

    public void playSong(String filename) { musicPlayer.setSong(filename); musicPlayer.play(); }

    public String getNameString() {
        return nameString;
    }

    public void setNameString(String nameString) {
        this.nameString = nameString;
    }

    public String getInfoString() {
        return infoString;
    }

    public void setInfoString(String infoString) {
        this.infoString = infoString;
    }

    public String getObjectiveInfo(){
        switch (getObjective().getID()){
            case 0:
                // Survive.
                return "TIME REMAINING";
            case 1:
                // Unlock.
                return "LOCKED/UNLOCKED";
            case 2:
                // Destroy.
                return "ENEMIES LEFT";
            default:
                return "???";
        }
    }

    public objectives getObjective() {
        return objective;
    }

    public void setObjective(objectives objective) {
        this.objective = objective;
    }
}
