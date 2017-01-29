package com.totlc.levels;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.totlc.Actors.enemies.AEnemy;

public class ObjectiveVerifier {

    public enum objectives {
        SURVIVE(0),
        UNLOCK(1),
        DESTROY(2);

        private int id;

        objectives(int id) {
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
                break;
            case 1:
                //UNLOCK
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
