package com.totlc.Actors;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.totlc.TradersOfTheLastCarp;

public abstract class totlcObject extends Actor {

    private float speed;

    //Image related fields
    private TextureAtlas textureAtlas;
    private AssetManager assetManager;

    Texture texture;
    Animation<TextureRegion> animation;
    String asset;
    float animationTime = 0;

    //Hit box related fields
    private Rectangle hitBox;
    private float HEIGHT;
    private float WIDTH;

    private float friction;
    private float[] acc;
    private float[] vel;

    // Orientation and movement flags.
    private boolean isMovingLeft, isMovingRight, isMovingUp, isMovingDown;

    public abstract void draw(Batch batch, float alpha);

    // Must return true if removing oneself, false otherwise.
    public abstract boolean collidesWith(Actor otherActor);

    public abstract void endCollidesWith(Actor otherActor);

    public boolean isMovingLeft() {
        return isMovingLeft;
    }

    public void setMovingLeft(boolean movingLeft) {
        isMovingLeft = movingLeft;
    }

    public boolean isMovingRight() {
        return isMovingRight;
    }

    public void setMovingRight(boolean movingRight) {
        isMovingRight = movingRight;
    }

    public boolean isMovingUp() {
        return isMovingUp;
    }

    public void setMovingUp(boolean movingUp) {
        this.isMovingUp = movingUp;
    }

    public boolean isMovingDown() {
        return isMovingDown;
    }

    public void setMovingDown(boolean movingDown) {
        isMovingDown = movingDown;
    }

    public void moveRel(float x, float y) {
        setX(getX() + x);
        setY(getY() + y);
        hitBox.setPosition(hitBox.getX() + x, hitBox.getY() + y);
    }

    public void moveAbs(float x, float y) {
        setX(x);
        setY(y);
        hitBox.setPosition(x, y);
    }

    public Rectangle getHitBox() { return hitBox; }

    public void setHitBox(Rectangle r) { hitBox = r; }

    public float getFriction() {
        return friction;
    }

    public void setFriction(float friction) {
        this.friction = friction;
    }

    public float[] getAcc() {
        return acc;
    }

    public void setAcc(float[] acc) {
        this.acc = acc;
    }

    public float[] getVel() {
        return vel;
    }

    public void setVel(float[] vel) {
        this.vel = vel;
    }

    public void setAssetManager(AssetManager assetManager) {
        this.assetManager = assetManager;
    }

    public void setTextureAtlas(TextureAtlas textureAtlas) {
        this.textureAtlas = textureAtlas;
    }

    public void setWidth(float w) { WIDTH = w; }

    public float getWidth() { return WIDTH; }

    public void setHeight(float h) { HEIGHT = h; }

    public float getHeight() { return HEIGHT; }

    public void setSpeed(float s) { speed = s; }

    public float getSpeed() { return speed; }

    public AssetManager getAssetManager() { return assetManager; }

    public void setTexture(Texture t) { texture = t; }

    public Texture getTexture() { return texture; }

    public void setAnimation(Animation<TextureRegion> a) { animation = a; }

    public Animation<TextureRegion> getAnimation() { return getAnimation(); }

    public String getAsset() { return asset; }

    public void setAsset(String asset) { this.asset = asset; }

    public void setAnimationTime(float a) { animationTime = a; }

    public float getAnimationTime() { return animationTime; }

    public void increaseAnimationTime(float i) { animationTime += i; }

    public void returnIntoBounds() {
        if (getX() < 0) {
            moveAbs(0, getY());
        }

        if (getX() + WIDTH > TradersOfTheLastCarp.CONFIG_WIDTH) {
            moveAbs(TradersOfTheLastCarp.CONFIG_WIDTH - WIDTH, getY());
        }

        if (getY() < 0) {
            moveAbs(getX(), 0);
        }

        if (getY() + HEIGHT > TradersOfTheLastCarp.CONFIG_HEIGHT) {
            moveAbs(getX(), TradersOfTheLastCarp.CONFIG_HEIGHT - HEIGHT);
        }
    }
}