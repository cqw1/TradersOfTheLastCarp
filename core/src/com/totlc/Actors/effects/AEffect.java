package com.totlc.Actors.effects;

import com.badlogic.gdx.assets.AssetManager;
import com.totlc.Actors.TotlcObject;

public abstract class AEffect extends TotlcObject {
    public AEffect(AssetManager assetManager, float x, float y) {
        super(assetManager, x, y);
    }
}
