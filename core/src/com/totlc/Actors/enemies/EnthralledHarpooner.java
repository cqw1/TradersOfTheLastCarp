package com.totlc.Actors.enemies;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.totlc.Actors.damage.Melee;
import com.totlc.Actors.enemies.movement.AMovement;
import com.totlc.AssetList;
import com.totlc.Directionality;

import java.awt.geom.Point2D;

public class EnthralledHarpooner extends AEnemy{

    // Stat constants.
    private static int id = 7;
    private static int hp = 2;
    private static int atk = 1;

    private static float maxVel = 200;
    private static float speed = 20;
    private static float friction = 0.9f;
    private float attackChance = .05f;
    private long attackTime = 100;
    private long attackStartTime;

    // Textures and animations.
    TextureAtlas walk, attack, harpoon;
    Texture stand, head;
    Animation<TextureRegion> walk_animation, attack_animation, harpoon_animation;

    private static float width = 128, height = 160;
    private float textureWidthBody, textureHeightBody, textureWidthHead, textureHeightHead, textureWidthHarpoon, textureHeightHarpoon;
    private float headXOffset;
    private float headYOffset;
    private float bob, sway;

    public EnthralledHarpooner(AssetManager assetManager, float x, float y, AMovement movement) {
        super(assetManager, new Rectangle(x, y, width, height), movement, hp, atk);
        initTextures(assetManager);

        textureWidthBody = walk.getRegions().get(0).getRegionWidth();
        textureHeightBody = walk.getRegions().get(0).getRegionHeight();
        textureWidthHead = head.getWidth();
        textureHeightHead = head.getHeight();
        textureWidthHarpoon = harpoon.getRegions().get(0).getRegionWidth();
        textureHeightHarpoon = harpoon.getRegions().get(0).getRegionWidth();

        getHitBox().setScale(0.7f, 0.7f);
        moveHitBox(getWidth() * 0.15f, 0);

        setHeadXOffset(20);
        setHeadYOffset(0);
        setSway(1);
        setBob(2);

        initMovement(friction, maxVel, speed);
    }

    @Override
    public void act(float deltaTime) {
        super.act(deltaTime);
        if (getAttacking()){
            if(System.currentTimeMillis() - attackStartTime > (attackTime + 500)){
                setAnimationTime(0);
                setAttacking(false);
            }
        } else {
            // Set facing direction.
            if(getVel().getX() < 0){
                setIsFacing(Directionality.LEFT);
            }  else{
                setIsFacing(Directionality.RIGHT);
            }
            // Attack chance.
            if (Math.random() < attackChance
                    && new Point2D.Double(getMovement().getTarget().getX(), getMovement().getTarget().getY()).distance(new Point2D.Double(getX(), getY())) < 200){
                setAnimationTime(0);
                this.attackStartTime = System.currentTimeMillis();
                attack();
            }
            drawDustTrail(10);
        }
    }

