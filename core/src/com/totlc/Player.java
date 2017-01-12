package com.totlc;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;

public class Player extends Actor {
    // Player texture information.
    TextureAtlas textureAtlas = new TextureAtlas(Gdx.files.internal("dummy/dummy.atlas"));
    Animation<TextureRegion> animation = new Animation(1/12f, textureAtlas.getRegions());
    Texture texture = new Texture(Gdx.files.internal("dummy/0.png"));

    private float animationTime = 0;

    // Player health.
    private int hp;
    private float speed = 200;

    // Orientation and movement flags.
    private boolean isMovingLeft, isMovingRight, isMovingUp, isMovingDown;

    public Player(int x, int y){
        setX(x);
        setY(y);
        setMovingLeft(false);
        setMovingRight(false);
        setMovingUp(false);
        setMovingDown(false);
        animation.setPlayMode(Animation.PlayMode.LOOP);
    }

    public void draw(Batch batch, float alpha) {
        batch.draw(animation.getKeyFrame(animationTime), getX(), getY());
        //batch.draw(texture, getX(), getY());
    }

    @Override
    public void act(float deltaTime){
        animationTime += deltaTime;
        System.out.println(deltaTime);
        System.out.println(animationTime);
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

    public int getHp() {
        return hp;
    }

    public void setHp(int hp) {
        this.hp = hp;
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


}
