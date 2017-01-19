package com.totlc.Actors.projectiles;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.totlc.Actors.TotlcObject;

import java.awt.geom.Point2D;

public class ProjectileFactory {

    public static Actor createProjectile(ProjEnum type, Point2D vel, AssetManager assetManager, float x, float y) {
        TotlcObject returnMe;

        //Handle the type
        if (type.equals(ProjEnum.ARROW)) {
            returnMe = new Arrow(assetManager, x, y);
        } else {
            return null;
        }

        //Handle direction
        returnMe.setVel(vel);

        return returnMe;
    }
}
