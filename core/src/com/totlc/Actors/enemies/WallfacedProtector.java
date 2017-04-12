package com.totlc.Actors.enemies;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.g2d.*;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.totlc.Actors.damage.Damage;

import com.totlc.Actors.enemies.movement.AMovement;
import com.totlc.Actors.weapons.Whip;
import com.totlc.AssetList;
import com.totlc.Directionality;

/**
 * Large enemy that is shielded from the front.
 * Must be damaged from other sides.
 * Moves relatively slowly and has only a melee attack.
 */
public class WallfacedProtector extends AEnemy {

    // Stat constants.
    private static int id = 5;
    private static int hp = 2;
    private static int atk = 2;

    private static float maxVel = 200;
    private static float speed = 70;
    private static float friction = 0.2f;
    private long lastHitTime;

    // Textures and animations.
    TextureAtlas stand, walk_side, walk_front, walk_back, head;
    Animation<TextureRegion> walk_animation_side, walk_animation_front, walk_animation_back;

    private static float width = 128, height = 136;
    private static float shieldSize = 200;
    private float textureWidthBody, textureHeightBody, textureWidthHead, textureHeightHead;
    private float headXOffset;
    private float headYOffset;
    private float sway;

    public WallfacedProtector(AssetManager assetManager, float x, float y, AMovement movement) {
        super(assetManager, new Rectangle(x, y, width, height), movement, hp, atk);
        initTextures(assetManager);

        textureWidthBody = walk_side.getRegions().get(0).getRegionWidth();
        textureHeightBody = walk_side.getRegions().get(0).getRegionHeight();
        textureWidthHead = head.getRegions().get(0).getRegionWidth();
        textureHeightHead = head.getRegions().get(0).getRegionHeight();

        getHitBox().setScale(0.8f, 1);
        moveHitBox(getWidth() * 0.1f, 0);

        setHeadXOffset(20);
        setHeadYOffset(0);
        setSway(1);

        this.lastHitTime = System.currentTimeMillis();

        initMovement(friction, maxVel, speed);
    }

    @Override
    public void act(float deltaTime) {
        super.act(deltaTime);
        // Set facing direction.
        if(Math.abs(getVel().getX()) > Math.abs(getVel().getY())){
            if(getVel().getX() < 0){
                setIsFacing(Directionality.LEFT);
            }  else{
                setIsFacing(Directionality.RIGHT);
            }
        } else {
            if(getVel().getY() < 0){
                setIsFacing(Directionality.DOWN);
            }  else{
                setIsFacing(Directionality.UP);
            }
        }
        if (System.currentTimeMillis() - this.lastHitTime > 1000) {
            setInvincible(false);
        }
        drawDustTrail(10);
    }

    @Override
    public void draw(Batch batch, float alpha) {
        // Draw Head, part 1.
        if (this.getIsFacing().isFacingUp()) {
            batch.draw(head.getRegions().get(0), getX() - getHorizontalHeadSway(getAnimationTime()) * 0.5f, getY() + getHeadYOffset(), getTextureWidthHead(), getTextureHeightHead());
        }

        // Draw Body.
        if (getVel().getX() == 0 && getVel().getY() == 0) {
            if (this.getIsFacing().isFacingLeft()) {
                batch.draw(stand.findRegion("stand_side"), getX(), getY(), getTextureWidthBody(), getTextureHeightBody());
            } else if (this.getIsFacing().isFacingRight()) {
                batch.draw(stand.findRegion("stand_side"), getX() + getTextureWidthBody(), getY(), -getTextureWidthBody(), getTextureHeightBody());
            } else if (this.getIsFacing().isFacingUp()) {
                batch.draw(stand.findRegion("stand_back"), getX(), getY(), getTextureWidthBody(), getTextureHeightBody());
            } else if (this.getIsFacing().isFacingDown()) {
                batch.draw(stand.findRegion("stand_front"), getX(), getY(), getTextureWidthBody(), getTextureHeightBody());
            }
        } else if (this.getIsFacing().isFacingRight()) {
            batch.draw(walk_animation_side.getKeyFrame(getAnimationTime(), true), getX() + getTextureWidthBody(), getY(), -getTextureWidthBody(), getTextureHeightBody());
        } else if (this.getIsFacing().isFacingLeft()) {
            batch.draw(walk_animation_side.getKeyFrame(getAnimationTime(), true), getX(), getY(), getTextureWidthBody(), getTextureHeightBody());
        } else if (this.getIsFacing().isFacingUp()) {
            batch.draw(walk_animation_back.getKeyFrame(getAnimationTime(), true), getX(), getY(), getTextureWidthBody(), getTextureHeightBody());
        } else if (this.getIsFacing().isFacingDown()) {
            batch.draw(walk_animation_front.getKeyFrame(getAnimationTime(), true), getX(), getY(), getTextureWidthBody(), getTextureHeightBody());
        } else {
            batch.draw(stand.findRegion("stand_front"), getX(), getY(), getTextureWidthBody(), getTextureHeightBody());
        }
        // Draw Head, part 2.
        if (this.getIsFacing().isFacingLeft()) {
            batch.draw(head.getRegions().get(3), getX() - getHeadXOffset() + getHorizontalHeadSway(getAnimationTime()), getY() + getHeadYOffset(), getTextureWidthHead(), getTextureHeightHead());
        } else if (this.getIsFacing().isFacingRight()) {
            batch.draw(head.getRegions().get(4), getX() + getHeadXOffset() - getHorizontalHeadSway(getAnimationTime()), getY() + getHeadYOffset(), getTextureWidthHead(), getTextureHeightHead());
        } else if (this.getIsFacing().isFacingUp()) {
            batch.draw(head.getRegions().get(1), getX() - getHorizontalHeadSway(getAnimationTime()) * 0.5f, getY() + getHeadYOffset(), getTextureWidthHead(), getTextureHeightHead());
        } else if (this.getIsFacing().isFacingDown()) {
            batch.draw(head.getRegions().get(2), getX() + getHorizontalHeadSway(getAnimationTime()) * 0.5f, getY() + getHeadYOffset(), getTextureWidthHead(), getTextureHeightHead());
        }

        drawHealth(batch, alpha, -(int)getHitBoxWidth() / 2, -(int)getHitBoxHeight() / 2);
        drawStatuses(batch, alpha);
        drawShield(batch);
    }

