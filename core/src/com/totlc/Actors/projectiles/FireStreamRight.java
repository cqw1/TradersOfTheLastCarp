package com.totlc.Actors.projectiles;

import com.badlogic.gdx.assets.AssetManager;
import com.totlc.Actors.TotlcObject;

public class FireStreamRight extends FireStream{

    public FireStreamRight(AssetManager assetManager, TotlcObject actor, float x, float y) {
        super(assetManager, actor, x, y);
    }

    @Override
    public void setDirection() {
        //Default direction. No additional configuration necessary.
    }
}
