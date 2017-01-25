package com.totlc.Actors.projectiles;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.math.Rectangle;
import com.totlc.Actors.TotlcObject;

public abstract class Projectile extends TotlcObject {

    private int attack = 1;

    // Damage type. 0 - Damages all; 1 - Damages Player; 2 - Damages enemies.
    private int damageType;

    private float scaleFactor = 1.0f;

    public Projectile(AssetManager assetManager, Rectangle r) {
        super(assetManager, r);
    }

    public int getAttack() {
        return attack;
    }

    public int getDamageType() {
        return damageType;
    }

    public void setDamageType(int damageType) {
        this.damageType = damageType;
    }

    public void setAttack(int attack) {
        this.attack = attack;
    }

    public float getScaleFactor() {
        return scaleFactor;
    }

    public void setScaleFactor(float scaleFactor) {
        this.scaleFactor = scaleFactor;
    }

    public void calibrateHitBox() {

    }
}
