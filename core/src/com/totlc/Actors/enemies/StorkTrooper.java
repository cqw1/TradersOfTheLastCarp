package com.totlc.Actors.enemies;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.*;
import com.badlogic.gdx.math.Rectangle;
import com.totlc.Actors.damage.DamageEnum;
import com.totlc.Actors.damage.DamageFactory;
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

    private static float width = 70;
    private static float height = 160;

    private static float maxVel = 36;
    private static float speed = 36;
    private static float friction = 0;

    private static float aimChance = .008f;
    private static long aimTime = 2000;

    // Asset and animation constants.
    private TextureAtlas walkTextureAtlas, attackTextureAtlas, particleAtlas;
    private Animation<TextureRegion> walkAnimation, attackAnimation;
    private ParticleEffect bulletCasing;
    private Sound quackSound, aimSound, fireSound, bulletSound;

    private Point2D aimVector;

    private boolean quack, aiming;
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

        textureWidthBody = walkTextureAtlas.getRegions().get(0).getRegionWidth();
        textureHeightBody = walkTextureAtlas.getRegions().get(0).getRegionHeight();

        quackSound = Gdx.audio.newSound(Gdx.files.internal("sounds/quack.mp3"));
        aimSound = Gdx.audio.newSound(Gdx.files.internal("sounds/prefire.mp3"));
        fireSound = Gdx.audio.newSound(Gdx.files.internal("sounds/gun.mp3"));
        bulletSound = Gdx.audio.newSound(Gdx.files.internal("sounds/shell0.wav"));

        this.quack = true;

        this.aiming = false;
    }

    @Override
    public void act(float deltaTime){
        super.act(deltaTime);
        // Set facing direction.
        if(Math.signum(getMovement().getTargetVector(this).getX()) > 0){
            setIsFacing(Directionality.RIGHT);
        } else{
            setIsFacing(Directionality.LEFT);
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
            // Attack chance.
            if (Math.random() < aimChance && !getMovement().isMoving()){
                setAnimationTime(0);
                takeAim();
            }
        } else{
            if (this.aiming && System.currentTimeMillis() - this.attackStartTime > aimTime * 0.8){
                fire();
            }
            if(!this.aiming && System.currentTimeMillis() - this.attackStartTime > aimTime){
                bulletSound.play(0.8f);
                setAttacking(false);
            }
        }
        if(getMovement().isMoving()){
            drawDustTrail(1000);
        }
    }

    @Override
    public void draw(Batch batch, float alpha) {
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

    private void takeAim(){
        aimSound.play(1f);
        setAttacking(true);
        this.aiming = true;
        this.attackStartTime = System.currentTimeMillis();
        aimVector = getTarget(((ALevel)getStage()).getPlayer());
    }

    private void fire(){
        fireSound.play(1f);

        getStage().addActor(DamageFactory.createDamage(DamageEnum.BULLET, new Point2D.Double(aimVector.getX() * 3000, aimVector.getY() * 3000), getAssetManager(), (float) getCenter().getX(), (float) getCenter().getY(), 1));

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
