package com.totlc.Actors;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.scenes.scene2d.Actor;

public abstract class Character extends totlcObject {

    // Player health.
    private int hpMAX;
    private int hpCURRENT;

    public Character(AssetManager assetManager, float x, float y) {
        super(assetManager, x, y);
    }

    abstract public void act(float deltaTime);

    public int getHpMax() {
        return hpMAX;
    }

    public void setHpMax(int hpMAX) {
        this.hpMAX = hpMAX;
    }

    public int getHpCurrent() {
        return hpCURRENT;
    }

    public void setHpCurrent(int hpCURRENT) {
        this.hpCURRENT = hpCURRENT;
    }

    public void takeDamage(int damage) { hpCURRENT = hpCURRENT - damage; }


}
