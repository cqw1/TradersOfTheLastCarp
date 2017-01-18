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

public class Spider extends Enemy {

    // Asset and animation constants.
    private TextureAtlas stand_texture_0, walk_texture_0;
    private Animation<TextureRegion> stand_animation_0, walk_animation_0;

    // Stat constants.
    private static int id = 0;
    private static int hp = 1;
    private static int atk = 0;

    private static float friction = 0.9f;
    private static float[] acceleration = {0, 0};
    private static float[] velocity = {0, 0};
    private static float acc = 200;
    private static float maxVelocity = 200;
    private static float knockback = 25;

    // State variables.
    private boolean skitter = false;
    private int wait_cycles = 50;
    private int counter = 0;
    float[] targetVector = {0, 0};


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
        setVel(velocity);
        setAcc(acceleration);
        setMaxVel(maxVelocity);
        setKnockback(knockback);
    }

    @Override
    public void act(float deltaTime) {
        increaseAnimationTime(deltaTime);
//        setAnimationTime(getAnimationTime() % 6);
        counter++;
        if (counter >= wait_cycles){
            skitter = !skitter;
            if (skitter){
                Actor target = ((ALevel)getStage()).getPlayer();
                targetVector[0] = target.getX() - getX();
                targetVector[1] = target.getY() - getY();
            }
            counter = 0;
        }
        float newAcc[] = getAcc();
        if (skitter){
            float n = (float)Math.sqrt( targetVector[0] * targetVector[0] + targetVector[1] * targetVector[1] );
            if (n != 0) {
                targetVector[0] = targetVector[0] / n;
                targetVector[1] = targetVector[1] / n;
            }
            newAcc[0] = targetVector[0] * getSpeed();
            newAcc[1] = targetVector[1] * getSpeed();
            setAcc(newAcc);
        } else{
            newAcc[0] = 0;
            newAcc[1] = 0;
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
