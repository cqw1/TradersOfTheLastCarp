package com.totlc.Actors.damage;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.math.Rectangle;
import com.totlc.Actors.TotlcObject;

public abstract class Projectile extends Damage {

    public Projectile(AssetManager assetManager, Rectangle r) {
        super(assetManager, r);
    }
}
