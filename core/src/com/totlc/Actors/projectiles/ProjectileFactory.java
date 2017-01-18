package com.totlc.Actors.projectiles;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.totlc.Actors.totlcObject;

/**
 * Created by Bao on 1/18/2017.
 */
public class ProjectileFactory {

    final static String ARROW = "ARROW";

    //Directional states
    final static String LEFT = "LEFT";
    final static String RIGHT = "RIGHT";
    final static String UP = "UP";
    final static String DOWN = "DOWN";

    public static Actor createProjectile(String type, String direction, float speed) {
        totlcObject returnMe;

        //Handle the type
        if (type.equals(ARROW)) {
            returnMe = new Arrow();
        } else {
            return null;
        }

        //Handle direction
        if (direction.equals(LEFT)) {
            returnMe.setMovingLeft(true);
        } else if (direction.equals(RIGHT)) {
            returnMe.setMovingRight(true);
        } else if (direction.equals(UP)) {
            returnMe.setMovingUp(true);
        } else if (direction.equals(DOWN)) {
            returnMe.setMovingDown(true);
        } else {
            return null;
        }

        returnMe.setSpeed(speed);

        return returnMe;
    }
}
