package com.totlc.Actors.terrain;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.sun.net.httpserver.Filter;
import com.totlc.AssetList;
import com.totlc.Directionality;
import com.totlc.levels.ObjectiveVerifier.Objectives;

public class Door extends AWall {

    private TextureAtlas doorAtlas;
    private Animation<TextureRegion> doorAnimation;

    private float textureWidth, textureHeight;
    private static float scaleX = 1.5f;
    private static float scaleY = 0.5f;
    private float xOffset, yOffset ;
    private float rotation = 0;

    public Door(AssetManager assetManager, float x, float y, float width, float height, Objectives obj) {
        this(assetManager, x, y, width, height, obj, Directionality.LEFT);
    }

    public Door(AssetManager assetManager, float x, float y, float width, float height, Objectives obj, Directionality d) {
        super(assetManager, new Rectangle(x, y, width, height));

        if (obj.equals(Objectives.DESTROY)) {
            doorAtlas = assetManager.get(AssetList.DOOR_SKULL.toString());
        } else if (obj.equals(Objectives.SURVIVE)) {
            doorAtlas = assetManager.get(AssetList.DOOR_TIME.toString());
        } else {
            doorAtlas = assetManager.get(AssetList.DOOR_LOCK.toString());
        }

        doorAnimation = new Animation<TextureRegion>(1 / 80f, doorAtlas.getRegions());
        textureWidth = doorAnimation.getKeyFrame(0).getRegionWidth();
        textureHeight = doorAnimation.getKeyFrame(0).getRegionHeight();

        if (d.isFacingRight()){
            rotation = 90;
            xOffset = (textureHeight / 2) * (1 - scaleY);
            yOffset = (28) * (1 - scaleX);
        } else if (d.isFacingLeft()){
            rotation = 270;
            xOffset = -(textureHeight / 2) * (1 - scaleY);
            yOffset = (1 - scaleX) - 28;
        } else if (d.isFacingUp()) {
            rotation = 180;
            xOffset = 0;
            yOffset = 0;
        } else{
            xOffset = 0;
            yOffset = 0;
        }

        for (int i = 0; i < doorAtlas.getRegions().size; i++){
            doorAtlas.getRegions().get(i).getTexture().setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
        }
    }

    @Override
    public void act(float deltaTime){
        super.act(deltaTime);
        if(isOpen()) {
            increaseAnimationTime(deltaTime);
        }
    }

    @Override
    public void draw(Batch batch, float alpha) {
        batch.draw(getAssetManager().get(AssetList.WALL_RIGHT.toString(), Texture.class), getX(), getY(), getWidth(), getHeight());
        batch.draw(doorAnimation.getKeyFrame(getAnimationTime()), getX() + xOffset, getY() + yOffset, textureWidth / 2, textureHeight / 2, textureWidth, textureHeight, scaleX, scaleY, rotation);
    }
}
