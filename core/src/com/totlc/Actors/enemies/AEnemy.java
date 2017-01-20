package com.totlc.Actors.enemies;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.totlc.Actors.Character;
import com.totlc.Actors.projectiles.Projectile;
import com.totlc.Actors.weapons.AWeapon;

import java.awt.geom.Point2D;

//Empty for now. All enemies will inherit from this one, to differentiate player and enemy?

public abstract class AEnemy extends Character {
    // Player health.
    private int attack;

    public AEnemy(AssetManager assetManager, int x, int y){
        super(assetManager, x ,y);
    }

    public boolean collidesWith(Actor otherActor) {
        System.out.println("colllidesWith detected");
        if (otherActor instanceof Projectile) {
            System.out.println("Projectile");
            takeDamage(((Projectile)otherActor).getAttack());
        } else if (otherActor instanceof AWeapon) {
            System.out.println("AWeapon");
            takeDamage(((AWeapon)otherActor).getAttack());
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
