package com.totlc.levels;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.totlc.Actors.Player;
import com.totlc.audio.MusicPlayer;

public abstract class ALevel extends Stage {

    private Player player;
    private MusicPlayer musicPlayer;

//    @Override
//    public void act(float delta) {
//        //First let actors update themselves
//        for (Actor a: getActors()) {
//            a.act(delta);
//        }
//
//        //Now check for collisions
//        for (Actor a: getActors()) {
//            for (Actor b: getActors()) {
//                if (Intersector.overlaps(a))
//            }
//        }
//    }

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
    public Player getPlayer() {
        return player;
    }

    public void setMusicPlayer(MusicPlayer mp) { musicPlayer = mp; }

    public void playSong(String filename) { musicPlayer.setSong(filename); musicPlayer.play(); }
}
