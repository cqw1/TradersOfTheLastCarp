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
import com.totlc.Actors.enemies.movement.AMovement;
import com.totlc.Actors.damage.DamageEnum;
import com.totlc.Actors.damage.DamageFactory;
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
    private boolean windDown = false;
    private float spinChance = .005f;

    private int spinPeriod = 1000; // in millis
    private long movementTime;

    // Asset and animation constants.
    private TextureAtlas bodyTextureAtlas;
    private TextureAtlas eyeTextureAtlas;
    private TextureAtlas spinTextureAtlas;
    private TextureAtlas gazeTextureAtlas;
    private Texture shadow;
    private float shadowSize = 0.5f;

    private Animation<TextureRegion> bodyAnimation;
    private Animation<TextureRegion> eyeAnimation;
    private Animation<TextureRegion> spinAnimation;
    private Animation<TextureRegion> gazeAnimation;
    private Animation<TextureRegion> gazeReverseAnimation;

    public Stargazer(AssetManager assetManager, float x, float y, AMovement movement) {
        super(assetManager, new Rectangle(x, y, 40, 40), movement, hp, atk);

        setFloating(true);

        //TODO: Remove after correcting hitboxes
        moveHitBox(44,44);

        initMovement(friction, maxVelocity, speed);
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
        super.act(deltaTime);
        if (checkStun()) {
            return;
        }
        if (!spin){
            if (windDown){
                if (System.currentTimeMillis() > (movementTime + (spinPeriod * 0.2))) {
                    windDown = false;
                    movementTime = System.currentTimeMillis();
                }
            } else {
                if (Math.random() < spinChance){
                    setAnimationTime(0);
                    spin = true;
                    movementTime = System.currentTimeMillis();
                }
            }
        } else {
            if (System.currentTimeMillis() > (movementTime + spinPeriod)) {
                setAnimationTime(0);
                windDown = true;
                spin = false;
                movementTime = System.currentTimeMillis();
                star_shot();
            }
        }


        if(Math.signum(getMovement().getTargetVector(this).getX()) > 0){
            setIsFacing(Directionality.RIGHT);
        } else{
            setIsFacing(Directionality.LEFT);
        }
    }

    @Override
    public void draw(Batch batch, float alpha) {

        boolean flip = getIsFacing().isFacingRight();
        float w = spinAnimation.getKeyFrame(getAnimationTime()).getRegionWidth();
        float h = spinAnimation.getKeyFrame(getAnimationTime()).getRegionHeight();
        if (!spin){
            batch.draw(bodyAnimation.getKeyFrame(getAnimationTime(), true), getX(), getY(), w / 2, h / 2, w, h, flip ? -1f : 1f, 1f, 0f);
            if (windDown){
                batch.draw(gazeReverseAnimation.getKeyFrame(getAnimationTime(), false), getX(), getY(), w / 2, h / 2, w, h, flip ? -1f : 1f, 1f, 0f);
            } else {
                batch.draw(eyeAnimation.getKeyFrame(getAnimationTime(), true), getX(), getY(), w / 2, h / 2, w, h, flip ? -1f : 1f, 1f, 0f);
            }
        } else{
            batch.draw(spinAnimation.getKeyFrame(getAnimationTime(), true), getX(), getY(), w / 2, h / 2, w, h, flip ? -1f : 1f, 1f, 0f);
            batch.draw(gazeAnimation.getKeyFrame(getAnimationTime(), false), getX(), getY(), w / 2, h / 2, w, h, flip ? -1f : 1f, 1f, 0f);
        }
        batch.draw(shadow, getX() + Math.abs(128 - shadow.getWidth() * shadowSize) / 2, getY() - 128 * 0.1f, shadow.getWidth() * shadowSize, shadow.getHeight() * shadowSize);

        drawHealth(batch, alpha, -(int)getHitBoxWidth() / 2, -(int)getHitBoxHeight() / 2);
        drawStatuses(batch, alpha);
    }

    private void star_shot(){
        Point2D targetVector = getTarget(((ALevel)getStage()).getPlayer());
        Sound starSound = Gdx.audio.newSound(Gdx.files.internal("sounds/sparkle0.mp3"));
        starSound.play(0.7f);
        getStage().addActor(DamageFactory.createDamage(DamageEnum.STAR_SHOT, new Point2D.Double(targetVector.getX() * projectileSpeed, targetVector.getY() * projectileSpeed), getAssetManager(), (float) getCenter().getX(), (float) getCenter().getY(), 1));
    }

    @Override
    public boolean collidesWith(Actor otherActor) {
        return super.collidesWith(otherActor);
    }

    @Override
    public void endCollidesWith(Actor otherActor) {}
}
