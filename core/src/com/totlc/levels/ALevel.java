package com.totlc.levels;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.Array;
import com.totlc.Actors.totlcObject;
import com.totlc.Actors.Player;
import com.totlc.audio.MusicPlayer;

import java.util.HashSet;

public abstract class ALevel extends Stage {

    private Player player;
    private MusicPlayer musicPlayer;

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
            if (!(a instanceof totlcObject) || toBeRemoved.contains(a)) {
                continue;
            }

            totlcObject objA = (totlcObject) a;
            for (int bCounter = aCounter; bCounter < allActors.size; bCounter++) {
                //Ignore again, except add that we ignore references to ourselves, or objects being removed
                Actor b = allActors.get(bCounter);
                if (!(b instanceof totlcObject) || a == b || toBeRemoved.contains(b)) {
                    continue;
                }

                totlcObject objB = (totlcObject) b;
                if (Intersector.overlaps(objA.getHitBox(), objB.getHitBox())) {
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
        if (keycode == Input.Keys.LEFT){
            player.setMovingLeft(true);
            return true;
        }
        if (keycode == Input.Keys.RIGHT){
            player.setMovingRight(true);
            return true;
        }
        if (keycode == Input.Keys.UP){
            player.setMovingUp(true);
            return true;
        }
        if (keycode == Input.Keys.DOWN){
            player.setMovingDown(true);
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

    public void setMusicPlayer(MusicPlayer mp) { musicPlayer = mp; }

    public void playSong(String filename) { musicPlayer.setSong(filename); musicPlayer.play(); }
}
