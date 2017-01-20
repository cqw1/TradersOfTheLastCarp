package com.totlc.levels;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.Array;
import com.totlc.Actors.TotlcObject;
import com.totlc.Actors.Player;
import com.totlc.Actors.weapons.Whip;
import com.totlc.Directionality;
import com.totlc.audio.MusicPlayer;

import java.util.HashSet;

public abstract class ALevel extends Stage {

    private Player player;
    private MusicPlayer musicPlayer;
    private AssetManager assetManager;
    private Whip whip;

    public ALevel(Player player, AssetManager assetManager){
        setPlayer(player);
        addActor(player);
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
                if (!(b instanceof TotlcObject) || a == b) { // || toBeRemoved.contains(b)
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
}
