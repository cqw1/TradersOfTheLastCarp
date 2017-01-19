package com.totlc.Actors.traps;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.totlc.Actors.totlcObject;

public abstract class ATrap extends totlcObject {
    private double delay;

    public ATrap(AssetManager assetManager, float x, float y) {
        super(assetManager, x, y);
        this.delay = delay;
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
}
