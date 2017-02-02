package com.totlc.Actors.effects;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.ParticleEffect;
import com.badlogic.gdx.graphics.g2d.ParticleEmitter;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.totlc.AssetList;

public class ArrowBreak extends AEffect{

    // Texture information
    private TextureAtlas particleAtlas;
    private ParticleEffect arrowBreak;

    private long startTime;

    public ArrowBreak(AssetManager assetManager, float x, float y) {
        super(assetManager, new Rectangle(x, y, 1, 1));
        loadAssets(assetManager);
        arrowBreak.setPosition(getX(), getY());
        this.startTime = System.currentTimeMillis();
        Sound impactSound = Gdx.audio.newSound(Gdx.files.internal("sounds/clang0.mp3"));
        impactSound.play(1.0f);
    }

    @Override
    public void act(float deltaTime){
        super.act(deltaTime);
    }

    @Override
    public void draw(Batch batch, float alpha) {
        arrowBreak.draw(batch, Gdx.graphics.getDeltaTime());
        if(System.currentTimeMillis() - startTime > arrowBreak.findEmitter("arrowpoint").getLife().getHighMax()){
            remove();
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
        arrowBreak = new ParticleEffect();
        arrowBreak.setPosition(getX(), getY());
        arrowBreak.load(Gdx.files.internal(AssetList.ARROW_BREAK.toString()), particleAtlas);
        ParticleEmitter point = arrowBreak.findEmitter("arrowpoint");
        ParticleEmitter shaft = arrowBreak.findEmitter("arrowshaft");

        // Configure effect duration.

        arrowBreak.start();
    }
}
