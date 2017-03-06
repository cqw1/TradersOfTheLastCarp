package com.totlc.Actors.players;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.totlc.Actors.Player;

public abstract class PlayableCharacter extends Player {

    // Textures and Dimensions.
    Texture stand;

    TextureAtlas walk_side, head;
    Animation<TextureRegion> walk_animation_side;

    private float textureWidth, textureHeight;

    public PlayableCharacter(AssetManager assetManager, float x, float y) {
        super(assetManager, x, y);
        textureWidth = walk_side.getRegions().get(0).getRegionWidth();
        textureHeight = walk_side.getRegions().get(0).getRegionHeight();
        initTextures(assetManager);
    }

    protected abstract void initTextures(AssetManager assetManager);

    @Override
    public void draw(Batch batch, float delta){
        if (isInvincible()) {
            if (System.currentTimeMillis() > (getInvincibilityStart() + getInvincibilityPeriod())) {
                setInvincible(false);
            } else {
                if (System.currentTimeMillis() % 2 == 0){
                    return;
                }
            }
        }

        // Draw Head, part 1.
        if (this.getIsFacing().isFacingDown()) {
            batch.draw(head.getRegions().get(2), getX(), getY(), getTextureWidth(), getTextureHeight());
        } else if (this.getIsFacing().isFacingLeft()) {
            batch.draw(head.getRegions().get(3), getX(), getY(), getTextureWidth(), getTextureHeight());
        } else if (this.getIsFacing().isFacingRight()) {
            batch.draw(head.getRegions().get(4), getX(), getY(), getTextureWidth(), getTextureHeight());
        } else if (this.getIsFacing().isFacingUp()) {
            batch.draw(head.getRegions().get(0), getX(), getY(), getTextureWidth(), getTextureHeight());
        }

        // Draw body.
        if (getAttacking()){

        } else if (getVel().getX() == 0 && getVel().getY() == 0) {
            if (this.getIsFacing().isFacingDown()) {

            } else if (this.getIsFacing().isFacingLeft()) {
                batch.draw(stand, getX(), getY(), getTextureWidth(), getTextureHeight());
            } else if (this.getIsFacing().isFacingRight()) {
                batch.draw(stand, getX(), getY(), -getTextureWidth(), getTextureHeight());
            } else if (this.getIsFacing().isFacingUp()) {

            }
        } else if (this.isMovingRight()) {
            batch.draw(walk_animation_side.getKeyFrame(getAnimationTime(), true), getX(), getY(), -getTextureWidth(), getTextureHeight());

        } else if (this.isMovingLeft()) {
            batch.draw(walk_animation_side.getKeyFrame(getAnimationTime(), true), getX(), getY(), getTextureWidth(), getTextureHeight());

        } else if (this.isMovingDown()) {

        } else if (this.isMovingUp()) {

        } else {
            batch.draw(stand, getX(), getY());
        }

        // Draw Head, part 2.
        if (this.getIsFacing().isFacingUp()) {
            batch.draw(head.getRegions().get(1), getX(), getY(), getTextureWidth(), getTextureHeight());
        }
    }

    public float getTextureWidth() {
        return textureWidth;
    }

    public void setTextureWidth(float textureWidth) {
        this.textureWidth = textureWidth;
    }

    public float getTextureHeight() {
        return textureHeight;
    }

    public void setTextureHeight(float textureHeight) {
        this.textureHeight = textureHeight;
    }
}
