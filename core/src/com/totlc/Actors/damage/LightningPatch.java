package com.totlc.Actors.damage;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.ParticleEffect;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.totlc.AssetList;

public class LightningPatch extends Damage {

    // Texture information
    private TextureAtlas particleAtlas;
    private ParticleEffect electricity;

    private long lifeSpan = 8000;
    private long startTime;
    private Sound sound = Gdx.audio.newSound(Gdx.files.internal(AssetList.ELECTRICITY_SOUND.toString()));

    public LightningPatch(AssetManager assetManager, float x, float y, float width, float height, int damageType) {
        super(assetManager, new Rectangle(x, y, width, height), 1, damageType);
        loadAssets(assetManager);
        this.startTime = System.currentTimeMillis();
        sound.play(0.3f);
    }

    @Override
    public void act(float deltaTime){
        if (getTimeToApplyDamage() > System.currentTimeMillis()) {
            setTimeToApplyDamage(getTimeToApplyDamage() + getDamageInterval());
        }
        increaseAnimationTime(deltaTime);
        if (System.currentTimeMillis() - startTime > lifeSpan){
            electricity.allowCompletion();
        }
        if (electricity.isComplete()){
            sound.stop();
            sound.dispose();
            remove();
        }
        electricity.setPosition(getX(), getY());
    }

    @Override
    public void draw(Batch batch, float alpha) {
        electricity.draw(batch, Gdx.graphics.getDeltaTime());
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
        electricity = new ParticleEffect();
        electricity.setPosition(getX(), getY());
        moveHitBox(-getHitBoxWidth() * 0.5f, -getHitBoxHeight() * 0.5f);
        electricity.load(Gdx.files.internal(AssetList.ELECTRICITY.toString()), particleAtlas);

        electricity.start();
    }
}
