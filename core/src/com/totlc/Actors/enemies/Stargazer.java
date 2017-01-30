package com.totlc.Actors.enemies;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.totlc.Actors.projectiles.ProjEnum;
import com.totlc.Actors.projectiles.ProjectileFactory;
import com.totlc.AssetList;
import com.totlc.Directionality;
import com.totlc.levels.ALevel;

import java.awt.geom.Point2D;

/**
 * Floating enemy that drifts slowly towards player.
 * Periodically shoots a projectile in direction of player.
 * Projectile attack is telegraphed by short spin-up animation.
 */
public class Stargazer extends AEnemy {

    // Stat constants.
    private static int id = 1;
    private static int hp = 2;
    private static int atk = 1;

    private static float friction = .99f;
    private static float speed = 25;
    private static float maxVelocity = 25;
    private static float knockback = 5;
    private static float projectileSpeed = 300;

    // State variables.
    private boolean spin = false;
    private boolean wind_down = false;
    private int spin_duration = 100;
    private float spin_chance = .005f;
    private int counter = 0;

    // Asset and animation constants.
    private TextureAtlas bodyTextureAtlas;
    private TextureAtlas eyeTextureAtlas;
    private TextureAtlas spinTextureAtlas;
    private TextureAtlas gazeTextureAtlas;
    private Texture shadow;
    private float shadow_size = 0.5f;

    private Animation<TextureRegion> bodyAnimation;
    private Animation<TextureRegion> eyeAnimation;
    private Animation<TextureRegion> spinAnimation;
    private Animation<TextureRegion> gazeAnimation;
    private Animation<TextureRegion> gazeReverseAnimation;


    public Stargazer(AssetManager assetManager, int x, int y) {
        super(assetManager, new Rectangle(x, y, 40, 40));

        setHpMax(hp);
        setHpCurrent(getHpMax());
        setAttack(atk);

        setFloating(true);

        //TODO: Remove after correcting hitboxes
        moveHitBox(44,44);

        setSpeed(speed);
        setFriction(friction);
        setMaxVel(maxVelocity);
        setKnockback(knockback);

        bodyTextureAtlas = getAssetManager().get(AssetList.STARGAZER_BODY.toString());
        bodyAnimation = new Animation<TextureRegion>(1/16f, this.bodyTextureAtlas.getRegions());

        eyeTextureAtlas = getAssetManager().get(AssetList.STARGAZER_EYE.toString());
        eyeAnimation = new Animation<TextureRegion>(1/12f, this.eyeTextureAtlas.getRegions());

        spinTextureAtlas = getAssetManager().get(AssetList.STARGAZER_SPIN.toString());
        spinAnimation = new Animation<TextureRegion>(1/24f, this.spinTextureAtlas.getRegions());

        gazeTextureAtlas = getAssetManager().get(AssetList.STARGAZER_GAZE.toString());
        gazeAnimation = new Animation<TextureRegion>(1/24f, this.gazeTextureAtlas.getRegions());
        gazeReverseAnimation = new Animation<TextureRegion>(1/24f, this.gazeTextureAtlas.getRegions());
        gazeReverseAnimation.setPlayMode(Animation.PlayMode.REVERSED);

        shadow = getAssetManager().get(AssetList.SHADOW.toString());
    }

    @Override
    public void act(float deltaTime) {
        increaseAnimationTime(deltaTime);
        if (!spin){
            if (wind_down){
                counter++;
                if (counter >= spin_duration * 0.2){
                    wind_down = false;
                    counter = 0;
                }
            } else{
                if (Math.random() < spin_chance){
                    setAnimationTime(0);
                    spin = true;
                }
            }
        } else {
            counter++;
            if (counter >= spin_duration){
                setAnimationTime(0);
                wind_down = true;
                spin = false;
                counter = 0;
                star_shot();
            }
        }

        Point2D newAcc = getAcc();

        Actor target = ((ALevel)getStage()).getPlayer();
        Point2D targetVector = getTarget(target);
        if(Math.signum(targetVector.getX()) > 0){
            setIsFacing(Directionality.RIGHT);
        } else{
            setIsFacing(Directionality.LEFT);
        }

        newAcc.setLocation(targetVector.getX() * getSpeed(), targetVector.getY() * getSpeed());
        setAcc(newAcc);

        updateVelocity();
        moveUnit(deltaTime);
        returnIntoBounds();
    }

    @Override
    public void draw(Batch batch, float alpha) {

        boolean flip = getIsFacing().isFacingRight();
        float w = spinAnimation.getKeyFrame(getAnimationTime()).getRegionWidth();
        float h = spinAnimation.getKeyFrame(getAnimationTime()).getRegionHeight();
        if (!spin){
            batch.draw(bodyAnimation.getKeyFrame(getAnimationTime(), true), getX(), getY(), w / 2, h / 2, w, h, flip ? -1f : 1f, 1f, 0f);
            if (wind_down){
                batch.draw(gazeReverseAnimation.getKeyFrame(getAnimationTime(), false), getX(), getY(), w / 2, h / 2, w, h, flip ? -1f : 1f, 1f, 0f);
            } else {
                batch.draw(eyeAnimation.getKeyFrame(getAnimationTime(), true), getX(), getY(), w / 2, h / 2, w, h, flip ? -1f : 1f, 1f, 0f);
            }
        } else{
            batch.draw(spinAnimation.getKeyFrame(getAnimationTime(), true), getX(), getY(), w / 2, h / 2, w, h, flip ? -1f : 1f, 1f, 0f);
            batch.draw(gazeAnimation.getKeyFrame(getAnimationTime(), false), getX(), getY(), w / 2, h / 2, w, h, flip ? -1f : 1f, 1f, 0f);
        }
        batch.draw(shadow, getX() + Math.abs(128 - shadow.getWidth() * shadow_size) / 2, getY() - 128 * 0.1f, shadow.getWidth() * shadow_size, shadow.getHeight() * shadow_size);

        drawHealth(batch, alpha, (int)getWidth() / 2, 24);
    }

    private void star_shot(){
        Point2D targetVector = getTarget(((ALevel)getStage()).getPlayer());
        Sound starSound = Gdx.audio.newSound(Gdx.files.internal("sounds/sparkle0.mp3"));
        starSound.play(0.7f);
        getStage().addActor(ProjectileFactory.createProjectile(ProjEnum.STAR_SHOT, new Point2D.Double(targetVector.getX() * projectileSpeed, targetVector.getY() * projectileSpeed), getAssetManager(), (float) getCenter().getX(), (float) getCenter().getY(), 1));
    }

    @Override
    public boolean collidesWith(Actor otherActor) {
        return super.collidesWith(otherActor);
    }

    @Override
    public void endCollidesWith(Actor otherActor) {}
}
