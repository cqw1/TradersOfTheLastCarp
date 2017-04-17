package com.totlc.Actors.players;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Array;
import com.totlc.Actors.damage.DamageEnum;
import com.totlc.Actors.damage.DamageFactory;
import com.totlc.Actors.damage.Lasso;
import com.totlc.Actors.weapons.NullWeapon;
import com.totlc.Actors.weapons.Whip;
import com.totlc.AssetList;

import java.awt.geom.Point2D;

public class Texas extends PlayableCharacter {

    private float headWidthOffset = -16;

    public Texas(AssetManager assetManager) {
        this(assetManager, 0, 0);
    }

    public Texas(AssetManager assetManager, float x, float y) {
        super(assetManager, x, y);

        setHeadXOffset(0);
        setHeadYOffset(32);
        setSway(2);
        setBob(3);

        setHpMax(5);
        setHpCurrent(getHpMax());
        setSpeed(300);
        setMaxVel(300);

        setMovingDown(true);
    }

    @Override
    protected void initTextures(AssetManager assetManager) {
        // Standing Textures.
        stand = assetManager.get(AssetList.ROSE_STAND.toString());

        //Walking Textures and Animations.
        walk_side = assetManager.get(AssetList.JACK_WALK_SIDE.toString());
        walk_animation_side = new Animation<TextureRegion>(1 / 12f, walk_side.getRegions(), Animation.PlayMode.LOOP);
        walk_front = assetManager.get(AssetList.JACK_WALK_FRONT.toString());
        walk_animation_front = new Animation<TextureRegion>(1 / 12f, walk_front.getRegions(), Animation.PlayMode.LOOP);
        walk_back = assetManager.get(AssetList.JACK_WALK_BACK.toString());
        walk_animation_back = new Animation<TextureRegion>(1 / 12f, walk_back.getRegions(), Animation.PlayMode.LOOP);

        // Attacking Textures and Animation.
        whip_side = assetManager.get(AssetList.JACK_WHIP_SIDE.toString());
        whip_animation_side = new Animation<TextureRegion>(1.0f /whip_side.getRegions().size * 0.3f, whip_side.getRegions());
        whip_front = assetManager.get(AssetList.JACK_WHIP_FRONT.toString());
        whip_animation_front = new Animation<TextureRegion>(1.0f /whip_front.getRegions().size * 0.3f, whip_front.getRegions());
        whip_back = assetManager.get(AssetList.JACK_WHIP_BACK.toString());
        whip_animation_back = new Animation<TextureRegion>(1.0f /whip_back.getRegions().size * 0.3f, whip_back.getRegions());

        // Head Textures.
        head = assetManager.get(AssetList.ROSE_HEAD.toString());

        // Lasso placeholder initialization.
        setWeapon(new NullWeapon(assetManager, this, 0, 0.3f, AssetList.ORANGE_WHIP_BACK.toString(), AssetList.ORANGE_WHIP_FRONT.toString(), AssetList.ORANGE_WHIP_LEFT.toString(), AssetList.ORANGE_WHIP_RIGHT.toString()));
        getWeapon().getHitBox().setScale(0, 0);    }

    @Override
    protected void drawHead(Batch batch, float delta) {
        // Draw Head, part 2.
        if (this.getIsFacing().isFacingLeft()) {
            batch.draw(head.getRegions().get(3), getX() - getHeadXOffset() + this.headWidthOffset + getHorizontalHeadSway(getAnimationTime()), getY() + getHeadYOffset(), getTextureWidthHead(), getTextureHeightHead());
        } else if (this.getIsFacing().isFacingRight()) {
            batch.draw(head.getRegions().get(4), getX() + getHeadXOffset() + this.headWidthOffset - getHorizontalHeadSway(getAnimationTime()), getY() + getHeadYOffset(), getTextureWidthHead(), getTextureHeightHead());
        } else if (this.getIsFacing().isFacingUp()) {
            batch.draw(head.getRegions().get(1), getX() + this.headWidthOffset - getHorizontalHeadSway(getAnimationTime()) * 0.5f, getY() + getHeadYOffset(), getTextureWidthHead(), getTextureHeightHead());
        } else if (this.getIsFacing().isFacingDown()) {
            batch.draw(head.getRegions().get(2), getX() + this.headWidthOffset + getHorizontalHeadSway(getAnimationTime()) * 0.5f, getY() + getHeadYOffset(), getTextureWidthHead(), getTextureHeightHead());
        }
    }

