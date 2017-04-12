package com.totlc.Actors.enemies.movement;

import com.totlc.Actors.enemies.AEnemy;
import com.totlc.Actors.Character;

import java.awt.geom.Point2D;

public class IntervalMovement extends AMovement {

    private boolean skitter = false;
    private long movementTime = 0L;
    private int skitterPeriod = 1000;
    private int waitPeriod = 1000;
    private Point2D targetVector;

    public IntervalMovement(Character target) {
        super(target);
        this.waitPeriod = this.waitPeriod + (int)(Math.random() * 1000);
    }

    public IntervalMovement(Character target, int skitterPeriod, int waitPeriod) {
        super(target);
        this.waitPeriod = waitPeriod;
        this.skitterPeriod = skitterPeriod;
    }

    @Override
    public void specializedMove(AEnemy self, float deltaTime) {
        Point2D newAcc = self.getAcc();
        if (skitter) {
            if (System.currentTimeMillis() > (movementTime + skitterPeriod)) {
                skitter = false;
                movementTime = System.currentTimeMillis();
            }

            newAcc.setLocation(targetVector.getX() * self.getSpeed(), targetVector.getY() * self.getSpeed());
            self.setAcc(newAcc);
        } else {
            if (System.currentTimeMillis() > (movementTime + waitPeriod)) {
                skitter = true;
                movementTime = System.currentTimeMillis();
                this.targetVector = getTargetVector(self);
            }

            newAcc.setLocation(0, 0);
            self.setAcc(newAcc);
        }
        self.updateVelocity();
        self.moveUnit(deltaTime);
    }

    public boolean isMoving() {return skitter;}
}