    protected void initTextures(AssetManager assetManager){
        // Standing Textures.
        stand = assetManager.get(AssetList.WALLFACE_STAND.toString());

        //Walking Textures and Animations.
        walk_side = assetManager.get(AssetList.WALLFACE_WALK_SIDE.toString());
        walk_animation_side = new Animation<TextureRegion>(1 / 10f, walk_side.getRegions(), Animation.PlayMode.LOOP);
        walk_front = assetManager.get(AssetList.WALLFACE_WALK_FRONT.toString());
        walk_animation_front = new Animation<TextureRegion>(1 / 10f, walk_front.getRegions(), Animation.PlayMode.LOOP);
        walk_back = assetManager.get(AssetList.WALLFACE_WALK_BACK.toString());
        walk_animation_back = new Animation<TextureRegion>(1 / 10f, walk_back.getRegions(), Animation.PlayMode.LOOP);

        // Head Textures.
        head = assetManager.get(AssetList.WALLFACE_HEAD.toString());
    }

    @Override
    public boolean collidesWith(Actor otherActor){
        if (otherActor instanceof Damage && ((Damage)otherActor).getDamageType() != 1) {
            Damage damage = (Damage) otherActor;
            if (checkDirectionalInvincibility(otherActor)){
                setInvincible(true);
                this.lastHitTime = System.currentTimeMillis();
            }
            if (!isInvincible()) {
                takeDamage(damage.getAttack());
            }
        } else if (otherActor instanceof Whip) {
            if (checkDirectionalInvincibility(otherActor)){
                setInvincible(true);
                this.lastHitTime = System.currentTimeMillis();
            }
            if (!isInvincible()) {
                // Invincible enemies can't be stunned.
                if (!isStunned()) {
                    setStunned(true);
                    setStunStart(System.currentTimeMillis());
                    setStunPeriod(((Whip)otherActor).getStunPeriod());
                    drawStunIndicator(getStunPeriod());
                    setHpTimer(System.currentTimeMillis());
                    setShowHp(true);
                }
            }
        }
        return (getHpCurrent() <= 0);
    }

    private boolean checkDirectionalInvincibility(Actor otherActor) {
        if(getIsFacing().isFacingRight()){
            if ((otherActor.getX()) > getX() && Math.abs(otherActor.getY() - getY()) < shieldSize){
                return true;
            } else{
                return false;
            }
        } else if (getIsFacing().isFacingLeft()){
            if ((otherActor.getX()) < getX() && Math.abs(otherActor.getY() - getY()) < shieldSize){
                return true;
            } else{
                return false;
            }
        } else if (getIsFacing().isFacingUp()){
            if ((otherActor.getY()) > getY() && Math.abs(otherActor.getX() - getX()) < shieldSize){
                return true;
            } else{
                return false;
            }
        } else {
            if ((otherActor.getY()) < getY() && Math.abs(otherActor.getX() - getX()) < shieldSize){
                return true;
            } else{
                return false;
            }
        }
    }

    // Head offsets for walking.
    private float getHorizontalHeadSway(float deltaTime){
        if (getVel().getX() == 0 && getVel().getY() == 0) {
            return 0;
        }
        switch(walk_animation_side.getKeyFrameIndex(deltaTime)){
            case 0:
                return -2 * getSway();
            case 1:
                return -3 * getSway();
            case 2:
                return -1 * getSway();
            case 3:
                return 1 * getSway();
            case 4:
                return 3 * getSway();
            case 5:
                return 2 * getSway();
            default:
                return 0;
        }
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

    public float getTextureWidthHead() {
        return textureWidthHead;
    }

    public void setTextureWidthHead(float textureWidthHead) {
        this.textureWidthHead = textureWidthHead;
    }

    public float getTextureHeightHead() {
        return textureHeightHead;
    }

    public void setTextureHeightHead(float textureHeightHead) {
        this.textureHeightHead = textureHeightHead;
    }

    public float getHeadXOffset() {
        return headXOffset;
    }

    public void setHeadXOffset(float headXOffset) {
        this.headXOffset = headXOffset;
    }

    public float getHeadYOffset() {
        return headYOffset;
    }

    public void setHeadYOffset(float headYOffset) {
        this.headYOffset = headYOffset;
    }

    public float getSway() {
        return sway;
    }

    public void setSway(float sway) {
        this.sway = sway;
    }
}
