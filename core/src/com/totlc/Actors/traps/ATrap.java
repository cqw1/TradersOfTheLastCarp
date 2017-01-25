package com.totlc.Actors.traps;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.totlc.Actors.TotlcObject;

public abstract class ATrap extends TotlcObject {
    private double delay;

    private boolean active;

    public ATrap(AssetManager assetManager, Rectangle r) {
        super(assetManager, r);
        setDelay(delay);
        setActive(false);
    }

    public void setDelay(double d) { delay = d; }

    public double getDelay() { return delay; }

    public abstract void activate();

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

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }
}
