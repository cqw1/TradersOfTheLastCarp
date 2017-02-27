package com.totlc.Actors.enemies.movement;

import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.totlc.Actors.enemies.AEnemy;
import com.totlc.Actors.Character;
import com.totlc.Actors.terrain.AWall;

import java.awt.geom.Point2D;

public abstract class AMovement {

    // Attack flag that determines aggro.
    private boolean attack = false;

    private Character target;

    public AMovement(Character player) {
        target = player;
    }

    public void move(AEnemy self, float deltaTime) {
        float formerX = self.getX();
        float formerY = self.getY();
        specializedMove(self, deltaTime);
        self.returnIntoBounds(formerX, formerY);
    }

    public abstract void specializedMove(AEnemy self, float deltaTime);

    public Character getTarget() {
        return target;
    }

    public void setTarget(Character target) {
        this.target = target;
    }

    public Point2D getTargetVector(Actor self) {
        Point2D targetVector = new Point2D.Double(target.getX() - self.getX(), target.getY() - self.getY());
        float n = (float) Math.sqrt(Math.pow(targetVector.getX(), 2) + Math.pow(targetVector.getY(), 2));
        if (n != 0) {
            targetVector.setLocation(targetVector.getX() / n, targetVector.getY() / n);
            return targetVector;
        } else{
            return new Point2D.Double(0, 0);
        }
    }

    public boolean isMoving() {return true;}

    public boolean isAttack() {
        return attack;
    }

    public void setAttack(boolean attack) {
        this.attack = attack;
    }
}
