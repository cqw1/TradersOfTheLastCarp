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

public class HeavyStorkTrooper extends AEnemy {
    // Stat constants.
    private static int id = 10;
    private static int basehp = 3;
    private static int atk = 2;

    private static float maxVel = 36;
    private static float speed = 36;
    private static float friction = 0;

    private static float width = 100;
    private static float height = 160;

    private static long attackTime = 1600;

    // Asset and animation constants.
    private TextureAtlas walkTextureAtlas, shootTextureAtlas;
    private Animation<TextureRegion> walkAnimation, shootAnimation;
    private Sound quackSound, fireSound, bulletSound;

    private Point2D aimVector;

    private long attackStartTime, lastQuackTime, lastShotTime;

    private float textureWidthBody, textureHeightBody;

    public HeavyStorkTrooper(AssetManager assetManager, float x, float y, AMovement movement) {
        this(assetManager, new Rectangle(x, y, width, height), movement);
    }

    public HeavyStorkTrooper(AssetManager assetManager, Rectangle r, AMovement movement) {
        super(assetManager, r, movement, basehp, atk);
        initMovement(friction, maxVel, speed);
        moveHitBox(24, 0);

        walkTextureAtlas = getAssetManager().get(AssetList.STORKTROOPER_HEAVY_MARCH.toString());
        walkAnimation = new Animation<TextureRegion>(1/12f, this.walkTextureAtlas.getRegions());

        shootTextureAtlas = getAssetManager().get(AssetList.STORKTROOPER_HEAVY_SHOOT.toString());
        shootAnimation = new Animation<TextureRegion>(1/24f, this.shootTextureAtlas.getRegions());

        textureWidthBody = walkTextureAtlas.getRegions().get(0).getRegionWidth();
        textureHeightBody = walkTextureAtlas.getRegions().get(0).getRegionHeight();

        quackSound = Gdx.audio.newSound(Gdx.files.internal("sounds/deepquack.wav"));
        fireSound = Gdx.audio.newSound(Gdx.files.internal("sounds/rapidgun.mp3"));
        bulletSound = Gdx.audio.newSound(Gdx.files.internal("sounds/shell1.wav"));
    }

    @Override
    public void act(float deltaTime){
        super.act(deltaTime);
        if (checkStun()) {
            return;
        }
        // Set facing direction.
        if(Math.signum(getMovement().getTargetVector(this).getX()) > 0){
            setIsFacing(Directionality.RIGHT);
        } else{
            setIsFacing(Directionality.LEFT);
        }

        if (getMovement().isMoving()) {
            drawDustTrail(1000);
            setAttacking(false);
        } else {
            if (!getAttacking()){
                fire();
            } else {
                // Quack.
                if (System.currentTimeMillis() - this.lastQuackTime > 300){
                    quackSound.play(0.8f);
                    this.lastQuackTime = System.currentTimeMillis();
                }
                if (System.currentTimeMillis() - this.lastShotTime > 200){
                    this.lastShotTime = System.currentTimeMillis();
                    getStage().addActor(new BulletCasing(getAssetManager(), (float)getHitBoxCenter().getX(), (float)getHitBoxCenter().getY()));
                    getStage().addActor(DamageFactory.createDamage(DamageEnum.BULLET, new Point2D.Double(aimVector.getX() * 3600, aimVector.getY() * 3600), getAssetManager(), (float) getHitBoxCenter().getX(), (float) getHitBoxCenter().getY(), 1));
                    aimVector = getTarget(((ALevel)getStage()).getPlayer());
                    bulletSound.play(0.8f);
                }
                if (System.currentTimeMillis() - this.attackStartTime > this.attackTime) {
                    setAttacking(false);
                }
            }
        }
    }

    @Override
    public void draw(Batch batch, float alpha) {
        if (getAttacking()){
            if (getIsFacing().isFacingRight()){
                batch.draw(shootAnimation.getKeyFrame(getAnimationTime(), true), getX(), getY(), getTextureWidthBody(), getTextureHeightBody());
            } else{
                batch.draw(shootAnimation.getKeyFrame(getAnimationTime(), true), getX() + getTextureWidthBody(), getY(), -getTextureWidthBody(), getTextureHeightBody());
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
            drawHealth(batch, alpha, -(int)getHitBoxWidth() / 2, -(int)getHitBoxHeight() / 2);
            drawStatuses(batch, alpha);
            drawShield(batch);
        }
    }

    private void fire(){
        fireSound.play(0.8f);
        setAttacking(true);
        aimVector = getTarget(((ALevel)getStage()).getPlayer());
        this.attackStartTime = System.currentTimeMillis();
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
