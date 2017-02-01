package com.totlc.Actors.enemies;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.totlc.Actors.enemies.movement.AMovement;
import com.totlc.AssetList;
import com.totlc.levels.ALevel;

import java.awt.geom.Point2D;

/**
 * Basic weak spider enemy.
 * Moves towards player in short bursts that are on a random interval.
 */
public class Spider extends AEnemy {

    // Stat constants.
    private static int id = 0;
    private static int hp = 1;
    private static int atk = 1;

    private static float width = 36;
    private static float height = 20;

    private static float friction = 0.8f;
    private static float speed = 100;
    private static float maxVelocity = 200;
    private static float knockback = 25;

    // State variables.
    private int waitPeriod = 1000; // in millis
    private int waitVariance = 1000; // in millis

    // Asset and animation constants.
    private TextureAtlas idleTextureAtlas;
    private Animation<TextureRegion> idleAnimation;

    private TextureAtlas walkTextureAtlas;
    private Animation<TextureRegion> walkAnimation;

    public Spider(AssetManager assetManager, int x, int y, AMovement movement){
        super(assetManager, new Rectangle(x, y, width, height), movement, hp, atk);

        //TODO: Remove after fixing hitboxes
        moveHitBox(48, 0);

        initMovement(friction, maxVelocity, speed);
        setKnockback(knockback);

        idleTextureAtlas = assetManager.get(AssetList.SPIDER_IDLE.toString());
        idleAnimation = new Animation<TextureRegion>(1 / 12f, idleTextureAtlas.getRegions());

        walkTextureAtlas = assetManager.get(AssetList.SPIDER_WALK.toString());
        walkAnimation = new Animation<TextureRegion>(1 / 12f, walkTextureAtlas.getRegions());
    }

    @Override
    public void act(float deltaTime) {
        increaseAnimationTime(deltaTime);

        if (checkStun()) {
            return;
        }

        getMovement().move(this, deltaTime);
    }

    @Override
    public void draw(Batch batch, float alpha){
        if (getMovement().isMoving()){
            batch.draw(walkAnimation.getKeyFrame(getAnimationTime(), true), getX(), getY());
        } else{
            batch.draw(idleAnimation.getKeyFrame(getAnimationTime(), true), getX(), getY());
        }
        drawHealth(batch, alpha, (int)getWidth() / 2, -8);
    }

    @Override
    public boolean collidesWith(Actor otherActor) {
        return super.collidesWith(otherActor);
    }

    @Override
    public void endCollidesWith(Actor otherActor) {}

    public static int getId() {
        return id;
    }
}
