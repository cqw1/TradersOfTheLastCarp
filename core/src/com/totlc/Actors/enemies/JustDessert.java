package com.totlc.Actors.enemies;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.*;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.totlc.Actors.effects.FlanParts;
import com.totlc.Actors.enemies.movement.AMovement;
import com.totlc.Actors.enemies.movement.IntervalMovement;
import com.totlc.Actors.projectiles.Projectile;
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

    private static float maxVel = 100;
    private static float speed = 20;
    private static float friction = 0.8f;

    // Asset and animation constants.
    private TextureAtlas walkTextureAtlas, walkTextureAtlasP;
    private Animation<TextureRegion> walkAnimation, walkAnimationP;

    /**
     * Constructor for a default slime
     *
     * @param assetManager
     * @param x
     * @param y
     */
    public JustDessert(AssetManager assetManager, float x, float y, AMovement movement) {
        super(assetManager, new Rectangle(x, y, width, height), movement, basehp, atk);
        walkTextureAtlas = assetManager.get(AssetList.FLAN.toString());
        walkAnimation = new Animation<TextureRegion>(1 / 16f, walkTextureAtlas.getRegions());
        walkTextureAtlasP = assetManager.get(AssetList.FLAN_PRIME.toString());
        walkAnimationP = new Animation<TextureRegion>(1 / 16f, walkTextureAtlasP.getRegions());

        this.scale = 1;
        for (int i = 0; i < walkTextureAtlas.getRegions().size; i++){
            walkTextureAtlas.getRegions().get(i).getTexture().setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
        }
        initMovement(friction, maxVel, speed);
    }

    /**
     * Constructor for custom-sized enemy
     */
    public JustDessert(AssetManager assetManager, float x, float y, int hp, AMovement movement) {
        super(assetManager, new Rectangle(x, y, width, height), movement, hp, atk);
        walkTextureAtlas = assetManager.get(AssetList.FLAN.toString());
        walkAnimation = new Animation<TextureRegion>(1 / 16f, walkTextureAtlas.getRegions());
        walkTextureAtlasP = assetManager.get(AssetList.FLAN_PRIME.toString());

        walkAnimationP = new Animation<TextureRegion>(1 / 16f, walkTextureAtlasP.getRegions());

        this.scale = (float) ((Math.log(hp + 1) / Math.log(2)) / (Math.log(basehp + 1) / Math.log(2)));
        getHitBox().setScale(scale, scale);
        initMovement(friction, maxVel, speed);
    }

    @Override
    public void act(float deltaTime) {
        super.act(deltaTime);
    }

    @Override
    public void draw(Batch batch, float alpha) {
        drawHealth(batch, alpha, -(int)getHitBoxWidth() / 2, -(int)getHitBoxHeight() / 2);
        if (getHpCurrent() == 7){
            batch.draw(walkAnimationP.getKeyFrame(getAnimationTime(), true), getX(), getY(),
                    (float) walkTextureAtlasP.getRegions().get(0).getRegionWidth() * 0.5f,
                    (float) walkTextureAtlasP.getRegions().get(0).getRegionHeight() * 0.5f,
                    (float) walkTextureAtlasP.getRegions().get(0).getRegionWidth(),
                    (float) walkTextureAtlasP.getRegions().get(0).getRegionHeight(), scale, scale, 0);
        } else{
            batch.draw(walkAnimation.getKeyFrame(getAnimationTime(), true), getX(), getY(),
                    (float) walkTextureAtlas.getRegions().get(0).getRegionWidth() * 0.5f,
                    (float) walkTextureAtlas.getRegions().get(0).getRegionHeight() * 0.5f,
                    (float) walkTextureAtlas.getRegions().get(0).getRegionWidth(),
                    (float) walkTextureAtlas.getRegions().get(0).getRegionHeight(), scale, scale, 0);
        }
        drawStatuses(batch, alpha);
    }

    @Override
    public boolean collidesWith(Actor otherActor){
        boolean returnMe = super.collidesWith(otherActor);
        if (otherActor instanceof Projectile && ((Projectile)otherActor).getDamageType() != 1) {
            if (!isInvincible()) {
                int newHP = (int) Math.floor(getHpCurrent() * 0.5);
                if (newHP > 0 && getHpCurrent() > 1){
                    getStage().addActor(new JustDessert(getAssetManager(), getX() - 16, getY(), newHP, new IntervalMovement(getMovement().getTarget())));
                    getStage().addActor(new JustDessert(getAssetManager(), getX() + 16, getY(), newHP, new IntervalMovement(getMovement().getTarget())));
                    returnMe =  true;
                }
            }
            getStage().addActor(new FlanParts(getAssetManager(), new Rectangle(getX(), getY(), 100, 100), getHpMax() == basehp));
        }
        return returnMe;
    }

}
