package com.totlc.Actors.enemies;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
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
    private static int atk = 1;
    private static int spd = 200;

    // State variables.
    private boolean skitter = false;
    private int wait_cycles = 50;
    private int counter = 0;


    public Spider(AssetManager assetManager, int x, int y){
        super(assetManager, x, y);
        this.walk_texture_0 = getAssetManager().get(AssetList.SPIDER_WALK.toString());
        this.stand_texture_0 = getAssetManager().get(AssetList.SPIDER_IDLE.toString());
        this.stand_animation_0 = new Animation<TextureRegion>(1/2f, this.stand_texture_0.getRegions());
        this.walk_animation_0 = new Animation<TextureRegion>(1/16f, this.walk_texture_0.getRegions());
        this.stand_animation_0.setPlayMode(Animation.PlayMode.LOOP);
        this.walk_animation_0.setPlayMode(Animation.PlayMode.LOOP);
        setHpMax(hp);
        setHpCurrent(getHpMax());
        setAttack(atk);
    }

    @Override
    public void act(float deltaTime) {
        increaseAnimationTime(deltaTime);
        setAnimationTime(getAnimationTime() % 6);
        counter++;
        if (counter >= wait_cycles){
            skitter = !skitter;
            counter = 0;
        }
        if (skitter){
            float moveDistance = spd * deltaTime;
            Actor target = ((ALevel)getStage()).getPlayer();

            float[] targetVector = {target.getX() - getX(), target.getY() - getY()};
            float n = (float)Math.sqrt( targetVector[0] * targetVector[0] + targetVector[1] * targetVector[1] );
            if (n != 0) {
                targetVector[0] = targetVector[0] / n;
                targetVector[1] = targetVector[1] / n;
            }

            moveBy(targetVector[0] * moveDistance, targetVector[1] * moveDistance);
        }
        returnIntoBounds();
    }

    @Override
    public void draw(Batch batch, float alpha){
        if (skitter){
            batch.draw(walk_animation_0.getKeyFrame(getAnimationTime()), getX(), getY());
        } else{
            batch.draw(stand_animation_0.getKeyFrame(getAnimationTime()), getX(), getY());
        }

    }

    @Override
    public boolean collidesWith(Actor otherActor) {
        skitter = false;
        return true;
    }

    @Override
    public void endCollidesWith(Actor otherActor) {}

    public static int getId() {
        return id;
    }
}