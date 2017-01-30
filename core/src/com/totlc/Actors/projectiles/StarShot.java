package com.totlc.Actors.projectiles;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.*;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.totlc.Actors.Player;
import com.totlc.Actors.effects.Impact;
import com.totlc.AssetList;

public class StarShot extends Projectile {

    // Asset and animation constants.
    private TextureAtlas shotTextureAtlas, particleAtlas;
    private Animation<TextureRegion> shotAnimation;
    private ParticleEffect star_trail;

    // Bookkeeping variables.
    private long startTime;
    private boolean removeFlag = false;

    public StarShot(AssetManager assetManager, float x, float y, int damageType){
        super(assetManager, new Rectangle(x, y, 24, 24));

        //TODO: Correct hitboxes?
        moveHitBox(20, 20);

        setDamageType(damageType);
        setAttack(2);
        setScaleFactor(1.0f);

        star_trail = new ParticleEffect();
        star_trail.setPosition((float)getCenter().getX(), (float)getCenter().getY());

        shotTextureAtlas = assetManager.get(AssetList.PROJECTILE_STAR_SHOT.toString());
        particleAtlas = assetManager.get(AssetList.STAR_PARTICLES.toString());
        shotAnimation = new Animation<TextureRegion>(1 / 16f, shotTextureAtlas.getRegions());
        star_trail.load(Gdx.files.internal(AssetList.STAR_TRAIL.toString()), particleAtlas);
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
            for (ParticleEmitter p : star_trail.getEmitters()){
                p.allowCompletion();
            }
        }
        star_trail.setPosition((float)getCenter().getX(), (float)getCenter().getY());
    }

    @Override
    public void draw(Batch batch, float alpha) {
        if (!removeFlag) {
            batch.draw(shotAnimation.getKeyFrame(getAnimationTime(), true), getX(), getY());
        }
        star_trail.draw(batch, Gdx.graphics.getDeltaTime());
    }

    @Override
    public boolean collidesWith(Actor otherActor) {
        if (otherActor instanceof Player && !removeFlag) {
            Sound impactSound = Gdx.audio.newSound(Gdx.files.internal("sounds/punch2.mp3"));
            impactSound.play(1.0f);
            getStage().addActor(new Impact(getAssetManager(), getX(), getY()));
            startTime = System.currentTimeMillis();
            removeFlag = true;
            return false;
        }
        return false;
    }

    private boolean delayRemove() {
        return System.currentTimeMillis() - startTime > 8000 && super.remove();
    }

    @Override
    public void endCollidesWith(Actor otherActor) {

    }
}
