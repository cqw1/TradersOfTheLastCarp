package com.totlc.Actors.damage;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.g2d.*;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.totlc.Actors.Player;
import com.totlc.Actors.effects.Impact;
import com.totlc.AssetList;
import com.totlc.TradersOfTheLastCarp;

import java.awt.geom.Point2D;

public class StarShot extends Damage {

    // Asset and animation constants.
    private TextureAtlas shotTextureAtlas, particleAtlas;
    private Animation<TextureRegion> shotAnimation;
    private ParticleEffectPool.PooledEffect starTrail;

    // Bookkeeping variables.
    private long startTime;
    private boolean removeFlag = false;
    private Point2D textureDimensions;
    private static int damage = 2;

    public StarShot(AssetManager assetManager, float x, float y, int damageType){
        super(assetManager, new Rectangle(x, y, 24, 24), damage, damageType);

        //TODO: Correct hitboxes?
        moveHitBox(20, 20);

        setDamageType(damageType);
        setScaleFactor(1.0f);

        shotTextureAtlas = assetManager.get(AssetList.PROJECTILE_STAR_SHOT.toString());
        shotAnimation = new Animation<TextureRegion>(1 / 16f, shotTextureAtlas.getRegions());

        textureDimensions = new Point2D.Float(shotAnimation.getKeyFrame(getAnimationTime()).getRegionWidth(), shotAnimation.getKeyFrame(getAnimationTime()).getRegionHeight());

//        particleAtlas = assetManager.get(AssetList.STAR_PARTICLES.toString());
//        starTrail = new ParticleEffect();
//        starTrail.setPosition(getX() + (float)textureDimensions.getX() / 2, getY() + (float)textureDimensions.getY() / 2);
//
//        starTrail.load(Gdx.files.internal(AssetList.STAR_TRAIL.toString()), particleAtlas);
//        starTrail.start();

        starTrail = TradersOfTheLastCarp.starTrailPool.obtain();
        System.out.println("starTrail obtained");
//        TradersOfTheLastCarp.starTrailPool.
        starTrail.setPosition(getX() + (float)textureDimensions.getX() / 2, getY() + (float)textureDimensions.getY() / 2);

    }

    @Override
    public void act(float deltaTime){
        increaseAnimationTime(deltaTime);
        moveUnit(deltaTime);

        if (isOutOfBounds()) {
            if(!removeFlag){
                startTime = System.currentTimeMillis();
                removeFlag = !removeFlag;
            }
            delayRemove();
        }

        if (removeFlag){
            for (ParticleEmitter p : starTrail.getEmitters()){
                p.allowCompletion();
            }
        }
        starTrail.setPosition(getX() + (float)textureDimensions.getX() / 2, getY() + (float)textureDimensions.getY() / 2);
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
            getStage().addActor(new Impact(getAssetManager(), getX(), getY()));
            startTime = System.currentTimeMillis();
            removeFlag = true;
//            return false;
        }
        return false;
    }

    private boolean delayRemove() {
        //boolean done = (System.currentTimeMillis() - startTime) > 8000;
        if (starTrail.isComplete()) {
            System.out.println("starTrail freed");
            starTrail.free();
        }

        return starTrail.isComplete() && super.remove();
    }

    @Override
    public void endCollidesWith(Actor otherActor) {

    }
}
