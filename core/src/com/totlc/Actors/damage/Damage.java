package com.totlc.Actors.damage;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.math.Rectangle;
import com.totlc.Actors.TotlcObject;

public abstract class Damage extends TotlcObject {

    private float scaleFactor = 1.0f;

    private int attack;

    // Damage type. 0 - Damages all; 1 - Damages Player; 2 - Damages enemies.
    private int damageType;

    public Damage(AssetManager assetManager, Rectangle r) {
        super(assetManager, r);
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
}
