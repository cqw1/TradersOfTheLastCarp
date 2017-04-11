package com.totlc.Actors.enemies;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.*;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.totlc.Actors.enemies.movement.AMovement;
import com.totlc.AssetList;
import com.totlc.Directionality;

/**
 * Enemy that rolls to move and attack.
 * Stands idly when not rolling.
 * When rolling, becomes invulnerable to damage.
 * Alternates between rolling and idle phases.
 */
public class DaredevilPangolini extends AEnemy {

    // Stat constants.
    private static int id = 4;
    private static int hp = 2;
    private static int atk = 1;

    private static float width = 70;
    private static float height = 100;

    private static float maxVel = 300;
    private static float speed = 20;
    private static float friction = 0f;

    private static float rollChance = .003f;
    private static long rollTime = 3000;
    private static float rollSpeed = 300;
    private static float rollDamage = 2;
    private long rollStartTime;

    // Asset and animation constants.
    private TextureAtlas idleTextureAtlas, rollTextureAtlas, particleAtlas;
    private Animation<TextureRegion> idleAnimation, rollAnimation;
    private ParticleEffect debrisTrail;

    private boolean spinUp, spinDown;

    private float textureWidthBody, textureHeightBody;

    public DaredevilPangolini(AssetManager assetManager, float x, float y, AMovement movement) {
        this(assetManager, new Rectangle(x, y, width, height), movement);
    }

    public DaredevilPangolini(AssetManager assetManager, Rectangle r, AMovement movement) {
        super(assetManager, r, movement, hp, atk);
        initMovement(friction, maxVel, speed);

        idleTextureAtlas = getAssetManager().get(AssetList.PANGOLINI_IDLE.toString());
        idleAnimation = new Animation<TextureRegion>(1/16f, this.idleTextureAtlas.getRegions());
        rollTextureAtlas = getAssetManager().get(AssetList.PANGOLINI_ROLL.toString());
        rollAnimation = new Animation<TextureRegion>(1/24f, this.rollTextureAtlas.getRegions());

        particleAtlas = assetManager.get(AssetList.PARTICLES.toString());
        debrisTrail = new ParticleEffect();
        debrisTrail.load(Gdx.files.internal(AssetList.DEBRIS_TRAIL.toString()), particleAtlas);
        debrisTrail.allowCompletion();

        textureWidthBody = idleTextureAtlas.getRegions().get(0).getRegionWidth();
        textureHeightBody = idleTextureAtlas.getRegions().get(0).getRegionHeight();

        moveHitBox(16, 8);
    }

    @Override
    public void act(float deltaTime) {
        super.act(deltaTime);
        if (checkStun()) {
            return;
        }
        if(Math.signum(getMovement().getTargetVector(this).getX()) > 0){
            debrisTrail.setPosition((float)getCenter().getX(), getY() + 10);
        } else{
            debrisTrail.setPosition((float)getCenter().getX() + 36, getY() + 10);
        }
        if (getAttacking()){
            getMovement().move(this, deltaTime);
            if(System.currentTimeMillis() - rollStartTime > rollTime){
                setAnimationTime(0);
                setAttacking(false);
                setSpeed(speed);
                setAttack(atk);
                getHitBox().setScale(1, 1);
                setInvincible(false);
                debrisTrail.allowCompletion();
            }
        } else {
            // Set facing direction.
            if(Math.signum(getMovement().getTargetVector(this).getX()) > 0){
                setIsFacing(Directionality.RIGHT);
                rollAnimation.setPlayMode(Animation.PlayMode.REVERSED);

            } else{
                setIsFacing(Directionality.LEFT);
                rollAnimation.setPlayMode(Animation.PlayMode.NORMAL);
            }
            // Roll chance.
            if (Math.random() < rollChance){
                setAnimationTime(0);
                this.rollStartTime = System.currentTimeMillis();
                roll();
            }
        }
    }

    @Override
    public void draw(Batch batch, float alpha) {
        debrisTrail.draw(batch, Gdx.graphics.getDeltaTime());
        if (this.getIsFacing().isFacingLeft()) {
            if (getAttacking()){
                batch.draw(rollAnimation.getKeyFrame(getAnimationTime(), true), getX(), getY(), getTextureWidthBody(), getTextureHeightBody());

            } else{
                batch.draw(idleAnimation.getKeyFrame(getAnimationTime(), true), getX(), getY(), getTextureWidthBody(), getTextureHeightBody());
            }
        } else {
            if (getAttacking()){
                batch.draw(rollAnimation.getKeyFrame(getAnimationTime(), true), getX() + getTextureWidthBody(), getY(), -getTextureWidthBody(), getTextureHeightBody());
            } else{
                batch.draw(idleAnimation.getKeyFrame(getAnimationTime(), true), getX() + getTextureWidthBody(), getY(), -getTextureWidthBody(), getTextureHeightBody());
            }
        }
        drawHealth(batch, alpha, -(int)getHitBoxWidth() / 2, -(int)getHitBoxHeight() / 2);
        drawStatuses(batch, alpha);
        drawShield(batch);
    }

    private void roll(){
        setAttacking(true);
        setSpeed(rollSpeed);
        setAttack((int)rollDamage);
        getHitBox().setScale(1, 0.6f);
        setInvincible(true);
        debrisTrail.start();
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
