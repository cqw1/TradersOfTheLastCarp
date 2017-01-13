package com.totlc;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.totlc.TradersOfTheLastCarp;

public class Player extends Actor {
    // Player texture information.
    //TextureAtlas textureAtlas = new TextureAtlas(Gdx.files.internal("dummy/dummy.atlas"));
    //TextureAtlas textureAtlas = TradersOfTheLastCarp.getAssetManager().get("dummy/dummy.atlas");
    Texture texture = new Texture(Gdx.files.internal("dummy/0.png"));

    private float animationTime = 0;

    // Player health.
    private int hpMAX;
    private int hpCURRENT;
    private float speed = 200;
    private TextureAtlas textureAtlas;
    private Animation<TextureRegion> animation;

    // Orientation and movement flags.
    private boolean isMovingLeft, isMovingRight, isMovingUp, isMovingDown;

    public Player(TextureAtlas textureAtlas, int x, int y){
        setX(x);
        setY(y);
        setMovingLeft(false);
        setMovingRight(false);
        setMovingUp(false);
        setMovingDown(false);
        setHpMAX(3);
        setHpCURRENT(getHpMAX());
        setTextureAtlas(textureAtlas);
        animation.setPlayMode(Animation.PlayMode.LOOP);
    }

    public void draw(Batch batch, float alpha) {
        batch.draw(animation.getKeyFrame(animationTime), getX(), getY());
    }

    @Override
    public void act(float deltaTime){
        animationTime += deltaTime;

        if (animationTime > 6) {
            animationTime = 1;
        }

        if (isMovingLeft()){
            moveBy(-speed * deltaTime, 0);
        }
        if (isMovingRight()){
            moveBy(speed * deltaTime, 0);
        }
        if (ismovingUp()){
            moveBy(0, speed * deltaTime);
        }
        if (isMovingDown()){
            moveBy(0, -speed * deltaTime);
        }
    }

    public int getHpMAX() {
        return hpMAX;
    }

    public void setHpMAX(int hpMAX) {
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

    public boolean ismovingUp() {
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


    public int getHpCURRENT() {
        return hpCURRENT;
    }

    public void setHpCURRENT(int hpCURRENT) {
        this.hpCURRENT = hpCURRENT;
    }

    public void setTextureAtlas(TextureAtlas textureAtlas) {
        this.textureAtlas = textureAtlas;
        this.animation = new Animation(1/12f, this.textureAtlas.getRegions());
    }
}
