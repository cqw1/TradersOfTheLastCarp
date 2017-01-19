package com.totlc.Actors.projectiles;

import com.badlogic.gdx.assets.AssetManager;
import com.totlc.Actors.TotlcObject;

public abstract class Projectile extends TotlcObject {

    private int attack = 1;

    public Projectile(AssetManager assetManager, float x, float y) {
        super(assetManager, x, y);
    }

    public int getAttack() {
        return attack;
    }

    public void setAttack(int attack) {
        this.attack = attack;
    }
}
