package com.totlc.Actors.effects;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.totlc.AssetList;

public class Exclamation extends AEffect {

    // Asset and animation constants.
    private TextureAtlas exclamationTextureAtlas;
    private Animation<TextureRegion> exclamationAnimation;

    private long startTime;
    private boolean removeFlag = false;
    private long duration = 500;

    public Exclamation(AssetManager assetManager, float x, float y) {
        super(assetManager, new Rectangle(x, y, 1, 1));
        exclamationTextureAtlas = assetManager.get(AssetList.EXCLAMATION.toString());
        exclamationAnimation = new Animation<TextureRegion>(1 / 36f, exclamationTextureAtlas.getRegions());
        this.startTime = System.currentTimeMillis();
    }

    @Override
    public void act(float deltaTime){
        increaseAnimationTime(deltaTime);
        if (!removeFlag && exclamationAnimation.isAnimationFinished(getAnimationTime())){
            removeFlag = true;
        }
        if (removeFlag && System.currentTimeMillis() - startTime > duration){
            remove();
        }
    }

    @Override
    public void draw(Batch batch, float alpha) {
        batch.draw(exclamationAnimation.getKeyFrame(getAnimationTime(), false), getX(), getY());
    }

    @Override
    public boolean collidesWith(Actor otherActor) {
        return false;
    }

    @Override
    public void endCollidesWith(Actor otherActor) {

    }
}
