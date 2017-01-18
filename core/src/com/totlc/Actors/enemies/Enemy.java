package com.totlc.Actors.enemies;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.totlc.Actors.Character;

import java.awt.geom.Point2D;

//Empty for now. All enemies will inherit from this one, to differentiate player and enemy?

public abstract class Enemy extends Character {
    // Player health.
    private int attack;

    public Enemy(AssetManager assetManager, int x, int y){
        setX(x);
        setY(y);
        setAssetManager(assetManager);
    }

    public int getAttack() {
        return attack;
    }

    public void setAttack(int attack) {
        this.attack = attack;
    }

    public Point2D getTarget(Actor target) {
        return new Point2D.Double(target.getX() - getX(), target.getY() - getY());
    }

}
