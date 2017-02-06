package com.totlc.Actors.enemies.movement;

import com.badlogic.gdx.math.Vector2;
import com.totlc.Actors.Character;
import com.totlc.Actors.enemies.AEnemy;

import java.awt.geom.Point2D;

public class ProximityBasedAggro extends AMovement {

    // Attack flag that determines aggro.
    private boolean attack = false;
    private double attackSpeed = 200;
    // Period to wait before changing direction.
    private int waitPeriod = 1000;
    // Direction vector.
    private Point2D targetVector;
    // Time counter.
    private long movementTime = 0L;


    public ProximityBasedAggro(Character target) {
        super(target);
        this.waitPeriod = this.waitPeriod + (int)(Math.random() * 1000);
        targetVector = new Point2D.Double();
        Vector2 v = new Vector2(1, 0);
        v.rotate((float) Math.random() * 360);
        targetVector.setLocation(v.x, v.y);
        movementTime = System.currentTimeMillis();
    }

    @Override
    public void move(AEnemy self, float deltaTime) {
        if (new Point2D.Double(getTarget().getX(), getTarget().getY()).distance(new Point2D.Double(self.getX(), self.getY())) < 100){
            attack = true;
        } else{
            attack = false;
        }

        Point2D newAcc = self.getAcc();
        double speed;
        if (attack){
            speed = attackSpeed;
            if (System.currentTimeMillis() - movementTime >= waitPeriod) {
                targetVector = getTargetVector(self);
                movementTime = System.currentTimeMillis();
            }
        } else{
            speed = getTarget().getSpeed();
            if (System.currentTimeMillis() - movementTime >= waitPeriod){
                Vector2 v = new Vector2((float) targetVector.getX(), (float) targetVector.getY());
                v.rotate((float) Math.random() * 360);
                targetVector.setLocation(v.x, v.y);
                movementTime = System.currentTimeMillis();
            }
        }
        System.out.print(attack);
        newAcc.setLocation(targetVector.getX() * speed, targetVector.getY() * speed);
        self.setAcc(newAcc);
        self.updateVelocity();
        self.moveUnit(deltaTime);
        self.returnIntoBounds();
    }
}
