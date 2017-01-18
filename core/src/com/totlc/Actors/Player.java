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
import com.totlc.AssetList;

import java.awt.geom.Point2D;

public class Player extends Character {
    // Player texture information.
    private Texture standDownTexture;
    private Texture standUpTexture;
    private Texture standLeftTexture;
    private Texture standRightTexture;


    // Player info.
    private float acc = 300;
    private float maxVelocity = 300;

    private TextureAtlas walkDownTextureAtlas;
    private Animation<TextureRegion> walkDownAnimation;

    private TextureAtlas walkUpTextureAtlas;
    private Animation<TextureRegion> walkUpAnimation;

    private TextureAtlas walkLeftTextureAtlas;
    private Animation<TextureRegion> walkLeftAnimation;

    private TextureAtlas walkRightTextureAtlas;
    private Animation<TextureRegion> walkRightAnimation;

    public Player(AssetManager assetManager, int x, int y){
        setX(x);
        setY(y);
        setHeight(128);
        setWidth(96);
        setHitBox(new Rectangle(x, y, getWidth(), getHeight()));

        setSpeed(acc);

        setMaxVel(maxVelocity);

        setMovingLeft(false);
        setMovingRight(false);
        setMovingUp(false);
        setMovingDown(false);

        setHpMax(5);
        setHpCurrent(getHpMax());

        setAssetManager(assetManager);

        // Standing assets.
        assetManager.load(AssetList.PLAYER_STAND_UP.toString(), Texture.class);
        assetManager.load(AssetList.PLAYER_STAND_DOWN.toString(), Texture.class);
        assetManager.load(AssetList.PLAYER_STAND_LEFT.toString(), Texture.class);
        assetManager.load(AssetList.PLAYER_STAND_RIGHT.toString(), Texture.class);

        // Walking animations
        assetManager.load(AssetList.PLAYER_WALK_UP.toString(), TextureAtlas.class);
        assetManager.load(AssetList.PLAYER_WALK_DOWN.toString(), TextureAtlas.class);
        assetManager.load(AssetList.PLAYER_WALK_LEFT.toString(), TextureAtlas.class);
        assetManager.load(AssetList.PLAYER_WALK_RIGHT.toString(), TextureAtlas.class);

        setTexture(new Texture(Gdx.files.internal(AssetList.PLAYER_STAND_DOWN.toString())));
    }

    public void draw(Batch batch, float delta) {
        AssetManager assetManager = getAssetManager();
        if (assetManager.update() && !assetsLoaded()) {
            // Done loading. Instantiate all assets

            setAssetsLoaded(true);

            standDownTexture = assetManager.get(AssetList.PLAYER_STAND_DOWN.toString());
            standUpTexture = assetManager.get(AssetList.PLAYER_STAND_DOWN.toString());
            standLeftTexture = assetManager.get(AssetList.PLAYER_STAND_DOWN.toString());
            standRightTexture = assetManager.get(AssetList.PLAYER_STAND_DOWN.toString());

            walkDownTextureAtlas = assetManager.get(AssetList.PLAYER_WALK_DOWN.toString());
            walkDownAnimation = new Animation<TextureRegion>(1 / 12f, walkDownTextureAtlas.getRegions());

            walkUpTextureAtlas = assetManager.get(AssetList.PLAYER_WALK_UP.toString());
            walkUpAnimation = new Animation<TextureRegion>(1 / 12f, walkUpTextureAtlas.getRegions());

            walkLeftTextureAtlas = assetManager.get(AssetList.PLAYER_WALK_LEFT.toString());
            walkLeftAnimation = new Animation<TextureRegion>(1 / 12f, walkLeftTextureAtlas.getRegions());

            walkRightTextureAtlas = assetManager.get(AssetList.PLAYER_WALK_RIGHT.toString());
            walkRightAnimation = new Animation<TextureRegion>(1 / 12f, walkRightTextureAtlas.getRegions());
        }

        if (assetsLoaded()) {

            if (this.isMovingRight()) {
                batch.draw(walkRightAnimation.getKeyFrame(getAnimationTime(), true), getX(), getY());

            } else if (this.isMovingLeft()) {
                batch.draw(walkLeftAnimation.getKeyFrame(getAnimationTime(), true), getX(), getY());

            } else if (this.isMovingDown()) {
                // TODO: double check parameter value.
                // Technically, animationTime parameter to getKeyFrame shouldn't matter cause it's looping. (???)
                batch.draw(walkDownAnimation.getKeyFrame(getAnimationTime(), true), getX(), getY());

            } else if (this.isMovingUp()) {
                batch.draw(walkUpAnimation.getKeyFrame(getAnimationTime(), true), getX(), getY());

            } else {
                batch.draw(standDownTexture, getX(), getY());
            }
        }
    }

    @Override
    public void act(float deltaTime){
        increaseAnimationTime(deltaTime);

        Point2D newAcc = getAcc();
        if (this.isMovingDown()) {
            newAcc.setLocation(newAcc.getX(), -getSpeed());
            setAcc(newAcc);
        }
        if (this.isMovingUp()) {
            newAcc.setLocation(newAcc.getX(), getSpeed());
            setAcc(newAcc);
        }
        if ((this.isMovingDown() && this.isMovingUp()) ||
                !(this.isMovingDown() || this.isMovingUp())){
            newAcc.setLocation(newAcc.getX(), 0);
            setAcc(newAcc);
        }

        if (this.isMovingRight()) {
            newAcc.setLocation(getSpeed(), newAcc.getY());
            setAcc(newAcc);
        }
        if (this.isMovingLeft()) {
            newAcc.setLocation(-getSpeed(), newAcc.getY());
            setAcc(newAcc);
        }
        if (this.isMovingRight() && this.isMovingLeft()||
                !(this.isMovingRight() || this.isMovingLeft())){
            newAcc.setLocation(0, newAcc.getY());
            setAcc(newAcc);
        }

        if (!(this.isMovingDown() || this.isMovingLeft() ||
                this.isMovingRight() || this.isMovingUp())) {
            setAnimationTime(0);
        }

        updateVelocity();
        moveUnit(deltaTime);
        returnIntoBounds();
    }

    public boolean collidesWith(Actor otherActor) {
        if (otherActor instanceof Enemy) {
            setHpCurrent(getHpCurrent() - ((Enemy)otherActor).getAttack());
        }

        return (getHpCurrent() <= 0);
    }

    public void endCollidesWith(Actor otherActor) {}
}
