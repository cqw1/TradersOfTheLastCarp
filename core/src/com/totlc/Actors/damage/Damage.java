package com.totlc.Actors.damage;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.math.Rectangle;
import com.totlc.Actors.TotlcObject;

public abstract class Damage extends TotlcObject {

    private float scaleFactor = 1.0f;
    private long expirationTime = 0;
    private int attack;

    // Damage type. 0 - Damages all; 1 - Damages Player; 2 - Damages enemies.
    private int damageType;

    public Damage(AssetManager assetManager, Rectangle r, int attack, int damageType) {
        super(assetManager, r);
        this.attack = attack;
        this.damageType = damageType;
    }

    public void act(float delta) {
        if (expirationTime != 0 && System.currentTimeMillis() > expirationTime) {
            remove();
        }
    }

    public int getAttack() {
        return attack;
    }

    public void setAttack(int attack) {
        this.attack = attack;
    }

    public int getDamageType() {
        return damageType;
    }

    public void setDamageType(int damageType) {
        this.damageType = damageType;
    }

    public float getScaleFactor() {
        return scaleFactor;
    }

    public void setScaleFactor(float scaleFactor) {
        this.scaleFactor = scaleFactor;
    }

    public long getExpirationTime() {
        return expirationTime;
    }

    public void setExpirationTime(long expirationTime) {
        this.expirationTime = expirationTime;
    }
}
