package com.totlc.Actors;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.totlc.Actors.damage.Damage;
import com.totlc.Actors.enemies.AEnemy;
import com.totlc.Actors.items.Key;
import com.totlc.Actors.terrain.AWall;
import com.totlc.Actors.weapons.AWeapon;
import com.totlc.Actors.weapons.Whip;
import com.totlc.AssetList;

import java.awt.geom.Point2D;

public class Player extends Character {
    // Player texture information.
    private Texture standDownTexture;
    private Texture standUpTexture;
    private Texture standLeftTexture;
    private Texture standRightTexture;

    private int goldfishCount = 0;


    // Player info.
    private float acc = 300;
    private float maxVelocity = 300;

    // Walking
    private TextureAtlas walkDownTextureAtlas;
    private Animation<TextureRegion> walkDownAnimation;

    private TextureAtlas walkUpTextureAtlas;
    private Animation<TextureRegion> walkUpAnimation;

    private TextureAtlas walkLeftTextureAtlas;
    private Animation<TextureRegion> walkLeftAnimation;

    private TextureAtlas walkRightTextureAtlas;
    private Animation<TextureRegion> walkRightAnimation;

    // Player whipping
    private TextureAtlas whippingRightTextureAtlas;
    private Animation<TextureRegion> whippingRightAnimation;

    private TextureAtlas whippingLeftTextureAtlas;
    private Animation<TextureRegion> whippingLeftAnimation;

    private TextureAtlas whippingUpTextureAtlas;
    private Animation<TextureRegion> whippingUpAnimation;

    private TextureAtlas whippingDownTextureAtlas;
    private Animation<TextureRegion> whippingDownAnimation;

    private boolean invincible = false;
    private float whippingAnimationLength = 0.5f;

    private int invincibilityPeriod = 1000; // in millis
    private long invincibilityStart;
    private AWeapon weapon;
    private boolean hasKey = false;

    public Player(AssetManager assetManager, float x, float y){
        super(assetManager, new Rectangle(x, y, 72, 100));

        setSpeed(acc);

        setMaxVel(maxVelocity);

        setMovingLeft(false);
        setMovingRight(false);
        setMovingUp(false);
        setMovingDown(false);

        setHpMax(5);
        setHpCurrent(getHpMax());

        //TODO: Correct the hitbox?
        moveHitBox(28, 0);

       initTextures(assetManager);
    }

