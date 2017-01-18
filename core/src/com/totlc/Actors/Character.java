package com.totlc.Actors;

public abstract class Character extends totlcObject {

    // Player health.
    private int hpMAX;
    private int hpCURRENT;

    abstract public void act(float deltaTime);

    public int getHpMax() {
        return hpMAX;
    }

    public void setHpMax(int hpMAX) {
        this.hpMAX = hpMAX;
    }

    public int getHpCurrent() {
        return hpCURRENT;
    }

    public void setHpCurrent(int hpCURRENT) {
        this.hpCURRENT = hpCURRENT;
    }


}
