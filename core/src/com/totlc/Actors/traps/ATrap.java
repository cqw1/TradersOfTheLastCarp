package com.totlc.Actors.traps;

public abstract class ATrap implements ITrap {
    private double delay;

    public void setDelay(double d) { delay = d; }

    public double getDelay() { return delay; }

    public abstract void activate();
}
