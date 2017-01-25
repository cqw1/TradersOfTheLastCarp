package com.totlc.Actors.effects;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.math.Rectangle;
import com.totlc.Actors.TotlcObject;

public abstract class AEffect extends TotlcObject {
    public AEffect(AssetManager assetManager, Rectangle r) {
        super(assetManager, r);
    }
}
