package com.totlc.Actors;

import com.badlogic.gdx.assets.AssetManager;

public abstract class Character extends TotlcObject {

    /**
     * IMPORTANT: MAKE SURE TO SET THE HEALTH OF THE CREATED OBJECT
     */

    // Player health.
    private int hpMAX;
    private int hpCURRENT;
    private boolean attacking = false;

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

    public void setAttacking(boolean attacking) {
        this.attacking = attacking;
    }

    public boolean getAttacking() {
        return attacking;
    }


}
