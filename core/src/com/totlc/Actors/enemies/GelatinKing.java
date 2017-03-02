package com.totlc.Actors.enemies;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.totlc.Actors.damage.Damage;
import com.totlc.Actors.damage.DamageEnum;
import com.totlc.Actors.damage.DamageFactory;
import com.totlc.Actors.damage.LightningPatch;
import com.totlc.Actors.enemies.movement.AMovement;
import com.totlc.AssetList;

import java.awt.geom.Point2D;

public class GelatinKing extends AEnemy {

    // Stat constants.
    private static int id = 2;
    private static int basehp = 1;
    private static int atk = 1;

    // Asset and animation constants.
    private TextureAtlas jellyTextureAtlas, kingTextureAtlas;
    private Animation<TextureRegion> jellyAnimation, kingAnimation;
    private Texture shadow;

    private static float maxVel = 2000;
    private static float speed = 10;
    private static float friction = 0.9f;
    private static long hazardSetTime = 2000;

    private long timeStamp;

    private boolean king;
    private float scale;
    private float shadowSize = 0.8f;

    private static float width = 160;
    private static float height = 250;

    public GelatinKing(AssetManager assetManager, float x, float y, AMovement movement) {
        super(assetManager, new Rectangle(x, y, width, height), movement, basehp, atk);
        jellyTextureAtlas = assetManager.get(AssetList.JELLYFISH.toString());
        jellyAnimation = new Animation<TextureRegion>(1 / 16f, jellyTextureAtlas.getRegions());
        kingTextureAtlas = assetManager.get(AssetList.GELATIN_KING.toString());
        kingAnimation = new Animation<TextureRegion>(1 / 16f, kingTextureAtlas.getRegions());
        shadow = getAssetManager().get(AssetList.SHADOW.toString());
        this.king = false;
        this.scale = 0.5f;
        this.timeStamp = System.currentTimeMillis();
        getHitBox().setScale(this.scale, this.scale);
        initMovement(friction, maxVel, speed);
        setFloating(true);

        for (int i = 0; i < jellyTextureAtlas.getRegions().size; i++){
           jellyTextureAtlas.getRegions().get(i).getTexture().setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
           kingTextureAtlas.getRegions().get(i).getTexture().setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
        }
    }

    public GelatinKing(AssetManager assetManager, float x, float y, AMovement movement, boolean crownMe) {
        super(assetManager, new Rectangle(x, y, width, height), movement, basehp, atk);
        jellyTextureAtlas = assetManager.get(AssetList.JELLYFISH.toString());
        jellyAnimation = new Animation<TextureRegion>(1 / 16f,jellyTextureAtlas.getRegions());
        kingTextureAtlas = assetManager.get(AssetList.GELATIN_KING.toString());
        kingAnimation = new Animation<TextureRegion>(1 / 16f, kingTextureAtlas.getRegions());
        shadow = getAssetManager().get(AssetList.SHADOW.toString());
        this.king = crownMe;
        if (crownMe){
            this.scale = 1;
            setHpMax(getHpMax() + 2);
            setHpCurrent(getHpCurrent() + 2);
            setAttack(getAttack() + 1);
        } else{
            this.scale = 0.5f;
        }
        this.timeStamp = System.currentTimeMillis();
        getHitBox().setScale(this.scale, this.scale);
        initMovement(friction, maxVel, speed);
        setFloating(true);

        for (int i = 0; i < jellyTextureAtlas.getRegions().size; i++){
            jellyTextureAtlas.getRegions().get(i).getTexture().setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
            kingTextureAtlas.getRegions().get(i).getTexture().setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
        }
    }

    @Override
    public void act(float deltaTime){
        super.act(deltaTime);
        if (System.currentTimeMillis() - timeStamp > hazardSetTime && this.king){
            timeStamp = System.currentTimeMillis();
            setLightningPatch();
        }
    }

    @Override
    public void draw(Batch batch, float alpha) {
        drawHealth(batch, alpha, -(int)getHitBoxWidth() / 2, -(int)getHitBoxHeight() / 2);
        if(this.king){
            batch.draw(kingAnimation.getKeyFrame(getAnimationTime(), true), getX(), getY(),
                    0,
                    0,
                    (float) kingTextureAtlas.getRegions().get(0).getRegionWidth(),
                    (float) kingTextureAtlas.getRegions().get(0).getRegionHeight(), scale, scale, 0);
        } else{
            batch.draw(jellyAnimation.getKeyFrame(getAnimationTime(), true), getX(), getY(),
                    0,
                    0,
                    (float) jellyTextureAtlas.getRegions().get(0).getRegionWidth(),
                    (float) jellyTextureAtlas.getRegions().get(0).getRegionHeight(), scale, scale, 0);
        }
        batch.draw(shadow, (float) getHitBoxCenter().getX() - (shadow.getWidth() * scale * shadowSize) / 2, (float)getHitBoxCenter().getY() - getHeight() * scale * 0.5f, shadow.getWidth() * scale * shadowSize, shadow.getHeight() * scale * shadowSize);
    }

    // Drop floor hazard.
    private void setLightningPatch(){
        Damage jellyDamage = DamageFactory.createDamage(DamageEnum.LIGHTNING_PATCH, new Point2D.Double(0, 0), getAssetManager(), (float) getHitBoxCenter().getX(), (float) getHitBoxCenter().getY(), 1);
        getStage().addActor(jellyDamage);
        jellyDamage.setZIndex(1);
    }
}