    @Override
    protected void drawHeadBack(Batch batch, float delta) {
        // Draw Head, part 1.
        if (this.getIsFacing().isFacingUp()) {
            batch.draw(head.getRegions().get(0), getX() + this.headWidthOffset - getHorizontalHeadSway(getAnimationTime()) * 0.5f, getY() + getHeadYOffset(), getTextureWidthHead(), getTextureHeightHead());
        }
    }

    @Override
    protected void drawHeadAttacking(Batch batch, float delta) {
        // Draw Head, part 2.
        if (this.getIsFacing().isFacingLeft()) {
            batch.draw(head.getRegions().get(3), getX() - getHeadXOffset() + this.headWidthOffset + (float)getHeadBob(getAnimationTime()).getX(), getY() + getHeadYOffset() + (float)getHeadBob(getAnimationTime()).getY(), getTextureWidthHead(), getTextureHeightHead());
        } else if (this.getIsFacing().isFacingRight()) {
            batch.draw(head.getRegions().get(4), getX() + getHeadXOffset() + this.headWidthOffset - (float)getHeadBob(getAnimationTime()).getX(), getY() + getHeadYOffset() + (float)getHeadBob(getAnimationTime()).getY(), getTextureWidthHead(), getTextureHeightHead());
        } else if (this.getIsFacing().isFacingUp()) {
            batch.draw(head.getRegions().get(1), getX() + this.headWidthOffset - (float)getHeadBob(getAnimationTime()).getX(), getY() + getHeadYOffset() + (float)getHeadBob(getAnimationTime()).getY(), getTextureWidthHead(), getTextureHeightHead());
        } else if (this.getIsFacing().isFacingDown()) {
            batch.draw(head.getRegions().get(2), getX() + this.headWidthOffset + (float)getHeadBob(getAnimationTime()).getX(), getY() + getHeadYOffset() + (float)getHeadBob(getAnimationTime()).getY(), getTextureWidthHead(), getTextureHeightHead());
        }
    }

    @Override
    protected void drawHeadBackAttacking(Batch batch, float delta) {
        // Draw Head, part 1.
        if (this.getIsFacing().isFacingUp()) {
            batch.draw(head.getRegions().get(0), getX() + this.headWidthOffset - (float)getHeadBob(getAnimationTime()).getX(), getY() + getHeadYOffset() + (float)getHeadBob(getAnimationTime()).getY(), getTextureWidthHead(), getTextureHeightHead());
        }
    }

    @Override
    public void createWeapon(){
        super.createWeapon();
        if (getAttacking()){
            Sound sound = Gdx.audio.newSound(Gdx.files.internal("sounds/woosh.mp3"));
            sound.play(1.0f);
            Point2D initVel;
            if (getIsFacing().isFacingRight()){
                initVel = new Point2D.Double(2000, 0);
            } else if(getIsFacing().isFacingLeft()) {
                initVel = new Point2D.Double(-2000, 0);
            } else if(getIsFacing().isFacingUp()) {
                initVel = new Point2D.Double(0, 2000);
            } else {
                initVel = new Point2D.Double(0, -2000);
            }

            Lasso lasso = new Lasso(getAssetManager(), this, (float) getHitBoxCenter().getX(), (float) getHitBoxCenter().getY(), 0, 2, 500, initVel);
            getStage().addActor(lasso);
            lasso.setZIndex(getZIndex() - 1);
        } else{
            Sound sound = Gdx.audio.newSound(Gdx.files.internal("sounds/negative0.wav"));
            sound.play(1.0f);
        }
    }
}
