package com.totlc.Actors.traps;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.totlc.Actors.TotlcObject;
import com.totlc.Actors.effects.Exclamation;

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

    public void setup() {
        if (!isSetup()) {
            // If someone's already triggered this trap
            getStage().addActor(new Exclamation(getAssetManager(), (float) getHitBoxCenter().getX() + getHitBoxWidth() / 2, (float) getHitBoxCenter().getY() + getHitBoxHeight() / 3));
            setStartTime(System.currentTimeMillis());
            setSetup(true);
        }
    }

    public void act(float delta) {
        delayActivation();
        if (isActive() && System.currentTimeMillis() > (getStartTime() + getDelay())) {
            // If the trap was active and we've already passed our delay and the allotted time for displaying eyebrows.
            setActive(false);
            setSetup(false);
        }
    }

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

    public void delayActivation() {
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
