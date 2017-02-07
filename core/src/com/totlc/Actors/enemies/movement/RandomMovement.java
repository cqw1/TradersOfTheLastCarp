package com.totlc.Actors.enemies.movement;

import com.badlogic.gdx.math.Vector2;
import com.totlc.Actors.Character;
import com.totlc.Actors.enemies.AEnemy;

import java.awt.geom.Point2D;

public class RandomMovement extends AMovement {

    private float angle;
    private int waitPeriod = 2000;
    private long movementTime = 0;

    public RandomMovement(Character player) {
        super(player);
        this.angle = 0;
    }

    @Override
    public void move(AEnemy self, float deltaTime) {
        Point2D newAcc = self.getAcc();
        Point2D targetVector = getTargetVector(self);
        if (System.currentTimeMillis() > (movementTime + waitPeriod)) {
            movementTime = System.currentTimeMillis();
            angle = (float) Math.random() * 360;
        }
        Vector2 v = new Vector2((float) targetVector.getX(), (float)targetVector.getY());
        v.rotate(angle);
        targetVector.setLocation(v.x, v.y);
        newAcc.setLocation(targetVector.getX() * self.getSpeed(), targetVector.getY() * self.getSpeed());

        self.setAcc(newAcc);
        self.updateVelocity();
        self.moveUnit(deltaTime);
    }
}
