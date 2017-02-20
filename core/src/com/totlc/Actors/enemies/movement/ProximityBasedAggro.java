package com.totlc.Actors.enemies.movement;

import com.totlc.Actors.Character;
import com.totlc.Actors.enemies.AEnemy;

import java.awt.geom.Point2D;

public class ProximityBasedAggro extends AMovement {

    private double attackRange = 500;
    private float attackSpeed = 150;

    private AMovement random, interval;

    public ProximityBasedAggro(Character target) {
        super(target);
        random = new RandomMovement(target);
        interval = new IntervalMovement(target);
    }

    @Override
    public void move(AEnemy self, float deltaTime) {
        if (isAttack()) {
            interval.move(self, deltaTime);
        } else {
            random.move(self, deltaTime);
        }

        if (new Point2D.Double(getTarget().getX(), getTarget().getY()).distance(new Point2D.Double(self.getX(), self.getY())) < attackRange){
            if (!isAttack()){
                self.setAnimationTime(0);
            }
            setAttack(true);
            self.setSpeed(attackSpeed);
        } else{
            setAttack(false);
            self.setSpeed(self.getBaseSpeed());
        }
    }
}
