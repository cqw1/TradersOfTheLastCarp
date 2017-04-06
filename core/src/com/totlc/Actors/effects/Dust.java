package com.totlc.Actors.effects;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.ParticleEffect;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.totlc.AssetList;

public class Dust extends AEffect {

    // Texture information.
    private ParticleEffect dust;
    private TextureAtlas particleAtlas;

    public Dust(AssetManager assetManager,float x, float y) {
        super(assetManager, new Rectangle(x, y, 1, 1));
        loadAssets(assetManager);
        dust.setPosition((float) getHitBoxCenter().getX(), (float)getHitBoxCenter().getY());
    }

    @Override
    public void act(float deltaTime) {
        super.act(deltaTime);
        dust.setPosition((float) getHitBoxCenter().getX(), (float)getHitBoxCenter().getY());
    }

    @Override
    public void draw(Batch batch, float alpha) {
        if(dust.isComplete()){
            remove();
        }
        dust.draw(batch, Gdx.graphics.getDeltaTime());
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
        dust = new ParticleEffect();
        dust.setPosition(getX(), getY());
        dust.load(Gdx.files.internal(AssetList.DUST.toString()), particleAtlas);

        dust.start();
    }
}
