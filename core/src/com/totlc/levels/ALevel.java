package com.totlc.levels;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.Array;
import com.totlc.Actors.TotlcObject;
import com.totlc.Actors.Player;
import com.totlc.Actors.UI.Bar;
import com.totlc.Actors.UI.Inventory;
import com.totlc.Actors.UI.LevelInfo;
import com.totlc.Actors.UI.LifeGauge;
import com.totlc.Actors.items.Health;
import com.totlc.Actors.terrain.*;
import com.totlc.Actors.tileset.BasicTileSet;
import com.totlc.AssetList;
import com.totlc.Directionality;
import com.totlc.TradersOfTheLastCarp;
import com.totlc.audio.MusicPlayer;
import com.totlc.levels.ObjectiveVerifier.Objectives;

import java.util.HashSet;

public abstract class ALevel extends Stage {

    private Player player;
    private MusicPlayer musicPlayer;
    private AssetManager assetManager;

    private TextureRegion objIcon;
    private NextStage nextStage;
    private ALevel nextLevel;
    private Vector2 playerStartPosition = new Vector2(DEFAULT_WALLSIZE + 10, TradersOfTheLastCarp.CONFIG_HEIGHT / 2 - 50);

    // Level information strings.
    private String nameString, infoString;

    private ObjectiveVerifier.Objectives objective;

    private long startTime;

    private long timeLimit = 0;

    private float wallSize = DEFAULT_WALLSIZE;
    public static float DEFAULT_WALLSIZE = 50;

    HashSet<Actor> toBeRemoved = new HashSet<Actor>();

    public ALevel() {}

    public ALevel(Player player, AssetManager assetManager) {
        this.player = player;
        this.assetManager = assetManager;
        setMusicPlayer(new MusicPlayer());
    }

//    public ALevel(Player player, AssetManager assetManager, NextStage nextStage, ALevel nextLevel, ObjectiveVerifier.Objectives objective) {
    public ALevel(Player player, AssetManager assetManager, ObjectiveVerifier.Objectives objective) {
        this.player = player;
        this.nextStage = new NextStage(assetManager, ALevel.DEFAULT_WALLSIZE, player.getHeight());
        this.assetManager = assetManager;
        this.objective = objective;
        this.startTime = System.currentTimeMillis();

        BasicTileSet bts = new BasicTileSet(getAssetManager());
        addActor(bts);

        TextureAtlas atlas = assetManager.get(AssetList.ICON_PACK.toString());
        switch (getObjective().getID()){
            case 0:
                // Survive.
                objIcon = atlas.findRegion("time_icon");
                setInfoString("SURVIVE FOR");
                break;
            case 1:
                // Unlock.
                objIcon = atlas.findRegion("lock_icon");
                setInfoString("UNLOCK DOOR");
                break;
            case 2:
                // Destroy.
                objIcon = atlas.findRegion("skull_icon");
                setInfoString("DEFEAT ENEMIES");
                break;
        }
        objIcon.getTexture().setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
        setMusicPlayer(new MusicPlayer());
    }

    public abstract void initLevel(Player player);

    public void endInit() {
        initWalls();

        player.moveAbs(playerStartPosition.x, playerStartPosition.y);
        addActor(player);
//        addActor(player.getWeapon());
        getPlayer().setZIndex(20);
        initUI();
    }

