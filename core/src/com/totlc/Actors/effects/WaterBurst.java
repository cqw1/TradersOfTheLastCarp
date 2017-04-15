package com.totlc.Actors.effects;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.ParticleEffect;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.totlc.AssetList;

public class WaterBurst extends AEffect {

    // Texture information.
    private ParticleEffect water;
    private TextureAtlas particleAtlas;

    public WaterBurst(AssetManager assetManager, float x, float y) {
        super(assetManager, new Rectangle(x, y, 1, 1));
        loadAssets(assetManager);
        water.setPosition((float) getHitBoxCenter().getX(), (float)getHitBoxCenter().getY());
    }

    @Override
    public void act(float deltaTime) {
        super.act(deltaTime);
        water.setPosition((float) getHitBoxCenter().getX(), (float)getHitBoxCenter().getY());
    }
    
    @Override
    public void draw(Batch batch, float alpha) {
        if(water.isComplete()){
            remove();
        }
        water.draw(batch, Gdx.graphics.getDeltaTime());
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
        water = new ParticleEffect();
        water.setPosition(getX(), getY());
        water.load(Gdx.files.internal(AssetList.WATER_BURST.toString()), particleAtlas);
        

        water.start();
    }
}
