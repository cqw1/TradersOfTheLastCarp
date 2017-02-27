package com.totlc.Actors.enemies.movement;

import com.totlc.Actors.Character;
import com.totlc.Actors.enemies.AEnemy;

import java.awt.geom.Point2D;

/**
 * Simply moves in the direction of the player
 */
public class BasicMovement extends AMovement{

    public BasicMovement(Character target) {
        super(target);
    }

    @Override
    public void specializedMove(AEnemy self, float deltaTime) {
        Point2D newAcc = self.getAcc();
        Point2D targetVector = getTargetVector(self);
        newAcc.setLocation(targetVector.getX() * self.getSpeed(), targetVector.getY() * self.getSpeed());

        self.setAcc(newAcc);
        self.updateVelocity();
        self.moveUnit(deltaTime);
    }
}
