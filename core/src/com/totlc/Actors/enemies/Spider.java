package com.totlc.Actors.enemies;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.totlc.AssetList;
import com.totlc.levels.ALevel;

import java.awt.geom.Point2D;

public class Spider extends Enemy {

    // Asset and animation constants.
    private TextureAtlas stand_texture_0, walk_texture_0;
    private Animation<TextureRegion> stand_animation_0, walk_animation_0;

    // Stat constants.
    private static int id = 0;
    private static int hp = 1;
    private static int atk = 0;

    private static float friction = 0.9f;
    private static float acc = 200;
    private static float maxVelocity = 200;
    private static float knockback = 25;

    // State variables.
    private boolean skitter = false;
    private int wait_cycles = 50;
    private int counter = 0;


    public Spider(AssetManager assetManager, int x, int y){
        super(assetManager, x, y);
        this.walk_texture_0 = getAssetManager().get(AssetList.SPIDER_WALK.toString());
        this.stand_texture_0 = getAssetManager().get(AssetList.SPIDER_IDLE.toString());
        this.stand_animation_0 = new Animation<TextureRegion>(1/2f, this.stand_texture_0.getRegions());
        this.walk_animation_0 = new Animation<TextureRegion>(1/12f, this.walk_texture_0.getRegions());
        setHpMax(hp);
        setHpCurrent(getHpMax());
        setAttack(atk);
        setHitBox(new Rectangle(x + 40, y + 16, 42, 16));

        setSpeed(acc);
        setFriction(friction);
        setMaxVel(maxVelocity);
        setKnockback(knockback);
    }

    @Override
    public void act(float deltaTime) {
        increaseAnimationTime(deltaTime);

        Point2D targetVector = new Point2D.Double(0, 0);
        if (++counter >= wait_cycles){
            skitter = !skitter;
            if (skitter){
                Actor target = ((ALevel)getStage()).getPlayer();
                targetVector = getTarget(target);
            }
            counter = 0;
        }

        Point2D newAcc = getAcc();
        if (skitter){
            float n = (float)Math.sqrt( Math.pow(targetVector.getX(), 2) + Math.pow(targetVector.getY(), 2));
            if (n != 0) {
                targetVector.setLocation(targetVector.getX() / n, targetVector.getY() / n);
            }
            newAcc.setLocation(targetVector.getX() * getSpeed(), targetVector.getY() * getSpeed());
            setAcc(newAcc);
        } else{
            newAcc.setLocation(0, 0);
            setAcc(newAcc);
        }

        updateVelocity();
        moveUnit(deltaTime);
        returnIntoBounds();
    }

    @Override
    public void draw(Batch batch, float alpha){
        if (skitter){
            batch.draw(walk_animation_0.getKeyFrame(getAnimationTime(), true), getX(), getY());
        } else{
            batch.draw(stand_animation_0.getKeyFrame(getAnimationTime(), true), getX(), getY());
        }
    }

    @Override
    public boolean collidesWith(Actor otherActor) {
        skitter = false;
        return false;
    }

    @Override
    public void endCollidesWith(Actor otherActor) {}

    public static int getId() {
        return id;
    }
}