    public void draw(Batch batch, float delta) {
        if (invincible) {
            if (System.currentTimeMillis() > (invincibilityStart + invincibilityPeriod)) {
                invincible = false;
            } else {
                if (System.currentTimeMillis() % 2 == 0){
                    return;
                }
            }
        }

        if (getAttacking()) {

            if (this.getIsFacing().isFacingDown()) {
                batch.draw(whippingDownAnimation.getKeyFrame(weapon.getAttackingCounter(), false), getX(), getY());
            } else if (this.getIsFacing().isFacingUp()) {
                batch.draw(whippingUpAnimation.getKeyFrame(weapon.getAttackingCounter(), false), getX(), getY());
            } else if (this.getIsFacing().isFacingRight()) {
                batch.draw(whippingRightAnimation.getKeyFrame(weapon.getAttackingCounter(), false), getX(), getY());
            } else if (this.getIsFacing().isFacingLeft()) {
                batch.draw(whippingLeftAnimation.getKeyFrame(weapon.getAttackingCounter(), false), getX(), getY());
            }

        } else if (getVel().getX() == 0 && getVel().getY() == 0) {
            if (this.getIsFacing().isFacingDown()) {
                batch.draw(standDownTexture, getX(), getY());
            } else if (this.getIsFacing().isFacingLeft()) {
                batch.draw(standLeftTexture, getX(), getY());
            } else if (this.getIsFacing().isFacingRight()) {
                batch.draw(standRightTexture, getX(), getY());
            } else if (this.getIsFacing().isFacingUp()) {
                batch.draw(standUpTexture, getX(), getY());
            }
        } else if (this.isMovingRight()) {
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

    @Override
    public void act(float deltaTime){
        increaseAnimationTime(deltaTime);

        if (getAttacking()) {
            // Returns so we don't move while we're attacking.
            return;
        }

        if (!(this.isMovingDown() || this.isMovingLeft() ||
                this.isMovingRight() || this.isMovingUp())) {
            setAnimationTime(0);
        }


        float formerX = getX();
        float formerY = getY();
        setAcc(getNewAcceleration());
        updateVelocity();
        moveUnit(deltaTime);
        returnIntoBounds(formerX, formerY);
    }

    public Point2D getNewAcceleration() {
        Point2D newAcc = getAcc();

        if (this.isMovingDown()) {
            newAcc.setLocation(newAcc.getX(), -getSpeed());
        }
        if (this.isMovingUp()) {
            newAcc.setLocation(newAcc.getX(), getSpeed());
        }
        if ((this.isMovingDown() && this.isMovingUp()) ||
                !(this.isMovingDown() || this.isMovingUp())){
            newAcc.setLocation(newAcc.getX(), 0);
        }

        if (this.isMovingRight()) {
            newAcc.setLocation(getSpeed(), newAcc.getY());
        }
        if (this.isMovingLeft()) {
            newAcc.setLocation(-getSpeed(), newAcc.getY());
        }
        if (this.isMovingRight() && this.isMovingLeft()||
                !(this.isMovingRight() || this.isMovingLeft())){
            newAcc.setLocation(0, newAcc.getY());
        }

        return newAcc;
    }

    public boolean collidesWith(Actor otherActor) {
        if (otherActor instanceof AEnemy) {
            if (!invincible) {
                takeDamage(((AEnemy)otherActor).getAttack());
                invincible = true;
                invincibilityStart = System.currentTimeMillis();
            }
        } else
            if (otherActor instanceof Damage) {
                if (!invincible && ((Damage)otherActor).getDamageType() != 2) {
                    takeDamage(((Damage)otherActor).getAttack());
                    invincible = true;
                    invincibilityStart = System.currentTimeMillis();
                }
        }

        return (getHpCurrent() <= 0);
    }

    @Override
    public void setAttacking(boolean attacking){
        super.setAttacking(attacking);
        if (attacking){
            Sound sound = Gdx.audio.newSound(Gdx.files.internal("sounds/whip0.mp3"));
            sound.play(0.7f);
        }
    }

    protected void initTextures(AssetManager assetManager){
        // Standing
        standDownTexture = assetManager.get(AssetList.PLAYER_STAND_DOWN.toString());
        standUpTexture = assetManager.get(AssetList.PLAYER_STAND_UP.toString());
        standLeftTexture = assetManager.get(AssetList.PLAYER_STAND_LEFT.toString());
        standRightTexture = assetManager.get(AssetList.PLAYER_STAND_RIGHT.toString());

        // Walking
        walkDownTextureAtlas = assetManager.get(AssetList.PLAYER_WALK_DOWN.toString());
        walkDownAnimation = new Animation<TextureRegion>(1 / 12f, walkDownTextureAtlas.getRegions());

        walkUpTextureAtlas = assetManager.get(AssetList.PLAYER_WALK_UP.toString());
        walkUpAnimation = new Animation<TextureRegion>(1 / 12f, walkUpTextureAtlas.getRegions());

        walkLeftTextureAtlas = assetManager.get(AssetList.PLAYER_WALK_LEFT.toString());
        walkLeftAnimation = new Animation<TextureRegion>(1 / 12f, walkLeftTextureAtlas.getRegions());

        walkRightTextureAtlas = assetManager.get(AssetList.PLAYER_WALK_RIGHT.toString());
        walkRightAnimation = new Animation<TextureRegion>(1 / 12f, walkRightTextureAtlas.getRegions());

        // Whipping
        whippingDownTextureAtlas = assetManager.get(AssetList.PLAYER_WHIP_DOWN.toString());
        whippingDownAnimation = new Animation<TextureRegion>((float) (1.0/whippingDownTextureAtlas.getRegions().size * whippingAnimationLength), whippingDownTextureAtlas.getRegions());

        whippingUpTextureAtlas = assetManager.get(AssetList.PLAYER_WHIP_UP.toString());
        whippingUpAnimation = new Animation<TextureRegion>((float) (1.0/whippingUpTextureAtlas.getRegions().size * whippingAnimationLength), whippingUpTextureAtlas.getRegions());

        whippingLeftTextureAtlas = assetManager.get(AssetList.PLAYER_WHIP_LEFT.toString());
        whippingLeftAnimation = new Animation<TextureRegion>((float) (1.0/whippingLeftTextureAtlas.getRegions().size * whippingAnimationLength), whippingLeftTextureAtlas.getRegions());

        whippingRightTextureAtlas = assetManager.get(AssetList.PLAYER_WHIP_RIGHT.toString());
        whippingRightAnimation = new Animation<TextureRegion>((float) (1.0/whippingRightTextureAtlas.getRegions().size * whippingAnimationLength), whippingRightTextureAtlas.getRegions());

        weapon = new Whip(assetManager, this, AssetList.WHIP_UP.toString(), AssetList.WHIP_DOWN.toString(), AssetList.ORANGE_WHIP_LEFT.toString(), AssetList.ORANGE_WHIP_RIGHT.toString());

        setTexture(new Texture(Gdx.files.internal(AssetList.PLAYER_STAND_DOWN.toString())));
    }

    public void createWeapon(){
        getStage().addActor(getWeapon());
        getWeapon().setZIndex(getZIndex() - 1);
        setAttacking(true);
    }

    public void endCollidesWith(Actor otherActor) {}

    public int getInvincibilityPeriod() {
        return invincibilityPeriod;
    }

    public void setInvincibilityPeriod(int invincibilityPeriod) {
        this.invincibilityPeriod = invincibilityPeriod;
    }

    public long getInvincibilityStart() {
        return invincibilityStart;
    }

    public void setInvincibilityStart(long start) {
        invincibilityStart = start;
    }

    public AWeapon getWeapon() { return weapon; }

    public void setWeapon(Whip whip) {
        weapon = whip;
    }

    public void setGoldfishCount(int count) {
        goldfishCount = count;
    }

    public int getGoldfishCount() {
        return goldfishCount;
    }

    public boolean isInvincible() {
        return invincible;
    }

    public boolean hasKey() {
        return hasKey;
    }

    public void removeKey() {
        this.hasKey = false;
    }

    public void giveKey() {
        this.hasKey = true;
    }
}
