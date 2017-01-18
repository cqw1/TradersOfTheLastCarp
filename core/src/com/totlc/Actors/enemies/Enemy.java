package com.totlc.Actors.enemies;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.math.Rectangle;
import com.totlc.Actors.Character;

//Empty for now. All enemies will inherit from this one, to differentiate player and enemy?

public abstract class Enemy extends Character {
    // Player health.
    private int hpMAX;
    private int hpCURRENT;
    private int attack;

    public Enemy(AssetManager assetManager, int x, int y){
        setX(x);
        setY(y);
        setAssetManager(assetManager);
        setHitBox(new Rectangle(x, y, getWidth(), getHeight()));
    }

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

    public int getAttack() {
        return attack;
    }

    public void setAttack(int attack) {
        this.attack = attack;
    }
}
