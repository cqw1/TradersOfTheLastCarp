package com.totlc.Actors.effects;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.ParticleEffect;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.totlc.Actors.TotlcObject;
import com.totlc.AssetList;

public class Smoke extends AEffect {

    // Texture information
    private TextureAtlas particleAtlas;
    private ParticleEffect smoke;

    private TotlcObject followMe;

    public Smoke(AssetManager assetManager, TotlcObject actor, float x, float y) {
        super(assetManager, new Rectangle(x, y, 1, 1));
        this.followMe = actor;
        loadAssets(assetManager);
    }

    @Override
    public void act(float deltaTime){
        increaseAnimationTime(deltaTime);
        moveAbs((float)followMe.getHitBoxCenter().getX() + followMe.getHitBoxWidth() / 2, (float)followMe.getHitBoxCenter().getY() + followMe.getHitBoxHeight() / 2);
        smoke.setPosition(getX(), getY());
    }

    @Override
    public void draw(Batch batch, float alpha) {
        smoke.draw(batch, Gdx.graphics.getDeltaTime());
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
        smoke = new ParticleEffect();
        smoke.setPosition(getX(), getY());
        smoke.load(Gdx.files.internal(AssetList.SMOKE.toString()), particleAtlas);
        smoke.start();
    }
}
