package com.totlc.Actors.traps;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.math.Rectangle;

/**
 * Bursts into spiders upon collision with another actor.
 */
public class SpiderRepository extends ATrap  {

    public SpiderRepository(AssetManager assetManager, Rectangle r) {
        super(assetManager, r);
    }

    @Override
    public void activate() {

    }
}
