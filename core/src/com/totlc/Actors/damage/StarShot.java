package com.totlc.Actors.damage;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.g2d.*;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.totlc.Actors.players.Player;
import com.totlc.Actors.terrain.AWall;
import com.totlc.AssetList;
import com.totlc.TradersOfTheLastCarp;

import java.awt.geom.Point2D;

public class StarShot extends Damage {

    // Asset and animation constants.
    private TextureAtlas shotTextureAtlas, particleAtlas;
    private Animation<TextureRegion> shotAnimation;
    private ParticleEffectPool.PooledEffect starTrail;

    // Bookkeeping variables.
    private boolean removeFlag = false;
    private Point2D textureDimensions;
    private static int damage = 2;

    public StarShot(AssetManager assetManager, float x, float y, int damageType){
        super(assetManager, new Rectangle(x, y, 24, 24), damage, damageType);

        //TODO: Correct hitboxes?
        moveHitBox(42, 42);

        setDamageType(damageType);
        setScaleFactor(1.0f);

        shotTextureAtlas = assetManager.get(AssetList.PROJECTILE_STAR_SHOT.toString());
        shotAnimation = new Animation<TextureRegion>(1 / 16f, shotTextureAtlas.getRegions());

        textureDimensions = new Point2D.Float(shotAnimation.getKeyFrame(getAnimationTime()).getRegionWidth(), shotAnimation.getKeyFrame(getAnimationTime()).getRegionHeight());

        starTrail = TradersOfTheLastCarp.starTrailPool.obtain();
        starTrail.setPosition(getX() + (float)textureDimensions.getX() / 2, getY() + (float)textureDimensions.getY() / 2);

    }

    @Override
    public void act(float deltaTime){
        increaseAnimationTime(deltaTime);
        moveUnit(deltaTime);
        starTrail.setPosition(getX() + (float)textureDimensions.getX() / 2, getY() + (float)textureDimensions.getY() / 2);
        delayRemove();
    }

    @Override
    public void draw(Batch batch, float alpha) {
        if (!removeFlag) {
            batch.draw(shotAnimation.getKeyFrame(getAnimationTime(), true), getX(), getY());
        }
        starTrail.draw(batch, Gdx.graphics.getDeltaTime());
    }

    @Override
    public boolean collidesWith(Actor otherActor) {
        if (otherActor instanceof Player && !removeFlag) {
            allowRemoval();
        } else if (otherActor instanceof AWall){
            allowRemoval();
        }
        return false;
    }

    private void allowRemoval(){
        removeFlag = true;
        getHitBox().setScale(0, 0);
        for (ParticleEmitter p : starTrail.getEmitters()){
            p.allowCompletion();
        }
    }

    private boolean delayRemove() {
        if (starTrail.isComplete()) {
            starTrail.free();
        }

        return starTrail.isComplete() && super.remove();
    }

    @Override
    public void endCollidesWith(Actor otherActor) {

    }
}
