package com.totlc.levels;

import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.totlc.Actors.enemies.AEnemy;

public class ObjectiveVerifier {

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
                return (float)currentLevel.getTimeLimit() - (System.currentTimeMillis() - currentLevel.getStartTime());
            case 1:
                //UNLOCK
                if (!(//currentLevel.getPlayer().hasKey() &&
                        Intersector.overlapConvexPolygons(currentLevel.getNextStage().physicalBlock.getHitBox(),
                                currentLevel.getPlayer().getHitBox()))) {
                    //currentLevel.getPlayer().removeKey();
                    returnValue++;
                }
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
