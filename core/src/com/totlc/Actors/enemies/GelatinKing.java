package com.totlc.Actors.enemies;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.totlc.Actors.enemies.movement.AMovement;
import com.totlc.AssetList;

public class GelatinKing extends AEnemy {

    // Stat constants.
    private static int id = 2;
    private static int basehp = 1;
    private static int atk = 1;

    // Asset and animation constants.
    private TextureAtlas jellyTextureAtlas, kingTextureAtlas;
    private Animation<TextureRegion> jellyAnimation, kingAnimation;

    private static float maxVel = 500;
    private static float speed = 20;
    private static float friction = 0.9f;

    private boolean king;
    private float scale;

    public GelatinKing(AssetManager assetManager, float x, float y, AMovement movement) {
        super(assetManager, new Rectangle(x, y, 160, 250), movement, basehp, atk);
        jellyTextureAtlas = assetManager.get(AssetList.JELLYFISH.toString());
        jellyAnimation = new Animation<TextureRegion>(1 / 16f, jellyTextureAtlas.getRegions());
        kingTextureAtlas = assetManager.get(AssetList.GELATIN_KING.toString());
        kingAnimation = new Animation<TextureRegion>(1 / 16f, kingTextureAtlas.getRegions());
        this.king = false;
        this.scale = 0.5f;
        getHitBox().setScale(this.scale, this.scale);
        initMovement(friction, maxVel, speed);
    }

    public GelatinKing(AssetManager assetManager, float x, float y, AMovement movement, boolean crownMe) {
        super(assetManager, new Rectangle(x, y, 160, 250), movement, basehp, atk);
        jellyTextureAtlas = assetManager.get(AssetList.JELLYFISH.toString());
        jellyAnimation = new Animation<TextureRegion>(1 / 16f,jellyTextureAtlas.getRegions());
        kingTextureAtlas = assetManager.get(AssetList.GELATIN_KING.toString());
        kingAnimation = new Animation<TextureRegion>(1 / 16f, kingTextureAtlas.getRegions());
        this.king = crownMe;
        if (crownMe){
            this.scale = 1;
            setHpMax(getHpMax() + 2);
            setHpCurrent(getHpCurrent() + 2);
            setAttack(getAttack() + 1);
        } else{
            this.scale = 0.5f;
        }
        getHitBox().setScale(this.scale, this.scale);
        initMovement(friction, maxVel, speed);
    }

    @Override
    public void draw(Batch batch, float alpha) {
        if(this.king){
            batch.draw(kingAnimation.getKeyFrame(getAnimationTime(), true), getX(), getY(),
                    (float) kingTextureAtlas.getRegions().get(0).getRegionWidth() * 0.5f,
                    (float) kingTextureAtlas.getRegions().get(0).getRegionHeight() * 0.5f,
                    (float) kingTextureAtlas.getRegions().get(0).getRegionWidth(),
                    (float) kingTextureAtlas.getRegions().get(0).getRegionHeight(), scale, scale, 0);
        } else{
            batch.draw(jellyAnimation.getKeyFrame(getAnimationTime(), true), getX(), getY(),
                    (float) jellyTextureAtlas.getRegions().get(0).getRegionWidth() * 0.5f,
                    (float) jellyTextureAtlas.getRegions().get(0).getRegionHeight() * 0.5f,
                    (float) jellyTextureAtlas.getRegions().get(0).getRegionWidth(),
                    (float) jellyTextureAtlas.getRegions().get(0).getRegionHeight(), scale, scale, 0);
        }
    }
}
