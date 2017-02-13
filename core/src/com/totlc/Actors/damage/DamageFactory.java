package com.totlc.Actors.damage;

import com.badlogic.gdx.assets.AssetManager;

import java.awt.geom.Point2D;

public class DamageFactory {

    public static Damage createDamage(DamageEnum type, Point2D vel, AssetManager assetManager, float x, float y, int damageType) {
        Damage returnMe;

        //Handle the type
        if (type.equals(DamageEnum.ARROW)) {
            returnMe = new Arrow(assetManager, x, y, damageType);
        } else if(type.equals(DamageEnum.STAR_SHOT)) {
            returnMe = new StarShot(assetManager, x, y, damageType);
        } else if(type.equals(DamageEnum.LIGHTNING_PATCH)) {
            returnMe = new LightningPatch(assetManager, x, y, 128, 128, damageType);
        } else {
            return null;
        }

        //Handle direction
        returnMe.setVel(vel);
        returnMe.getHitBox().rotate(returnMe.getVelocityAngle());

        return returnMe;
    }
}
