package com.totlc.Actors.traps;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.math.Rectangle;

public abstract class FireTrap extends ATrap{

    public FireTrap(AssetManager assetManager, Rectangle r, long delay) {
        super(assetManager, r, delay);
    }
}
