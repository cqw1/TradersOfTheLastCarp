package com.totlc.Actors.terrain;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.totlc.AssetList;
import com.totlc.Directionality;

public class Door extends AWall {
    private TextureAtlas doorAtlas;
    private Animation<TextureRegion> doorAnimation;

    private float textureWidth, textureHeight;
    private static float scaleX = 1.0f;
    private static float scaleY = 1.0f;
    private float rotation = 0;

    public Door(AssetManager assetManager, Rectangle r) {
        super(assetManager, r);
        doorAtlas = assetManager.get(AssetList.DOOR_LOCK.toString());
        doorAnimation = new Animation<TextureRegion>(1 / 128f, doorAtlas.getRegions());
        textureWidth = doorAnimation.getKeyFrame(0).getRegionWidth();
        textureHeight = doorAnimation.getKeyFrame(0).getRegionHeight();
    }

    public Door(AssetManager assetManager, Rectangle r, Directionality d) {
        super(assetManager, r, d);
        doorAtlas = assetManager.get(AssetList.DOOR_LOCK.toString());
        doorAnimation = new Animation<TextureRegion>(1 / 128f, doorAtlas.getRegions());
        textureWidth = doorAnimation.getKeyFrame(0).getRegionWidth();
        textureHeight = doorAnimation.getKeyFrame(0).getRegionHeight();
        if (d.isFacingRight()){
            rotation = 270;
        } else if (d.isFacingLeft()){
            rotation = 90;
        } else if (d.isFacingUp()){
            rotation = 180;
        }
    }

    @Override
    public void act(float deltaTime){
        super.act(deltaTime);
        increaseAnimationTime(deltaTime);
    }

    @Override
    public void draw(Batch batch, float alpha) {
        batch.draw(doorAnimation.getKeyFrame(getAnimationTime()), getX(), getY(), 0, 0, textureWidth, textureHeight, scaleX, scaleY, rotation);
    }
}
