package com.totlc.status;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.ParticleEffect;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.totlc.Actors.TotlcObject;
import com.totlc.AssetList;

public class Invulnerable extends AStatus {

    // Texture information
    private TextureAtlas particleAtlas;
    private ParticleEffect shield;

    public Invulnerable(AssetManager assetManager, TotlcObject followMe) {
        super(assetManager, followMe);
        loadAssets(assetManager);
    }

    @Override
    public void act(float deltaTime){
        super.act(deltaTime);
        shield.setPosition(getX(), getY());
    }

    @Override
    public void draw(Batch batch, float alpha) {
        shield.draw(batch, Gdx.graphics.getDeltaTime());
    }

    @Override
    public void proc() {

    }

    private void loadAssets(AssetManager assetManager){
        particleAtlas = assetManager.get(AssetList.PARTICLES.toString());
        shield = new ParticleEffect();
        shield.setPosition((float) getFollowMe().getHitBoxCenter().getX(), (float) getFollowMe().getHitBoxCenter().getY());
        shield.load(Gdx.files.internal(AssetList.SHIELD.toString()), particleAtlas);
        shield.start();
    }
}
