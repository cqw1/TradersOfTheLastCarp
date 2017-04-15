package com.totlc.Actors.players;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

import java.awt.geom.Point2D;

public abstract class PlayableCharacter extends Player {

    // Textures and Dimensions.
    TextureAtlas stand, walk_side, walk_front, walk_back, whip_side, whip_front, whip_back, head;
    Animation<TextureRegion> walk_animation_side, walk_animation_front, walk_animation_back, whip_animation_side, whip_animation_front, whip_animation_back;

    private float textureWidthBody, textureHeightBody, textureWidthHead, textureHeightHead;
    private float headXOffset;
    private float headYOffset;
    private float sway;
    private float bob;

    public PlayableCharacter(AssetManager assetManager, float x, float y) {
        super(assetManager, x, y);
        initTextures(assetManager);
        textureWidthBody = walk_side.getRegions().get(0).getRegionWidth();
        textureHeightBody = walk_side.getRegions().get(0).getRegionHeight();
        textureWidthHead = head.getRegions().get(0).getRegionWidth();
        textureHeightHead = head.getRegions().get(0).getRegionHeight();
        setHeadXOffset(0);
        setHeadYOffset(0);
        setSway(0);
        setBob(0);
    }

    protected abstract void initTextures(AssetManager assetManager);

    @Override
    public void draw(Batch batch, float delta) {
        if (isInvincible()) {
            if (System.currentTimeMillis() % 2 == 0) {
                return;
            }
        }

        if (getAttacking()) {
            drawAttacking(batch, delta);
        } else {
           drawHeadBack(batch, delta);

            // Draw Body
            if (Math.abs(getAcc().getX()) < .001 && Math.abs(getAcc().getY()) < .001) {
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
            drawHead(batch, delta);
        }
    }

    // Head offsets for walking.
    protected float getHorizontalHeadSway(float deltaTime){
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

    // Head offsets for whipping.
    protected Point2D getHeadBob(float deltaTime){
        switch(whip_animation_side.getKeyFrameIndex(deltaTime)){
            case 0:
                return new Point2D.Float(2 * getSway(), 1 * getBob());
            case 1:
                return new Point2D.Float(-3 *getSway(), -3 * getBob());
            case 2:
                return new Point2D.Float(-2 * getSway(), -2 * getBob());
            case 3:
                return new Point2D.Float(3 * getSway(), 0 * getBob());
            default:
                return new Point2D.Float(0, 0);
        }
    }

    protected void drawAttacking(Batch batch, float delta){
        drawHeadBackAttacking(batch, delta);

        // Draw body.
        if (this.getIsFacing().isFacingLeft()) {
            batch.draw(whip_animation_side.getKeyFrame(getWeapon().getAttackingCounter(), false), getX(), getY(), getTextureWidthBody(), getTextureHeightBody());
        } else if (this.getIsFacing().isFacingRight()) {
            batch.draw(whip_animation_side.getKeyFrame(getWeapon().getAttackingCounter(), false), getX() + getTextureWidthBody(), getY(), -getTextureWidthBody(), getTextureHeightBody());
        } else if (this.getIsFacing().isFacingDown()) {
            batch.draw(whip_animation_front.getKeyFrame(getWeapon().getAttackingCounter(), false), getX(), getY(), getTextureWidthBody(), getTextureHeightBody());
        } else if (this.getIsFacing().isFacingUp()) {
            batch.draw(whip_animation_back.getKeyFrame(getWeapon().getAttackingCounter(), false), getX(), getY(), getTextureWidthBody(), getTextureHeightBody());
        }

        drawHeadAttacking(batch, delta);
    }

    protected void drawHead(Batch batch, float delta) {
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
    }

    protected void drawHeadBack(Batch batch, float delta) {
        // Draw Head, part 1.
        if (this.getIsFacing().isFacingUp()) {
            batch.draw(head.getRegions().get(0), getX() - getHorizontalHeadSway(getAnimationTime()) * 0.5f, getY() + getHeadYOffset(), getTextureWidthHead(), getTextureHeightHead());
        }
    }

    protected void drawHeadAttacking(Batch batch, float delta) {
        // Draw Head, part 2.
        if (this.getIsFacing().isFacingLeft()) {
            batch.draw(head.getRegions().get(3), getX() - getHeadXOffset() + (float)getHeadBob(getAnimationTime()).getX(), getY() + getHeadYOffset() + (float)getHeadBob(getAnimationTime()).getY(), getTextureWidthHead(), getTextureHeightHead());
        } else if (this.getIsFacing().isFacingRight()) {
            batch.draw(head.getRegions().get(4), getX() + getHeadXOffset() - (float)getHeadBob(getAnimationTime()).getX(), getY() + getHeadYOffset() + (float)getHeadBob(getAnimationTime()).getY(), getTextureWidthHead(), getTextureHeightHead());
        } else if (this.getIsFacing().isFacingUp()) {
            batch.draw(head.getRegions().get(1), getX() - (float)getHeadBob(getAnimationTime()).getX(), getY() + getHeadYOffset() + (float)getHeadBob(getAnimationTime()).getY(), getTextureWidthHead(), getTextureHeightHead());
        } else if (this.getIsFacing().isFacingDown()) {
            batch.draw(head.getRegions().get(2), getX() + (float)getHeadBob(getAnimationTime()).getX(), getY() + getHeadYOffset() + (float)getHeadBob(getAnimationTime()).getY(), getTextureWidthHead(), getTextureHeightHead());
        }
    }

    protected void drawHeadBackAttacking(Batch batch, float delta) {
        // Draw Head, part 1.
        if (this.getIsFacing().isFacingUp()) {
            batch.draw(head.getRegions().get(0), getX() - (float)getHeadBob(getAnimationTime()).getX(), getY() + getHeadYOffset() + (float)getHeadBob(getAnimationTime()).getY(), getTextureWidthHead(), getTextureHeightHead());
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

    public float getBob() {
        return bob;
    }

    public void setBob(float bob) {
        this.bob = bob;
    }
}