package com.totlc.Actors.damage;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.totlc.Actors.TotlcObject;

import java.awt.geom.Point2D;

public class ProjectileFactory {

    public static Damage createProjectile(ProjEnum type, Point2D vel, AssetManager assetManager, float x, float y, int damageType) {
        Damage returnMe;

        //Handle the type
        if (type.equals(ProjEnum.ARROW)) {
            returnMe = new Arrow(assetManager, x, y, damageType);
        } else if(type.equals(ProjEnum.STAR_SHOT)) {
            returnMe = new StarShot(assetManager, x, y, damageType);
        } else {
            return null;
        }

        //Handle direction
        returnMe.setVel(vel);
        returnMe.getHitBox().rotate(returnMe.getVelocityAngle());

        return returnMe;
    }
}
