package com.totlc.Actors.projectiles;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.ParticleEffect;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.totlc.Actors.TotlcObject;
import com.totlc.AssetList;

public abstract class FireStream extends Projectile {

    // Texture information
    private TextureAtlas particleAtlas;
    private ParticleEffect fire;

    private TotlcObject followMe;

    public FireStream(AssetManager assetManager, TotlcObject actor, float x, float y) {
        super(assetManager, new Rectangle(x, y, 30, 100));
        loadAssets(assetManager);
        this.followMe = actor;
        fire.setPosition(getX(), getY());
    }

    @Override
    public void act(float deltaTime){
        increaseAnimationTime(deltaTime);
        moveAbs((float)followMe.getHitBoxCenter().getX() + followMe.getHitBoxWidth() / 2, (float)followMe.getHitBoxCenter().getY() + followMe.getHitBoxHeight() / 2);
        fire.setPosition(getX(), getY());
    }

    @Override
    public void draw(Batch batch, float alpha) {
        fire.draw(batch, Gdx.graphics.getDeltaTime());
    }

    @Override
    public boolean collidesWith(Actor otherActor) {
        return false;
    }

    @Override
    public void endCollidesWith(Actor otherActor) {

    }

    /**
     * Extend this method with orientation setup for directional fire streams. (Default direction RIGHT)
     * @param assetManager: Assetmanager to load assets from.
     */
    private void loadAssets(AssetManager assetManager){
        particleAtlas = assetManager.get(AssetList.PARTICLES.toString());
        fire = new ParticleEffect();
        fire.setPosition(getX(), getY());
        fire.load(Gdx.files.internal(AssetList.FLAMETHROWER.toString()), particleAtlas);
        // Start effect in concrete implementations.
        setDirection();
    }

    public abstract void setDirection();

    public void endEffect(){
        fire.allowCompletion();
    }
}
