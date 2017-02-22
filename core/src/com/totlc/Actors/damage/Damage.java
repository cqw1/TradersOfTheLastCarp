package com.totlc.Actors.damage;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.totlc.Actors.Player;
import com.totlc.Actors.TotlcObject;
import com.totlc.Actors.enemies.AEnemy;

public abstract class Damage extends TotlcObject {

    private float scaleFactor = 1.0f;
    private long expirationTime = 0;
    private long timeToApplyDamage = 0;
    private int attack;

    // Damage type. 0 - Damages all; 1 - Damages Player; 2 - Damages enemies.
    private int damageType;
    private long damageInterval = 500;

    public Damage(AssetManager assetManager, Rectangle r, int attack, int damageType) {
        super(assetManager, r);
        this.attack = attack;
        this.damageType = damageType;
        timeToApplyDamage = System.currentTimeMillis();
    }

    public void act(float delta) {
        if (expirationTime != 0 && System.currentTimeMillis() > expirationTime) {
            remove();
        }
        if (System.currentTimeMillis() > timeToApplyDamage + damageInterval) {
            timeToApplyDamage = System.currentTimeMillis();
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

    public long getTimeToApplyDamage() {
        return timeToApplyDamage;
    }

    public void setTimeToApplyDamage(long timeToApplyDamage) {
        this.timeToApplyDamage = timeToApplyDamage;
    }

    public long getDamageInterval() {
        return damageInterval;
    }

    public void setDamageInterval(long damageInterval) {
        this.damageInterval = damageInterval;
    }
}
