package com.totlc.Actors.effects;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.ParticleEffect;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.totlc.AssetList;

public class RockFall extends AEffect{
    // Texture information
    private TextureAtlas particleAtlas;
    private ParticleEffect rocks;

    public RockFall(AssetManager assetManager, float x, float y) {
        this(assetManager, new Rectangle(x, y, 1, 1));
        loadAssets(assetManager);
        rocks.setPosition(getX(), getY());
    }

    public RockFall(AssetManager assetManager, Rectangle r) {
        super(assetManager, r);
    }

    @Override
    public void act(float deltaTime){
        increaseAnimationTime(deltaTime);
        rocks.setPosition(getX(), getY());
        if(rocks.isComplete()){
            remove();
        }
    }

    @Override
    public void draw(Batch batch, float alpha) {
        rocks.draw(batch, Gdx.graphics.getDeltaTime());
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
        rocks = new ParticleEffect();
        rocks.load(Gdx.files.internal(AssetList.DEBRIS_DROP.toString()), particleAtlas);
        rocks.start();
    }
}
