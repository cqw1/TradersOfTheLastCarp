package com.totlc.Actors.enemies;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.*;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.totlc.Actors.damage.Damage;
import com.totlc.Actors.effects.FlanParts;
import com.totlc.Actors.enemies.movement.AMovement;
import com.totlc.Actors.enemies.movement.IntervalMovement;
import com.totlc.AssetList;

/**
 * Slime enemy that splits upon taking damage. Copies have half health.
 * Size scales with health.
 * If only one health remains when hit, it dies.
 * Moves around randomly in a small area. Will jump at player if they get close.
 * Jump leaves behind a sticky syrup puddle that slows movement.
 */
public class JustDessert extends AEnemy{

    // Stat constants.
    private static int id = 2;
    private static int basehp = 7;
    private static int atk = 1;

    //Size constants.
    private static float width = 64, height = 64;
    private float scale;

    private static float maxVel = 200;
    private static float speed = 40;
    private static float friction = 0.2f;

    // Asset and animation constants.
    private TextureAtlas walkTextureAtlas, walkTextureAtlasP, jumpTextureAtlas, jumpTextureAtlasP;
    private Animation<TextureRegion> walkAnimation, walkAnimationP, jumpAnimation, jumpAnimationP;

    /**
     * Constructor for a default slime
     *
     * @param assetManager
     * @param x
     * @param y
     */
    public JustDessert(AssetManager assetManager, float x, float y, AMovement movement) {
        this(assetManager, x, y, basehp, movement);
    }

    /**
     * Constructor for custom-sized enemy
     */
    public JustDessert(AssetManager assetManager, float x, float y, int hp, AMovement movement) {
        super(assetManager, new Rectangle(x, y, width, height), movement, hp, atk);
        initTextures(assetManager);

        this.scale = (float) ((Math.log(hp + 1) / Math.log(2)) / (Math.log(basehp + 1) / Math.log(2)));
        getHitBox().setScale(scale, scale);
        moveHitBox(getWidth() * 0.5f * scale, 0);
        initMovement(friction, maxVel, speed);
    }

    @Override
    public void draw(Batch batch, float alpha) {
        drawHealth(batch, alpha, -(int)getHitBoxWidth() / 2, -(int)getHitBoxHeight() / 2);
        if (getHpCurrent() == 7){
            if (getMovement().isAttack()){
                batch.draw(jumpAnimationP.getKeyFrame(getAnimationTime(), true), getX(), getY(),
                        0,
                        0,
                        (float) jumpTextureAtlasP.getRegions().get(0).getRegionWidth(),
                        (float) jumpTextureAtlasP.getRegions().get(0).getRegionHeight(), scale, scale, 0);
            } else{
                batch.draw(walkAnimationP.getKeyFrame(getAnimationTime(), true), getX(), getY(),
                        0,
                        0,
                        (float) walkTextureAtlasP.getRegions().get(0).getRegionWidth(),
                        (float) walkTextureAtlasP.getRegions().get(0).getRegionHeight(), scale, scale, 0);
            }
        } else{
            if (getMovement().isAttack()){
                batch.draw(jumpAnimation.getKeyFrame(getAnimationTime(), true), getX(), getY(),
                        0,
                        0,
                        (float) jumpTextureAtlas.getRegions().get(0).getRegionWidth(),
                        (float) jumpTextureAtlas.getRegions().get(0).getRegionHeight(), scale, scale, 0);
            } else{
                batch.draw(walkAnimation.getKeyFrame(getAnimationTime(), true), getX(), getY(),
                        0,
                        0,
                        (float) walkTextureAtlas.getRegions().get(0).getRegionWidth(),
                        (float) walkTextureAtlas.getRegions().get(0).getRegionHeight(), scale, scale, 0);
            }
        }
        drawStatuses(batch, alpha);
    }

    @Override
    public boolean collidesWith(Actor otherActor){
        boolean returnMe = super.collidesWith(otherActor);
        if (otherActor instanceof Damage && ((Damage)otherActor).getDamageType() != 1) {
            if (!isInvincible()) {
                int newHP = (int) Math.floor(getHpCurrent() * 0.5);
                if (newHP > 0 && getHpCurrent() > 1){
                    getStage().addActor(new JustDessert(getAssetManager(), getX() - 48, getY(), newHP, getMovement()));
                    getStage().addActor(new JustDessert(getAssetManager(), getX() + 48, getY(), newHP, getMovement()));
                    returnMe =  true;
                }
            }
            getStage().addActor(new FlanParts(getAssetManager(), new Rectangle(getX(), getY(), 100, 100), getHpMax() == basehp));
        }
        return returnMe;
    }

    private void initTextures(AssetManager assetManager){
        walkTextureAtlas = assetManager.get(AssetList.FLAN.toString());
        walkAnimation = new Animation<TextureRegion>(1 / 16f, walkTextureAtlas.getRegions());
        walkTextureAtlasP = assetManager.get(AssetList.FLAN_PRIME.toString());
        walkAnimationP = new Animation<TextureRegion>(1 / 16f, walkTextureAtlasP.getRegions());
        jumpTextureAtlas = assetManager.get(AssetList.FLAN_JUMP.toString());
        jumpAnimation = new Animation<TextureRegion>(1 / 20f, jumpTextureAtlas.getRegions());
        jumpTextureAtlasP = assetManager.get(AssetList.FLAN_JUMP_PRIME.toString());
        jumpAnimationP = new Animation<TextureRegion>(1 / 20f, jumpTextureAtlasP.getRegions());
        for (int i = 0; i < walkTextureAtlas.getRegions().size; i++){
            walkTextureAtlas.getRegions().get(i).getTexture().setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
        }
    }
}
