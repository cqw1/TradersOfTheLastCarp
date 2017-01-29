package com.totlc.Actors.terrain;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.totlc.Actors.TotlcObject;

public abstract class AWall extends TotlcObject {
    public AWall(AssetManager assetManager, Rectangle r) {
        super(assetManager, r);
    }
}
