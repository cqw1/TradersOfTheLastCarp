package com.totlc.Actors.effects;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.ParticleEffect;
import com.badlogic.gdx.graphics.g2d.ParticleEmitter;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.totlc.AssetList;

public class FlanParts extends AEffect {

    // Texture information.
    private ParticleEffect flan_parts, flan_parts_prime;
    private TextureAtlas particleAtlas;

    private boolean prime;

    public FlanParts(AssetManager assetManager, Rectangle r, boolean prime) {
        super(assetManager, r);
        this.prime = prime;
        loadAssets(assetManager);
        flan_parts.setPosition((float) getHitBoxCenter().getX(), (float)getHitBoxCenter().getY());
        flan_parts_prime.setPosition((float) getHitBoxCenter().getX(), (float)getHitBoxCenter().getY());
    }

    @Override
    public void act(float deltaTime) {
        super.act(deltaTime);
        flan_parts.setPosition((float) getHitBoxCenter().getX(), (float)getHitBoxCenter().getY());
        flan_parts_prime.setPosition((float) getHitBoxCenter().getX(), (float)getHitBoxCenter().getY());
    }

    @Override
    public void draw(Batch batch, float alpha) {
        if (prime){
            if(flan_parts_prime.isComplete()){
                remove();
            }
            flan_parts_prime.draw(batch, Gdx.graphics.getDeltaTime());
        } else{
            if(flan_parts.isComplete()){
                remove();
            }
            flan_parts.draw(batch, Gdx.graphics.getDeltaTime());
        }
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
        flan_parts = new ParticleEffect();
        flan_parts.setPosition(getX(), getY());
        flan_parts.load(Gdx.files.internal(AssetList.FLAN_PARTS_0.toString()), particleAtlas);

        flan_parts_prime = new ParticleEffect();
        flan_parts_prime.setPosition(getX(), getY());
        flan_parts_prime.load(Gdx.files.internal(AssetList.FLAN_PARTS_1.toString()), particleAtlas);

        flan_parts.start();
        flan_parts_prime.start();
    }
}