    @Override
    public void act(float delta) {
        System.out.println("ALevel.act");
        //First let actors update themselves
        for (Actor a: getActors()) {
            System.out.println(a);
            a.act(delta);
        }

        if (ObjectiveVerifier.verifyDone(this) <= 0.0f) {
            nextStage.unlock();
        }

        if (Intersector.overlapConvexPolygons(player.getHitBox(), nextStage.getHitBox())) {
            initNextLevel();
            dispose();
            TradersOfTheLastCarp.level = nextLevel;
            return;
        }

        //Now check for collisions
        Array<Actor> allActors = getActors();
        toBeRemoved.clear();
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
            this.addActor(player.getWeapon());
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
        int xOffset = 32;
        int yOffset = 32;
        // Add health bar, inventory bar, and level information bar.
        LifeGauge hpBar = new LifeGauge(getAssetManager(), player, 0, TradersOfTheLastCarp.CONFIG_HEIGHT);
        Inventory inventory = new Inventory(getAssetManager(), player, 0, TradersOfTheLastCarp.CONFIG_HEIGHT);
        Bar bar0 = new Bar(getAssetManager(), 0, TradersOfTheLastCarp.CONFIG_HEIGHT, (int)(hpBar.getWidth() + inventory.getWidth() + 32), 64);
        LevelInfo info = new LevelInfo(getAssetManager(), this, TradersOfTheLastCarp.CONFIG_WIDTH, TradersOfTheLastCarp.CONFIG_HEIGHT);

        //Move them to their correct position
        bar0.moveBy(-xOffset, -(bar0.getHeight() + yOffset));
        hpBar.moveBy(inventory.getWidth() - xOffset * 0.3f, -(hpBar.getHeight() + yOffset * 0.85f));
        inventory.moveBy(0, -(inventory.getHeight() + yOffset * 0.6f));
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

    public Vector2 getPlayerStartPosition() {
        return playerStartPosition;
    }

    public void setPlayerStartPosition(Vector2 playerStartPosition) {
        this.playerStartPosition = playerStartPosition;
    }

    public AssetManager getAssetManager() {
        return assetManager;
    }

    public void setAssetManager(AssetManager assetManager) {
        this.assetManager = assetManager;
    }

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
                return " " + Math.max(Math.ceil(ObjectiveVerifier.verifyDone(this) / 100) / 10, 0);
            case 1:
                // Unlock.
                return "LOCKED/UNLOCKED";
            case 2:
                // Destroy.
                return "x " + (int)ObjectiveVerifier.verifyDone(this);
            default:
                return "???";
        }
    }

    public Objectives getObjective() {
        return objective;
    }

    public void setObjective(Objectives objective) {
        this.objective = objective;
    }

    public long getStartTime() {
        return startTime;
    }

    public void setStartTime(long startTime) {
        this.startTime = startTime;
    }

    public long getTimeLimit() {
        return timeLimit;
    }

    public void setTimeLimit(long timeLimit) {
        this.timeLimit = timeLimit;
    }

    public float getWallSize() {
        return wallSize;
    }

    public void setWallSize(float wallSize) {
        this.wallSize = wallSize;
    }

    public TextureRegion getObjIcon() {
        return objIcon;
    }

    public void setObjIcon(TextureRegion objIcon) {
        this.objIcon = objIcon;
    }

    public void initWalls() {
        addActor(nextStage);
        addActor(nextStage.getPhysicalBlock());

        LeftWall lw = new LeftWall(assetManager, new Rectangle(0, 0, wallSize, TradersOfTheLastCarp.CONFIG_HEIGHT));
        // two separate walls for the exit
        RightWall rwBot = new RightWall(assetManager, new Rectangle(TradersOfTheLastCarp.CONFIG_WIDTH - wallSize, 0, wallSize, nextStage.getY()));
        RightWall rwTop = new RightWall(assetManager, new Rectangle(TradersOfTheLastCarp.CONFIG_WIDTH - wallSize, nextStage.getY() + nextStage.getHeight(), wallSize, TradersOfTheLastCarp.CONFIG_HEIGHT - nextStage.getY() + nextStage.getHeight()));
        TopWall tw = new TopWall(assetManager, new Rectangle(0, TradersOfTheLastCarp.CONFIG_HEIGHT - wallSize, TradersOfTheLastCarp.CONFIG_WIDTH, wallSize));
        BottomWall bw = new BottomWall(assetManager, new Rectangle(0, 0, TradersOfTheLastCarp.CONFIG_WIDTH, wallSize));

        // add actors
        addActor(lw);
        addActor(rwBot);
        addActor(rwTop);
        addActor(tw);
        addActor(bw);

        bw.setZIndex(2);
        tw.setZIndex(2);
        rwTop.setZIndex(1);
        rwBot.setZIndex(1);
        lw.setZIndex(1);
    }

    public void initNextLevel() {
        musicPlayer.stop();

        int index = TradersOfTheLastCarp.LEVEL_NAME.indexOf(this.getClass().getName());
        System.out.println(index);
        nextLevel = TradersOfTheLastCarp.LEVEL_OBJ.get(index + 1);
        nextLevel.initLevel(player);
    }
}
