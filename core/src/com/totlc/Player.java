package com.totlc;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.totlc.TradersOfTheLastCarp;

public class Player extends Character {
    // Player texture information.
    Texture texture = new Texture(Gdx.files.internal("dummy/0.png"));


    public Player(AssetManager assetManager, String asset, int x, int y){
        setX(x);
        setY(y);
        setHeight(128);
        setWidth(96);
        setHitBox(new Rectangle(x, y, getWidth(), getHeight()));

        setSpeed(200);

        setMovingLeft(false);
        setMovingRight(false);
        setMovingUp(false);
        setMovingDown(false);

        setHpMax(3);
        setHpCurrent(getHpMax());

        setAssetManager(assetManager);
        setAsset(asset);
        setTexture(texture);
    }

    @Override
    public void act(float deltaTime){
        increaseAnimationTime(deltaTime);
        if (getAnimationTime() > 6) {
            setAnimationTime(1);
        }

        if (isMovingLeft()){
            moveRel(-getSpeed() * deltaTime, 0);
        }
        if (isMovingRight()){
            moveRel(getSpeed() * deltaTime, 0);
        }
        if (isMovingUp()){
            moveRel(0, getSpeed() * deltaTime);
        }
        if (isMovingDown()){
            moveRel(0, -getSpeed() * deltaTime);
        }

        returnIntoBounds();
    }

}