    @Override
    public void draw(Batch batch, float alpha) {
        if (!getAttacking()) {
            // Draw Body.
            if (getVel().getX() == 0 && getVel().getY() == 0) {
                if (this.getIsFacing().isFacingLeft()) {
                    batch.draw(stand, getX(), getY(), getTextureWidthBody(), getTextureHeightBody());
                } else {
                    batch.draw(stand, getX() + getTextureWidthBody(), getY(), -getTextureWidthBody(), getTextureHeightBody());
                }
            } else if (this.getIsFacing().isFacingRight()) {
                batch.draw(walk_animation.getKeyFrame(getAnimationTime(), true), getX() + getTextureWidthBody(), getY(), -getTextureWidthBody(), getTextureHeightBody());
            } else if (this.getIsFacing().isFacingLeft()) {
                batch.draw(walk_animation.getKeyFrame(getAnimationTime(), true), getX(), getY(), getTextureWidthBody(), getTextureHeightBody());
            } else {
                batch.draw(stand, getX(), getY(), getTextureWidthBody(), getTextureHeightBody());
            }

            // Draw Head, part 2.
            if (this.getIsFacing().isFacingLeft()) {
                batch.draw(head, getX() - getHeadXOffset() + getHorizontalHeadSway(getAnimationTime()), getY() + getHeadYOffset(), getTextureWidthHead(), getTextureHeightHead());
            } else {
                batch.draw(head, getX() + getHeadXOffset() - getHorizontalHeadSway(getAnimationTime()) + getTextureWidthHead(), getY() + getHeadYOffset(), -getTextureWidthHead(), getTextureHeightHead());
            }
        } else{
            // Draw weapon
            if (this.getIsFacing().isFacingLeft()) {
                batch.draw(harpoon_animation.getKeyFrame(getAnimationTime(), false), (float)getHitBoxCenter().getX() - getTextureWidthHarpoon(), getY(), getTextureWidthHarpoon(), getTextureHeightHarpoon());
            } else {
                batch.draw(harpoon_animation.getKeyFrame(getAnimationTime(), false), (float)getHitBoxCenter().getX() + getTextureWidthHarpoon(),  getY(), -getTextureWidthHarpoon(), getTextureHeightHarpoon());
            }

            // Draw body.
            if (this.getIsFacing().isFacingLeft()) {
                batch.draw(attack_animation.getKeyFrame(getAnimationTime(), false), getX(), getY(), getTextureWidthBody(), getTextureHeightBody());
            } else {
                batch.draw(attack_animation.getKeyFrame(getAnimationTime(), false), getX() + getTextureWidthBody(), getY(), -getTextureWidthBody(), getTextureHeightBody());
            }

            // Draw Head, part 2.
            if (this.getIsFacing().isFacingLeft()) {
                batch.draw(head, getX() - getHeadXOffset() + (float)getHeadBob(getAnimationTime()).getX(), getY() + getHeadYOffset() + (float)getHeadBob(getAnimationTime()).getY(), getTextureWidthHead(), getTextureHeightHead());
            } else {
                batch.draw(head, getX() + getHeadXOffset() - (float)getHeadBob(getAnimationTime()).getX() + getTextureWidthHead(), getY() + getHeadYOffset() + (float)getHeadBob(getAnimationTime()).getY(), -getTextureWidthHead(), getTextureHeightHead());
            }
        }

        drawHealth(batch, alpha, -(int)getHitBoxWidth() / 2, -(int)getHitBoxHeight() / 2);
    }

    protected void initTextures(AssetManager assetManager){
        // Standing Textures.
        stand = assetManager.get(AssetList.HARPOONER_STAND.toString());

        //Walking Textures and Animations.
        walk = assetManager.get(AssetList.HARPOONER_WALK.toString());
        walk_animation = new Animation<TextureRegion>(1 / 10f, walk.getRegions(), Animation.PlayMode.LOOP);

        // Attacking Textures and Animation.
        attack = assetManager.get(AssetList.HARPOONER_ATTACK.toString());
        attack_animation = new Animation<TextureRegion>(1.0f /attack.getRegions().size * 0.2f, attack.getRegions());

        //  Harpoon Textures and Animation.
        harpoon = assetManager.get(AssetList.HARPOON.toString());
        harpoon_animation = new Animation<TextureRegion>(1.0f /harpoon.getRegions().size * 0.2f, harpoon.getRegions());

        // Head Textures.
        head = assetManager.get(AssetList.HARPOONER_HEAD.toString());
    }

    private void attack(){
        setAnimationTime(0);
        setAttacking(true);
        if (this.getIsFacing().isFacingLeft()) {
            getStage().addActor(new Melee(getAssetManager(), (float)getHitBoxCenter().getX() - 150, (float)getHitBoxCenter().getY() - 40, 150, 80, this.attackTime, 2, 1));
        } else {
            getStage().addActor(new Melee(getAssetManager(), (float)getHitBoxCenter().getX(), (float)getHitBoxCenter().getY() - 40, 150, 80, this.attackTime, 2, 1));
        }
    }

    // Head offsets for walking.
    private float getHorizontalHeadSway(float deltaTime){
        if (getVel().getX() == 0 && getVel().getY() == 0) {
            return 0;
        }
        switch(walk_animation.getKeyFrameIndex(deltaTime)){
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

    private Point2D getHeadBob(float deltaTime){
        switch(attack_animation.getKeyFrameIndex(deltaTime)){
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

    public float getTextureWidthHarpoon() {
        return textureWidthHarpoon;
    }

    public void setTextureWidthHarpoon(float textureWidthHarpoon) {
        this.textureWidthHarpoon = textureWidthHarpoon;
    }

    public float getTextureHeightHarpoon() {
        return textureHeightHarpoon;
    }

    public void setTextureHeightHarpoon(float textureHeightHarpoon) {
        this.textureHeightHarpoon = textureHeightHarpoon;
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
