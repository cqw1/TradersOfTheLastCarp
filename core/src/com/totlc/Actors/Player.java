package com.totlc.Actors;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.totlc.Actors.enemies.Enemy;

public class Player extends Character {
    // Player texture information.
    private TextureAtlas textureAtlas;

    // Player info.
    private float friction = 0.9f;
    private float[] acceleration = {0, 0};
    private float[] velocity = {0, 0};


    public Player(AssetManager assetManager, String asset, int x, int y){
        setX(x);
        setY(y);
        setHeight(128);
        setWidth(96);
        setHitBox(new Rectangle(x, y, getWidth(), getHeight()));

        setSpeed(200);
        setFriction(friction);
        setVel(velocity);
        setAcc(acceleration);

        setMovingLeft(false);
        setMovingRight(false);
        setMovingUp(false);
        setMovingDown(false);

        setHpMax(3);
        setHpCurrent(getHpMax());

        setAssetManager(assetManager);
        setTexture(new Texture(Gdx.files.internal("dummy/0.png")));
        this.asset = asset;
    }

    public void draw(Batch batch, float delta) {
        AssetManager assetManager = getAssetManager();
        if (assetManager.update()) {
            // Done loading. Move to next screen.
            // TODO: Move to next screen.
            textureAtlas = assetManager.get(asset);
            animation = new Animation<TextureRegion>(1/12f, textureAtlas.getRegions());
            animation.setPlayMode(Animation.PlayMode.LOOP);
            batch.draw(animation.getKeyFrame(animationTime), getX(), getY());
        }
    }

    @Override
    public void act(float deltaTime){
        increaseAnimationTime(deltaTime);
        setAnimationTime(getAnimationTime() % 6);

        movePlayer(getSpeed() * deltaTime);

        if (!(isMovingDown() || isMovingLeft() ||
        isMovingRight() || isMovingUp())) {
            setAnimationTime(0);
        }

        returnIntoBounds();
    }

    public boolean collidesWith(Actor otherActor) {
        int knockback = 25;

        if (otherActor instanceof Enemy) {
            movePlayer(-knockback);
            setHpCurrent(getHpCurrent() - ((Enemy)otherActor).getAttack());
        }

        return (getHpCurrent() <= 0);
    }

    private void movePlayer(float distance) {
        if (isMovingLeft()){
            moveRel(-distance, 0);
        }
        if (isMovingRight()){
            moveRel(distance, 0);
        }
        if (isMovingUp()){
            moveRel(0, distance);
        }
        if (isMovingDown()){
            moveRel(0, -distance);
        }
    }

    public void endCollidesWith(Actor otherActor) {}
}
