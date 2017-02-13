package com.totlc.Actors.traps;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.math.Rectangle;
import com.totlc.TradersOfTheLastCarp;

public class Teleporter extends ATrap {

    private static long delay = 500;

    public Teleporter(AssetManager assetManager, float x, float y) {
        super(assetManager, new Rectangle(x, y, 0, 0), delay);
    }

    @Override
    public void activate() {
        TradersOfTheLastCarp.player.moveAbs(getX(), getY());
    }
}
