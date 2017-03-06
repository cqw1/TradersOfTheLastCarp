package com.totlc.levels;

import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Polygon;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.totlc.Actors.enemies.AEnemy;

import java.awt.geom.Point2D;

public class ObjectiveVerifier {

    private static float unlockThreshold = 150;

    public enum Objectives {
        SURVIVE(0),
        UNLOCK(1),
        DESTROY(2);

        private int id;

        Objectives(int id) {
            this.id = id;
        }

        public int getID() {
            return this.id;
        }
    }

    public static float verifyDone(ALevel currentLevel) {
        float returnValue = 0.0f;

        switch(currentLevel.getObjective().getID()) {
            case 0:
                //SURVIVE
                returnValue = (float)currentLevel.getTimeLimit() - (System.currentTimeMillis() - currentLevel.getStartTime());
                break;
            case 1:
                //UNLOCK
                Point2D doorCenter = currentLevel.getNextStage().physicalBlock.getCenter();
                Point2D playerCenter = currentLevel.getPlayer().getCenter();
                if (currentLevel.getPlayer().hasKey() &&
                        Math.hypot(doorCenter.getX() - playerCenter.getX(), doorCenter.getY() - playerCenter.getY()) < unlockThreshold) {
                    currentLevel.getPlayer().removeKey();
                } else {
                    returnValue++;
                }
                break;
            case 2:
                //DESTROY
                for (Actor actor: currentLevel.getActors()) {
                    if (actor instanceof AEnemy) {
                        returnValue++;
                    }
                }
                break;
        }

        return returnValue;
    }
}
