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

public abstract class Character extends Actor {
    // Player texture information.
    //TextureAtlas textureAtlas = new TextureAtlas(Gdx.files.internal("dummy/dummy.atlas"));
    //TextureAtlas textureAtlas = TradersOfTheLastCarp.getAssetManager().get("dummy/dummy.atlas");
    Texture texture;

    private float animationTime = 0;
    private float HEIGHT;
    private float WIDTH;

    // Player health.
    private int hpMAX;
    private int hpCURRENT;
    private float speed;
    private TextureAtlas textureAtlas;
    private Animation<TextureRegion> animation;
    private AssetManager assetManager;
    private String asset;
    private Rectangle hitBox;

    // Orientation and movement flags.
    private boolean isMovingLeft, isMovingRight, isMovingUp, isMovingDown;

    public void draw(Batch batch, float alpha) {
        if (assetManager.update()) {
            // Done loading. Move to next screen.
            // TODO: Move to next screen.
            textureAtlas = assetManager.get(asset);

            /*
            TextureRegion walkingRightRegion = textureAtlas.findRegion("right");
            System.out.println("walkingRightRegion: " + walkingRightRegion);
            animation = new Animation<TextureRegion>(1/12f, walkingRightRegion);
            */

            animation = new Animation<TextureRegion>(1/12f, textureAtlas.getRegions());
            animation.setPlayMode(Animation.PlayMode.LOOP);
            batch.draw(animation.getKeyFrame(animationTime), getX(), getY());
        }
    }

    abstract public void act(float deltaTime);

    public int getHpMax() {
        return hpMAX;
    }

    public void setHpMax(int hpMAX) {
        this.hpMAX = hpMAX;
    }

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

    public int getHpCurrent() {
        return hpCURRENT;
    }

    public void setHpCurrent(int hpCURRENT) {
        this.hpCURRENT = hpCURRENT;
    }

    public Rectangle getHitBox() { return hitBox; }

    public void setHitBox(Rectangle r) { hitBox = r; }

    public void setAssetManager(AssetManager assetManager) {
        this.assetManager = assetManager;
    }

    public void setAsset(String asset) {
        this.asset = asset;
    }

    public void setTexture(Texture t) { texture = t; }

    public void setWidth(float w) { WIDTH = w; }

    public float getWidth() { return WIDTH; }

    public void setHeight(float h) { HEIGHT = h; }

    public float getHeight() { return HEIGHT; }

    public void setSpeed(float s) { speed = s; }

    public float getSpeed() { return speed; }

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
