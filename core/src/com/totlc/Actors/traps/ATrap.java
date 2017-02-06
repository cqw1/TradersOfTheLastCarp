package com.totlc.Actors.traps;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.totlc.Actors.TotlcObject;

public abstract class ATrap extends TotlcObject {
    private long delay; // in millis
    private boolean active;
    private boolean setup;

    private long startTime;

    public ATrap(AssetManager assetManager, Rectangle r, long delay) {
        super(assetManager, r);
        setDelay(delay);
        setActive(false);
    }

    public void setDelay(long d) { delay = d; }

    public long getDelay() { return delay; }

    public void setSetup(boolean setup) {
        this.setup = setup;
    }

    public boolean isSetup() {
        return setup;
    }

    public abstract void activate();
    public abstract void setup();

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

    public void delayActivation(){
        if (isSetup() && !isActive()) {
            if (System.currentTimeMillis() < (startTime + getDelay())) {
                // Still waiting for delay.
                return;
            } else {
                setActive(true);
                activate();
            }
        }
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public long getStartTime() {
        return startTime;
    }

    public void setStartTime(long startTime) {
        this.startTime = startTime;
    }
}
