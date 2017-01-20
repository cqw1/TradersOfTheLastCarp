package com.totlc.Actors.enemies;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.totlc.AssetList;
import com.totlc.Directionality;
import com.totlc.levels.ALevel;

import java.awt.geom.Point2D;

public class Stargazer extends AEnemy {

    // Stat constants.
    private static int id = 1;
    private static int hp = 3;
    private static int atk = 1;

    private static float friction = .99f;
    private static float speed = 15;
    private static float maxVelocity = 15;
    private static float knockback = 5;

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

    private Animation<TextureRegion> bodyAnimation;
    private Animation<TextureRegion> eyeAnimation;
    private Animation<TextureRegion> spinAnimation;
    private Animation<TextureRegion> gazeAnimation;
    private Animation<TextureRegion> gazeReverseAnimation;


    public Stargazer(AssetManager assetManager, int x, int y) {
        super(assetManager, x, y);

        setHpMax(hp);
        setHpCurrent(getHpMax());
        setAttack(atk);

        setWidth(32);
        setHeight(32);

        initHitBox();
        setSpeed(speed);
        setFriction(friction);
        setMaxVel(maxVelocity);
        setKnockback(knockback);

        assetManager.load(AssetList.STARGAZER_BODY.toString(), TextureAtlas.class);
        assetManager.load(AssetList.STARGAZER_EYE.toString(), TextureAtlas.class);
        assetManager.load(AssetList.STARGAZER_SPIN.toString(), TextureAtlas.class);
        assetManager.load(AssetList.STARGAZER_GAZE.toString(), TextureAtlas.class);
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
        if (getAssetManager().update() && !assetsLoaded()) {
            // Done loading. Instantiate all assets

            setAssetsLoaded(true);

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
        }

        if (assetsLoaded()) {
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
        }
    }

    @Override
    public boolean collidesWith(Actor otherActor) {
        return super.collidesWith(otherActor);
    }

    @Override
    public void endCollidesWith(Actor otherActor) {}
}
