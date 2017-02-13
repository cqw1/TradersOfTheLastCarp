package com.totlc.Actors.traps;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.totlc.Actors.damage.Damage;

public class SpikeTrap extends ATrap {

    public static float width = 128;
    public static float height = 128;
    public static long duration = 1000;
    public static long delay = 500;
    private static Damage damageArea;
    private static int damage = 1;
    private long timeActivated = 0;

    public SpikeTrap(AssetManager assetManager, float x, float y) {
        this(assetManager, new Rectangle(x, y, width, height), delay);
    }

    public SpikeTrap(AssetManager assetManager, Rectangle r, long delay) {
        super(assetManager, r, delay);
        damageArea = new Damage(assetManager, r, damage) {
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
    }

    @Override
    public void activate() {
        getStage().addActor(damageArea);
        damageArea.setExpirationTime(System.currentTimeMillis() + duration);
//        timeActivated = System.currentTimeMillis();
    }

//    @Override
//    public void act(float delta) {
//        delayActivation();
//        if (isActive() && System.currentTimeMillis() > (getStartTime() + getDelay())) {
//            // If the trap was active and we've already passed our delay and the allotted time for displaying eyebrows.
//            setActive(false);
//            setSetup(false);
//        }
//        if (timeActivated != 0 && System.currentTimeMillis() > (timeActivated + duration)) {
//            timeActivated = 0;
//            damageArea.remove();
//        }
//    }
}
