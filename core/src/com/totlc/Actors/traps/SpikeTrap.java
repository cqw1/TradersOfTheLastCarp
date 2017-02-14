package com.totlc.Actors.traps;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.totlc.Actors.damage.Damage;
import com.totlc.AssetList;

public class SpikeTrap extends ATrap {

    // Asset and animation constants.
    private TextureAtlas trapTextureAtlas;
    private Animation<TextureRegion> trapAnimation, trapReverseAnimation;

    public static float width = 128;
    public static float height = 128;
    public static long duration = 1000;
    public static long delay = 500;
    private static Damage damageArea;
    private static int damage = 1;

    private boolean reverse;

    public SpikeTrap(AssetManager assetManager, float x, float y) {
        this(assetManager, new Rectangle(x, y, width, height), delay);
        trapTextureAtlas = assetManager.get(AssetList.SPIKE_TRAP.toString());
        trapAnimation = new Animation<TextureRegion>(1 / 64f, trapTextureAtlas.getRegions());
        trapReverseAnimation = new Animation<TextureRegion>(1 / 64f, trapTextureAtlas.getRegions());
        trapReverseAnimation.setPlayMode(Animation.PlayMode.REVERSED);
        this.reverse = false;
        setAnimationTime(0);
    }

    public SpikeTrap(AssetManager assetManager, Rectangle r, long delay) {
        super(assetManager, r, delay);
        damageArea = new Damage(assetManager, r, damage, 0) {
            @Override
            public void draw(Batch batch, float alpha) {

            }

            @Override
            public boolean collidesWith(Actor otherActor) {
                return false;
            }

            @Override
            public void endCollidesWith(Actor otherActor) {

            }
        };
        trapTextureAtlas = assetManager.get(AssetList.ARROW_TRAP.toString());
        trapAnimation = new Animation<TextureRegion>(1 / 48f, trapTextureAtlas.getRegions());
        trapReverseAnimation = new Animation<TextureRegion>(1 / 48f, trapTextureAtlas.getRegions());
        trapReverseAnimation.setPlayMode(Animation.PlayMode.REVERSED);
        setAnimationTime(0);
        this.reverse = false;
    }

    @Override
    public void activate() {
        getStage().addActor(damageArea);
        damageArea.setExpirationTime(System.currentTimeMillis() + duration);
        setAnimationTime(0);
        this.reverse = false;
    }

    @Override
    public void act(float delta) {
        delayActivation();
        if (isActive() && System.currentTimeMillis() > (getStartTime() + getDelay() + duration)) {
            // If the trap was active and we've already passed our delay and the allotted time for displaying eyebrows.
            setActive(false);
            setSetup(false);
            setAnimationTime(0);
            this.reverse = true;
        }
        increaseAnimationTime(delta);
    }

    @Override
    public void draw(Batch batch, float alpha) {
        if (isActive()){
            batch.draw(trapAnimation.getKeyFrame(getAnimationTime(), false), getX(), getY());
        } else {
            batch.draw(trapTextureAtlas.getRegions().get(0), getX(), getY());
            if(this.reverse){
                batch.draw(trapReverseAnimation.getKeyFrame(getAnimationTime(), false), getX(), getY());
            }
        }
    }
}
