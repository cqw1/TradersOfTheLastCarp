package com.totlc.Actors.projectiles;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.assets.loaders.ParticleEffectLoader;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.*;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.totlc.AssetList;

public class StarShot extends Projectile {

    // Asset and animation constants.
    private TextureAtlas shotTextureAtlas, particleAtlas;
    private Animation<TextureRegion> shotAnimation;
    private ParticleEffect star_trail;

    public StarShot(AssetManager assetManager, float x, float y){
        super(assetManager, x, y);

        setWidth(64);
        setHeight(64);
        initHitBox();
        setAttack(2);
        setScaleFactor(1.0f);

        assetManager.load(AssetList.PROJECTILE_STAR_SHOT.toString(), TextureAtlas.class);
        assetManager.load(AssetList.STAR_PARTICLES.toString(), TextureAtlas.class);
        star_trail = new ParticleEffect();
        star_trail.setPosition(getX(), getY());
    }

    @Override
    public void act(float deltaTime){
        increaseAnimationTime(deltaTime);
        moveUnit(deltaTime);
        if (isOutOfBounds()) {
            remove();
        }
        star_trail.setPosition(getX(), getY());
    }

    @Override
    public void draw(Batch batch, float alpha) {
        AssetManager assetManager = getAssetManager();

        if (assetManager.update() && !assetsLoaded()) {
            // Done loading. Instantiate all assets
            setAssetsLoaded(true);

            shotTextureAtlas = assetManager.get(AssetList.PROJECTILE_STAR_SHOT.toString());
            particleAtlas = assetManager.get(AssetList.STAR_PARTICLES.toString());
            shotAnimation = new Animation<TextureRegion>(1 / 16f, shotTextureAtlas.getRegions());
            star_trail.load(Gdx.files.internal(AssetList.STAR_TRAIL.toString()), particleAtlas);
        }

        if (assetsLoaded()) {
            batch.draw(shotAnimation.getKeyFrame(getAnimationTime(), true), getX(), getY());
            star_trail.draw(batch);
        }
    }

    @Override
    public boolean collidesWith(Actor otherActor) {
        return false;
    }

    @Override
    public void endCollidesWith(Actor otherActor) {

    }
}
