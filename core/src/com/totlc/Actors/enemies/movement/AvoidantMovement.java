package com.totlc.Actors.enemies.movement;

import com.totlc.Actors.Character;
import com.totlc.Actors.enemies.AEnemy;

import java.awt.geom.Point2D;

public class AvoidantMovement extends AMovement{

    private double fleeRange = 300;
    private boolean isMoving;

    public AvoidantMovement(Character player) {
        super(player);
        this.isMoving = false;
    }

    @Override
    public void specializedMove(AEnemy self, float deltaTime) {
        if (new Point2D.Double(getTarget().getX(), getTarget().getY()).distance(new Point2D.Double(self.getX(), self.getY())) < fleeRange){
            // Flee.
            this.isMoving = true;
            Point2D newAcc = self.getAcc();
            Point2D targetVector = getTargetVector(self);
            newAcc.setLocation(-targetVector.getX() * self.getSpeed(), -targetVector.getY() * self.getSpeed());

            self.setAcc(newAcc);
            self.updateVelocity();
            self.moveUnit(deltaTime);
        } else{
            this.isMoving = false;
        }
    }
    @Override
    public boolean isMoving() {
        return this.isMoving;
    }
}
