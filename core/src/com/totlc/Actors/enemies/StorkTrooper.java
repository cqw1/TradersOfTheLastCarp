package com.totlc.Actors.enemies;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.*;
import com.badlogic.gdx.math.Rectangle;
import com.totlc.Actors.damage.DamageEnum;
import com.totlc.Actors.damage.DamageFactory;
import com.totlc.Actors.effects.BulletCasing;
import com.totlc.Actors.enemies.movement.AMovement;
import com.totlc.AssetList;
import com.totlc.Directionality;
import com.totlc.levels.ALevel;

import java.awt.geom.Point2D;

public class StorkTrooper extends AEnemy {

    // Stat constants.
    private static int id = 9;
    private static int basehp = 2;
    private static int atk = 1;

    private static float width = 100;
    private static float height = 160;

    private static float maxVel = 36;
    private static float speed = 36;
    private static float friction = 0;

    private static float aimChance = .008f;
    private static long aimTime = 1000;

    // Asset and animation constants.
    private TextureAtlas walkTextureAtlas, aimTextureAtlas, shootTextureAtlas;
    private Animation<TextureRegion> walkAnimation, aimAnimation, shootAnimation;
    private Sound quackSound, aimSound, fireSound, bulletSound;

    private Point2D aimVector;

    private boolean quack, aiming, cooldown;
    private long attackStartTime;

    private float textureWidthBody, textureHeightBody;

    public StorkTrooper(AssetManager assetManager, float x, float y, AMovement movement) {
        this(assetManager, new Rectangle(x, y, width, height), movement);
    }

    public StorkTrooper(AssetManager assetManager, Rectangle r, AMovement movement) {
        super(assetManager, r, movement, basehp, atk);
        initMovement(friction, maxVel, speed);
        moveHitBox(24, 0);

        walkTextureAtlas = getAssetManager().get(AssetList.STORKTROOPER_MARCH.toString());
        walkAnimation = new Animation<TextureRegion>(1/16f, this.walkTextureAtlas.getRegions());

        aimTextureAtlas = getAssetManager().get(AssetList.STORKTROOPER_AIM.toString());
        aimAnimation = new Animation<TextureRegion>(1/16f, this.aimTextureAtlas.getRegions());

        shootTextureAtlas = getAssetManager().get(AssetList.STORKTROOPER_SHOOT.toString());
        shootAnimation = new Animation<TextureRegion>(1/20f, this.shootTextureAtlas.getRegions());

        textureWidthBody = walkTextureAtlas.getRegions().get(0).getRegionWidth();
        textureHeightBody = walkTextureAtlas.getRegions().get(0).getRegionHeight();

        quackSound = Gdx.audio.newSound(Gdx.files.internal("sounds/quack.mp3"));
        aimSound = Gdx.audio.newSound(Gdx.files.internal("sounds/prefire.mp3"));
        fireSound = Gdx.audio.newSound(Gdx.files.internal("sounds/gun.mp3"));
        bulletSound = Gdx.audio.newSound(Gdx.files.internal("sounds/shell0.wav"));

        this.quack = true;
        this.aiming = false;
        this.cooldown = false;
    }

    @Override
    public void act(float deltaTime){
        super.act(deltaTime);
        if (checkStun()) {
            return;
        }
        // Quack.
        if (getMovement().isMoving() && this.quack){
            quackSound.play(0.2f);
            this.quack = false;
        }
        if (!getMovement().isMoving() && !this.quack){
            this.quack = true;
        }
        if (!getAttacking()){
            // Set facing direction.
            if(getAcc().getX() > 0){
                setIsFacing(Directionality.RIGHT);
            } else if (getAcc().getX() < 0){
                setIsFacing(Directionality.LEFT);
            }
            // Attack chance.
            if (Math.random() < aimChance && !getMovement().isMoving()){
                setAnimationTime(0);
                takeAim();
            }
        } else{
            if (this.cooldown) {
                if (aimAnimation.isAnimationFinished(getAnimationTime())) {
                    setAttacking(false);
                    this.cooldown = false;
                }
            } else{
                if (this.aiming) {
                    if(System.currentTimeMillis() - this.attackStartTime > aimTime){
                        fire();
                    }
                } else {
                    if(shootAnimation.isAnimationFinished(getAnimationTime())){
                        bulletSound.play(0.8f);
                        setAnimationTime(0);
                        aimAnimation.setPlayMode(Animation.PlayMode.REVERSED);
                        this.cooldown = true;
                    }
                }
            }
        }
        if(getMovement().isMoving()){
            drawDustTrail(1000);
        }
    }

