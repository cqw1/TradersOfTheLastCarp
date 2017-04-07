package com.totlc.Actors.effects;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.ParticleEffect;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.totlc.AssetList;

public class Sparks extends AEffect {

    // Texture information.
    private ParticleEffect sparks;
    private TextureAtlas particleAtlas;

    public Sparks(AssetManager assetManager,float x, float y) {
        super(assetManager, new Rectangle(x, y, 1, 1));
        loadAssets(assetManager);
        sparks.setPosition((float) getHitBoxCenter().getX(), (float)getHitBoxCenter().getY());
    }

    @Override
    public void act(float deltaTime) {
        super.act(deltaTime);
        sparks.setPosition((float) getHitBoxCenter().getX(), (float)getHitBoxCenter().getY());
    }

    @Override
    public void draw(Batch batch, float alpha) {
        if(sparks.isComplete()){
            remove();
        }
        sparks.draw(batch, Gdx.graphics.getDeltaTime());
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
        sparks = new ParticleEffect();
        sparks.setPosition(getX(), getY());
        sparks.load(Gdx.files.internal(AssetList.SPARKS.toString()), particleAtlas);

        sparks.start();
    }
}
