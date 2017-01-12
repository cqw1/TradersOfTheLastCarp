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
    TextureAtlas textureAtlas = new TextureAtlas(Gdx.files.internal("dummy_spritesheet/dummy_spritesheet.atlas"));
    Animation<TextureRegion> animation = new Animation(1/12f, textureAtlas.getRegions());
    Texture texture = new Texture(Gdx.files.internal("dummy_spritesheet/0.png"));

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
    }

    public void draw(Batch batch, float alpha) {
        batch.draw(texture, getX(), getY());
    }

    @Override
    public void act(float deltaTime){
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
        System.out.println(getX() +"," + getY());
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
