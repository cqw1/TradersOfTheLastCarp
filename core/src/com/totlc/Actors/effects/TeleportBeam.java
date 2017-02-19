package com.totlc.Actors.effects;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.ParticleEffect;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.totlc.AssetList;

public class TeleportBeam extends AEffect {

    // Texture information.
    private ParticleEffect beam;
    private TextureAtlas particleAtlas;
    private static long duration = 300;
    private long startTime;

    public TeleportBeam(AssetManager assetManager, float x, float y) {
        super(assetManager, new Rectangle(x, y, 1, 1));
        loadAssets(assetManager);
        beam.setPosition((float) getHitBoxCenter().getX(), (float)getHitBoxCenter().getY());
        this.startTime = System.currentTimeMillis();
    }

    @Override
    public void act(float deltaTime) {
        super.act(deltaTime);
        if (System.currentTimeMillis() - this.startTime > duration){
            beam.allowCompletion();
        }
        beam.setPosition((float) getHitBoxCenter().getX(), (float)getHitBoxCenter().getY());
    }

    @Override
    public void draw(Batch batch, float alpha) {
        if(beam.isComplete()){
            remove();
        }
        beam.draw(batch, Gdx.graphics.getDeltaTime());
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
        beam = new ParticleEffect();
        beam.setPosition(getX(), getY());
        beam.load(Gdx.files.internal(AssetList.TELEPORT_BEAM.toString()), particleAtlas);

        beam.start();
    }
}
