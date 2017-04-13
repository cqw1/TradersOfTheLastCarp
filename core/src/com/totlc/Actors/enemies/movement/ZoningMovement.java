package com.totlc.Actors.enemies.movement;

import com.totlc.Actors.Character;
import com.totlc.Actors.enemies.AEnemy;

import java.awt.geom.Point2D;

public class ZoningMovement extends AMovement {

    private static float zoneDistance = 200;

    public ZoningMovement(Character target) {
        super(target);
    }

    @Override
    public void specializedMove(AEnemy self, float deltaTime) {
        Point2D newAcc = self.getAcc();
        Point2D targetVector = predictDestination(self, getTarget(), deltaTime);

        float speed;

        if(new Point2D.Double(getTarget().getX(), getTarget().getY()).distance(new Point2D.Double(self.getX(), self.getY())) > zoneDistance) {
            speed = self.getSpeed();
        } else{
            speed = 10;
        }
        if (getTarget().isInMotion()){
            if(getTarget().isMovingLeft() || getTarget().isMovingRight() ) {
                newAcc.setLocation(targetVector.getX() * speed * 1.5, targetVector.getY() * speed * 2);
           } else {
                newAcc.setLocation(targetVector.getX() * speed * 2, targetVector.getY() * speed * 1.5);
            }
        } else{
            newAcc.setLocation(targetVector.getX() * speed * 0.2f, targetVector.getY() * speed * 0.2f);
        }
        self.updateVelocity();
        self.moveUnit(deltaTime);
    }

    private Point2D predictDestination(Character self, Character target, float deltaTime){
        Point2D predictedDestination;
        if(getTarget().isMovingLeft()) {
            predictedDestination = new Point2D.Double(target.getX() - zoneDistance, target.getY() + getTarget().getMaxVel() * deltaTime * 3);
        } else if(getTarget().isMovingRight()){
            predictedDestination = new Point2D.Double(target.getX() + zoneDistance, target.getY() + getTarget().getMaxVel() * deltaTime * 3);
        } else if(getTarget().isMovingUp()){
            predictedDestination = new Point2D.Double(target.getX() + getTarget().getMaxVel() * deltaTime * 3, target.getY() + zoneDistance);
        } else {
            predictedDestination = new Point2D.Double(target.getX() + getTarget().getMaxVel() * deltaTime * 3, target.getY() - zoneDistance);
        }

        Point2D targetVector = new Point2D.Double(predictedDestination.getX() - self.getX(), predictedDestination.getY() - self.getY());
        float n = (float) Math.sqrt(Math.pow(targetVector.getX(), 2) + Math.pow(targetVector.getY(), 2));
        if (n != 0) {
            targetVector.setLocation(targetVector.getX() / n, targetVector.getY() / n);
            return targetVector;
        } else{
            return new Point2D.Double(0, 0);
        }
    }
}
