package com.totlc.Actors.players;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.totlc.Actors.damage.Bubble;
import com.totlc.Actors.damage.Damage;
import com.totlc.Actors.damage.DamageEnum;
import com.totlc.Actors.damage.DamageFactory;
import com.totlc.Actors.weapons.NullWeapon;
import com.totlc.Actors.weapons.Whip;
import com.totlc.AssetList;

import java.awt.geom.Point2D;

public class MrFischl extends PlayableCharacter {

    private TextureAtlas head_spit;

    public MrFischl(AssetManager assetManager) {
        this(assetManager, 0, 0);
    }

    public MrFischl(AssetManager assetManager, float x, float y) {
        super(assetManager, x, y);

        setHeadXOffset(16);
        setHeadYOffset(32);
        setSway(1);
        setBob(4);

        setHpMax(5);
        setHpCurrent(getHpMax());
        setSpeed(300);
        setMaxVel(300);
    }

    @Override
    protected void initTextures(AssetManager assetManager) {
        // Head Textures.
        // Standing Textures.
        stand = assetManager.get(AssetList.FISCHL_STAND.toString());

        //Walking Textures and Animations.
        walk_side = assetManager.get(AssetList.FISCHL_WALK_SIDE.toString());
        walk_animation_side = new Animation<TextureRegion>(1 / 12f, walk_side.getRegions(), Animation.PlayMode.LOOP);
        walk_front = assetManager.get(AssetList.FISCHL_WALK_FRONT.toString());
        walk_animation_front = new Animation<TextureRegion>(1 / 12f, walk_front.getRegions(), Animation.PlayMode.LOOP);
        walk_back = assetManager.get(AssetList.FISCHL_WALK_BACK.toString());
        walk_animation_back = new Animation<TextureRegion>(1 / 12f, walk_back.getRegions(), Animation.PlayMode.LOOP);

        // Attacking Textures and Animation (Should be unused for Fischl).
        whip_side = assetManager.get(AssetList.JANE_WHIP_SIDE.toString());
        whip_animation_side = new Animation<TextureRegion>(1.0f / whip_side.getRegions().size * 0.3f, whip_side.getRegions());
        whip_front = assetManager.get(AssetList.JANE_WHIP_FRONT.toString());
        whip_animation_front = new Animation<TextureRegion>(1.0f / whip_front.getRegions().size * 0.3f, whip_front.getRegions());
        whip_back = assetManager.get(AssetList.JANE_WHIP_BACK.toString());
        whip_animation_back = new Animation<TextureRegion>(1.0f / whip_back.getRegions().size * 0.3f, whip_back.getRegions());

        head = assetManager.get(AssetList.FISCHL_HEAD.toString());
        head_spit = assetManager.get(AssetList.FISCHL_HEAD_SPIT.toString());

        // WaterBubble placeholder initialization.
        setWeapon(new NullWeapon(assetManager, this, 0, 0.3f, AssetList.BLUE_WHIP_BACK.toString(), AssetList.BLUE_WHIP_FRONT.toString(), AssetList.BLUE_WHIP_LEFT.toString(), AssetList.BLUE_WHIP_RIGHT.toString()));
        getWeapon().getHitBox().setScale(0, 0);
    }

    @Override
    protected void drawAttacking(Batch batch, float delta){
        // Draw Head, part 1.
        if (this.getIsFacing().isFacingUp()) {
            batch.draw(head_spit.getRegions().get(0), getX() - (float)getHeadBob(getAnimationTime()).getX(), getY() + getHeadYOffset() + (float)getHeadBob(getAnimationTime()).getY(), getTextureWidthHead(), getTextureHeightHead());
        }

        // Draw body.
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
            batch.draw(head_spit.getRegions().get(3), getX() - getHeadXOffset() + (float)getHeadBob(getAnimationTime()).getX(), getY() + getHeadYOffset() + (float)getHeadBob(getAnimationTime()).getY(), getTextureWidthHead(), getTextureHeightHead());
        } else if (this.getIsFacing().isFacingRight()) {
            batch.draw(head_spit.getRegions().get(4), getX() + getHeadXOffset() - (float)getHeadBob(getAnimationTime()).getX(), getY() + getHeadYOffset() + (float)getHeadBob(getAnimationTime()).getY(), getTextureWidthHead(), getTextureHeightHead());
        } else if (this.getIsFacing().isFacingUp()) {
            batch.draw(head_spit.getRegions().get(1), getX() - (float)getHeadBob(getAnimationTime()).getX(), getY() + getHeadYOffset() + (float)getHeadBob(getAnimationTime()).getY(), getTextureWidthHead(), getTextureHeightHead());
        } else if (this.getIsFacing().isFacingDown()) {
            batch.draw(head_spit.getRegions().get(2), getX() + (float)getHeadBob(getAnimationTime()).getX(), getY() + getHeadYOffset() + (float)getHeadBob(getAnimationTime()).getY(), getTextureWidthHead(), getTextureHeightHead());
        }
    }

    @Override
    public void createWeapon(){
        super.createWeapon();
        if (getAttacking()){
            Sound sound = Gdx.audio.newSound(Gdx.files.internal("sounds/blop.mp3"));

            Point2D initVel;
            sound.play(1.0f);
            if (getIsFacing().isFacingRight()){
                initVel = new Point2D.Double(500, 0);
            } else if(getIsFacing().isFacingLeft()) {
                initVel = new Point2D.Double(-500, 0);
            } else if(getIsFacing().isFacingUp()) {
                initVel = new Point2D.Double(0, 500);
            } else {
                initVel = new Point2D.Double(0, -500);
            }
            Damage bubble = DamageFactory.createDamage(DamageEnum.BUBBLE, initVel, getAssetManager(), (float)getHitBoxCenter().getX() - 33, (float)getHitBoxCenter().getY() - 16, 2);
            getStage().addActor(bubble);
            if (getIsFacing().isFacingUp()){
                bubble.setZIndex(getZIndex() - 1);
            }
        } else{
            Sound sound = Gdx.audio.newSound(Gdx.files.internal("sounds/negative0.wav"));
            sound.play(1.0f);
        }
    }
}
