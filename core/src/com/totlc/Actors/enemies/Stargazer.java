package com.totlc.Actors.enemies;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.totlc.AssetList;

public class Stargazer extends Enemy {

    // Asset and animation constants.
    private TextureAtlas body_texture_0, eye_texture_0, spin_texture_0, gaze_texture_0;
    private Animation<TextureRegion> body_animation_0, eye_animation_0, spin_animation_0, gaze_animation_0, gaze_animation_1;

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

    public Stargazer(AssetManager assetManager, int x, int y) {
        super(assetManager, x, y);
        this.body_texture_0 = getAssetManager().get(AssetList.STARGAZER_BODY.toString());
        this.eye_texture_0 = getAssetManager().get(AssetList.STARGAZER_EYE.toString());
        this.spin_texture_0 = getAssetManager().get(AssetList.STARGAZER_SPIN.toString());
        this.gaze_texture_0 = getAssetManager().get(AssetList.STARGAZER_GAZE.toString());
        this.body_animation_0 = new Animation<TextureRegion>(1/16f, this.body_texture_0.getRegions());
        this.eye_animation_0 = new Animation<TextureRegion>(1/12f, this.eye_texture_0.getRegions());
        this.spin_animation_0 = new Animation<TextureRegion>(1/24f, this.spin_texture_0.getRegions());
        this.gaze_animation_0 = new Animation<TextureRegion>(1/24f, this.gaze_texture_0.getRegions());
        this.gaze_animation_1 = new Animation<TextureRegion>(1/24f, this.gaze_texture_0.getRegions());
        this.gaze_animation_1.setPlayMode(Animation.PlayMode.REVERSED);

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
        if (!spin){
            batch.draw(body_animation_0.getKeyFrame(getAnimationTime(), true), getX(), getY());
            if (wind_down){
                batch.draw(gaze_animation_1.getKeyFrame(getAnimationTime(), false), getX(), getY());
            } else {
                batch.draw(eye_animation_0.getKeyFrame(getAnimationTime(), true), getX(), getY());
            }
        } else{
            batch.draw(spin_animation_0.getKeyFrame(getAnimationTime(), true), getX(), getY());
            batch.draw(gaze_animation_0.getKeyFrame(getAnimationTime(), false), getX(), getY());
        }
    }

    @Override
    public boolean collidesWith(Actor otherActor) {
        return false;
    }

    @Override
    public void endCollidesWith(Actor otherActor) {}
}
