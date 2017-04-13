package com.totlc.Actors.effects;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.ParticleEffect;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.totlc.AssetList;

public class BulletCasing extends AEffect {

    // Texture information.
    private ParticleEffect bulletCasing;
    private TextureAtlas particleAtlas;

    public BulletCasing(AssetManager assetManager, float x, float y) {
        super(assetManager, new Rectangle(x, y, 1, 1));
        loadAssets(assetManager);
        bulletCasing.setPosition(getX(), getY());
    }

    @Override
    public void act(float deltaTime) {
        super.act(deltaTime);
        bulletCasing.setPosition(getX(), getY());
    }

    @Override
    public void draw(Batch batch, float alpha) {
        if(bulletCasing.isComplete()){
            remove();
        }
        bulletCasing.draw(batch, Gdx.graphics.getDeltaTime());
    }

    @Override
    public boolean collidesWith(Actor otherActor) {
        return false;
    }

    @Override
    public void endCollidesWith(Actor otherActor) {

    }

    private void loadAssets(AssetManager assetManager){
        particleAtlas = assetManager.get(AssetList.PARTICLES.toString());
        bulletCasing = new ParticleEffect();
        bulletCasing.setPosition(getX(), getY());
        bulletCasing.load(Gdx.files.internal(AssetList.BULLET_CASING.toString()), particleAtlas);

        bulletCasing.start();
    }
}
