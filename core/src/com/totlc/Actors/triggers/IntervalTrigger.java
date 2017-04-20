package com.totlc.Actors.triggers;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.scenes.scene2d.Actor;

public class IntervalTrigger extends ATrigger {

    private long triggerTime;
    private long interval;

    public IntervalTrigger(AssetManager assetManager, float x, float y) {
        this(assetManager, x, y, 2500);
    }

    public IntervalTrigger(AssetManager assetManager, float x, float y, long interval) {
        super(assetManager, new Rectangle(x, y, 1, 1));
        this.interval = interval;
        this.triggerTime = System.currentTimeMillis() + interval;
    }

    public void act(float delta) {
        if (System.currentTimeMillis() > triggerTime) {
            activateTraps();
            triggerTime = System.currentTimeMillis() + interval;
        }
    }

    @Override
    public void draw(Batch batch, float alpha) {}

    public boolean collidesWith(Actor otherActor) {
        return false;
    }

    public void endCollidesWith(Actor otherActor) {

    }

    public void setInterval(long time) {
        interval = time;
    }

    public long getInterval() {
        return interval;
    }

    public void setStartTime(long time) {
        triggerTime = System.currentTimeMillis() + time;
    }
}
