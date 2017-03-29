package com.totlc.Actors.effects;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.ParticleEffect;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.totlc.AssetList;

public class BoulderBreak extends AEffect{


    // Texture information
    private TextureAtlas particleAtlas;
    private ParticleEffect debris;

    public BoulderBreak(AssetManager assetManager, float x, float y) {
        this(assetManager, new Rectangle(x, y, 1, 1));
        loadAssets(assetManager);
        debris.setPosition(getX(), getY());
    }

    public BoulderBreak(AssetManager assetManager, Rectangle r) {
        super(assetManager, r);
    }

    @Override
    public void act(float deltaTime){
        increaseAnimationTime(deltaTime);
        debris.setPosition(getX(), getY());
        if(debris.isComplete()){
            remove();
        }
    }

    @Override
    public void draw(Batch batch, float alpha) {
        debris.draw(batch, Gdx.graphics.getDeltaTime());
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
        debris = new ParticleEffect();
        debris.load(Gdx.files.internal(AssetList.BOULDER_BREAK.toString()), particleAtlas);
        debris.start();
    }
}