    @Override
    public void draw(Batch batch, float alpha) {
        if (getAttacking()){
            if (this.aiming || this.cooldown) {
                if (getIsFacing().isFacingRight()) {
                    batch.draw(aimAnimation.getKeyFrame(getAnimationTime(), false), getX(), getY(), getTextureWidthBody(), getTextureHeightBody());
                } else {
                    batch.draw(aimAnimation.getKeyFrame(getAnimationTime(), false), getX() + getTextureWidthBody(), getY(), -getTextureWidthBody(), getTextureHeightBody());
                }
            } else {
                if (getIsFacing().isFacingRight()){
                    batch.draw(shootAnimation.getKeyFrame(getAnimationTime(), false), getX(), getY(), getTextureWidthBody(), getTextureHeightBody());
                } else{
                    batch.draw(shootAnimation.getKeyFrame(getAnimationTime(), false), getX() + getTextureWidthBody(), getY(), -getTextureWidthBody(), getTextureHeightBody());
                }
            }
        } else{
            if(getMovement().isMoving()){
                if (getIsFacing().isFacingRight()){
                    batch.draw(walkAnimation.getKeyFrame(getAnimationTime(), true), getX(), getY(), getTextureWidthBody(), getTextureHeightBody());
                } else{
                    batch.draw(walkAnimation.getKeyFrame(getAnimationTime(), true), getX() + getTextureWidthBody(), getY(), -getTextureWidthBody(), getTextureHeightBody());
                }
            } else{
                if (getIsFacing().isFacingRight()){
                    batch.draw(walkTextureAtlas.getRegions().get(0), getX(), getY(), getTextureWidthBody(), getTextureHeightBody());
                } else{
                    batch.draw(walkTextureAtlas.getRegions().get(0), getX() + getTextureWidthBody(), getY(), -getTextureWidthBody(), getTextureHeightBody());
                }
            }
        }
        drawHealth(batch, alpha, -(int)getHitBoxWidth() / 2, -(int)getHitBoxHeight() / 2);
        drawStatuses(batch, alpha);
        drawShield(batch);
    }

    private void takeAim(){
        // Set facing direction.
        if(Math.signum(getMovement().getTargetVector(this).getX()) > 0){
            setIsFacing(Directionality.RIGHT);
        } else{
            setIsFacing(Directionality.LEFT);
        }
        aimSound.play(1f);
        aimAnimation.setPlayMode(Animation.PlayMode.NORMAL);
        setAttacking(true);
        this.aiming = true;
        this.attackStartTime = System.currentTimeMillis();
        aimVector = getTarget(((ALevel)getStage()).getPlayer());
    }

    private void fire(){
        setAnimationTime(0);
        fireSound.play(1f);
        getStage().addActor(new BulletCasing(getAssetManager(), (float)getHitBoxCenter().getX(), (float)getHitBoxCenter().getY()));
        getStage().addActor(DamageFactory.createDamage(DamageEnum.BULLET, new Point2D.Double(aimVector.getX() * 3000, aimVector.getY() * 3000), getAssetManager(), (float) getHitBoxCenter().getX(), (float) getHitBoxCenter().getY(), 1));
        this.aiming = false;
    }

    public float getTextureWidthBody() {
        return textureWidthBody;
    }

    public void setTextureWidthBody(float textureWidthBody) {
        this.textureWidthBody = textureWidthBody;
    }

    public float getTextureHeightBody() {
        return textureHeightBody;
    }

    public void setTextureHeightBody(float textureHeightBody) {
        this.textureHeightBody = textureHeightBody;
    }
}
