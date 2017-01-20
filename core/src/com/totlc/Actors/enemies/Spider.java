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

public class Spider extends AEnemy {

    // Stat constants.
    private static int id = 0;
    private static int hp = 1;
    private static int atk = 1;

    private static float friction = 0.8f;
    private static float speed = 100;
    private static float maxVelocity = 200;
    private static float knockback = 25;

    // State variables.
    private boolean skitter = false;
    private int wait_cycles = 40;
    private int wait_variance = 20;
    private int skitter_cycles = 40;
    private int counter = 0;
    private Point2D targetVector = new Point2D.Double(0, 0);

    // Asset and animation constants.
    private TextureAtlas idleTextureAtlas;
    private Animation<TextureRegion> idleAnimation;

    private TextureAtlas walkTextureAtlas;
    private Animation<TextureRegion> walkAnimation;

    public Spider(AssetManager assetManager, int x, int y){
        super(assetManager, x, y);

        setHpMax(hp);
        setHpCurrent(getHpMax());
        setAttack(atk);

        setHeight(32);
        setWidth(32);
        initHitBox();

        setSpeed(speed);
        setFriction(friction);
        setMaxVel(maxVelocity);
        setKnockback(knockback);

        assetManager.load(AssetList.SPIDER_IDLE.toString(), TextureAtlas.class);
        assetManager.load(AssetList.SPIDER_WALK.toString(), TextureAtlas.class);

        // Randomize wait_cycles.
        wait_cycles = wait_cycles + (int)(Math.random() * wait_variance);
    }

    @Override
    public void act(float deltaTime) {
        increaseAnimationTime(deltaTime);
        Point2D newAcc = getAcc();
        if (skitter){
            if (++counter >= skitter_cycles){
                skitter = false;
                counter = 0;
            }
            newAcc.setLocation(targetVector.getX() * getSpeed(), targetVector.getY() * getSpeed());
            setAcc(newAcc);
        } else{
            if (++counter >= wait_cycles){
                skitter = true;
                Actor target = ((ALevel)getStage()).getPlayer();
                targetVector = getTarget(target);
                counter = 0;
            }
            newAcc.setLocation(0, 0);
            setAcc(newAcc);
        }
        updateVelocity();
        moveUnit(deltaTime);
        returnIntoBounds();
    }

    @Override
    public void draw(Batch batch, float alpha){
        AssetManager assetManager = getAssetManager();
        if (assetManager.update() && !assetsLoaded()) {
            // Done loading. Instantiate all assets

            setAssetsLoaded(true);

            idleTextureAtlas = assetManager.get(AssetList.SPIDER_IDLE.toString());
            idleAnimation = new Animation<TextureRegion>(1 / 12f, idleTextureAtlas.getRegions());

            walkTextureAtlas = assetManager.get(AssetList.SPIDER_WALK.toString());
            walkAnimation = new Animation<TextureRegion>(1 / 12f, walkTextureAtlas.getRegions());
        }

        if (assetsLoaded()) {
            if (skitter){
                batch.draw(walkAnimation.getKeyFrame(getAnimationTime(), true), getX(), getY());
            } else{
                batch.draw(idleAnimation.getKeyFrame(getAnimationTime(), true), getX(), getY());
            }
        }
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
