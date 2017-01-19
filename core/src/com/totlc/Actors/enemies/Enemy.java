package com.totlc.Actors.enemies;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.totlc.Actors.Character;
import com.totlc.Actors.projectiles.Projectile;

import java.awt.geom.Point2D;

//Empty for now. All enemies will inherit from this one, to differentiate player and enemy?

public abstract class Enemy extends Character {
    // Player health.
    private int attack;

    public Enemy(AssetManager assetManager, int x, int y){
        super(assetManager, x ,y);
    }

    public boolean collidesWith(Actor otherActor) {
        if (otherActor instanceof Projectile) {
            takeDamage(((Projectile)otherActor).getAttack());
        }

        return (getHpCurrent() <= 0);
    }

    public void endCollidesWith(Actor otherActor) {}

    public int getAttack() {
        return attack;
    }

    public void setAttack(int attack) {
        this.attack = attack;
    }

    public Point2D getTarget(Actor target) {
        Point2D targetVector = new Point2D.Double(target.getX() - getX(), target.getY() - getY());
        float n = (float) Math.sqrt(Math.pow(targetVector.getX(), 2) + Math.pow(targetVector.getY(), 2));
        if (n != 0) {
            targetVector.setLocation(targetVector.getX() / n, targetVector.getY() / n);
            return targetVector;
        } else{
            return new Point2D.Double(0, 0);
        }
    }

}
