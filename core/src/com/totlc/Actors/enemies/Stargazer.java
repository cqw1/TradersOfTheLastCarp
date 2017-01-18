package com.totlc.Actors.enemies;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.totlc.AssetList;
import com.totlc.levels.ALevel;

public class Stargazer extends Enemy {

    // Stat constants.
    private static int id = 1;
    private static int hp = 3;
    private static int atk = 1;

    private static float friction = 0.95f;
    private static float[] acceleration = {0, 0};
    private static float[] velocity = {0, 0};
    private static float acc = 200;
    private static float maxVelocity = 50;
    private static float knockback = 5;

    // State variables.
    private boolean spin = false;
    private boolean wind_down = false;
    private int spin_duration = 200;
    private float spin_chance = .005f;
    private int counter = 0;
    float[] targetVector = {0, 0};

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
        setHitBox(new Rectangle(x + 40, y + 16, 42, 16));
        setSpeed(acc);
        setFriction(friction);
        setVel(velocity);
        setAcc(acceleration);
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

        float newAcc[] = getAcc();

        Actor target = ((ALevel)getStage()).getPlayer();
        targetVector[0] = target.getX() - getX();
        targetVector[1] = target.getY() - getY();

        float n = (float)Math.sqrt( targetVector[0] * targetVector[0] + targetVector[1] * targetVector[1] );
        if (n != 0) {
            targetVector[0] = targetVector[0] / n;
            targetVector[1] = targetVector[1] / n;
        }
        newAcc[0] = targetVector[0] * getSpeed();
        newAcc[1] = targetVector[1] * getSpeed();
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
            if (!spin){
                batch.draw(bodyAnimation.getKeyFrame(getAnimationTime(), true), getX(), getY());
                if (wind_down){
                    batch.draw(gazeReverseAnimation.getKeyFrame(getAnimationTime(), false), getX(), getY());
                } else {
                    batch.draw(eyeAnimation.getKeyFrame(getAnimationTime(), true), getX(), getY());
                }
            } else{
                batch.draw(spinAnimation.getKeyFrame(getAnimationTime(), true), getX(), getY());
                batch.draw(gazeAnimation.getKeyFrame(getAnimationTime(), false), getX(), getY());
            }
        }
    }

    @Override
    public boolean collidesWith(Actor otherActor) {
        return false;
    }

    @Override
    public void endCollidesWith(Actor otherActor) {}
}
