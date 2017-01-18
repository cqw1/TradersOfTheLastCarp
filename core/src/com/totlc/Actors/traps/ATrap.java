package com.totlc.Actors.traps;

import com.badlogic.gdx.scenes.scene2d.Actor;

public abstract class ATrap extends Actor {
    private double delay;

    public void setDelay(double d) { delay = d; }

    public double getDelay() { return delay; }

    public abstract void activate();
}
