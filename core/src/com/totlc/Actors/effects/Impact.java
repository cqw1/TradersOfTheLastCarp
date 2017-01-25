package com.totlc.Actors.effects;


import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Polygon;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.totlc.AssetList;

public class Impact extends AEffect {

    // Asset and animation constants.
    private TextureAtlas impactTextureAtlas;
    private Animation<TextureRegion> impactAnimation;

    public Impact(AssetManager assetManager, float x, float y) {
        super(assetManager, new Rectangle(x, y, 1, 1));

        assetManager.load(AssetList.IMPACT.toString(), TextureAtlas.class);
    }

    @Override
    public void act(float deltaTime){
        increaseAnimationTime(deltaTime);
    }

    @Override
    public void draw(Batch batch, float alpha) {
        AssetManager assetManager = getAssetManager();

        if (assetManager.update() && !assetsLoaded()) {
            // Done loading. Instantiate all assets
            setAssetsLoaded(true);

            impactTextureAtlas = assetManager.get(AssetList.IMPACT.toString());
            impactAnimation = new Animation<TextureRegion>(1 / 16f, impactTextureAtlas.getRegions());
        }

        if (assetsLoaded()) {
            batch.draw(impactAnimation.getKeyFrame(getAnimationTime(), false), getX(), getY());
            if (impactAnimation.isAnimationFinished(getAnimationTime())){
                remove();
            }
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
