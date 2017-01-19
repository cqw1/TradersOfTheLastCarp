package com.totlc.Actors.projectiles;

import com.badlogic.gdx.assets.AssetManager;
import com.totlc.Actors.totlcObject;

public abstract class Projectile extends totlcObject {

    private int attack = 1;

    private float scaleFactor = 1.0f;

    public Projectile(AssetManager assetManager, float x, float y) {
        super(assetManager, x, y);
    }

    public int getAttack() {
        return attack;
    }

    public void setAttack(int attack) {
        this.attack = attack;
    }

    public float getVelocityAngle(){
        double angle = Math.atan2(getVel().getY(), getVel().getX());
        return (float)Math.toDegrees(angle);
    }
    public float getScaleFactor() {
        return scaleFactor;
    }

    public void setScaleFactor(float scaleFactor) {
        this.scaleFactor = scaleFactor;
    }
}
