package com.totlc.Actors.items;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.math.Rectangle;
import com.totlc.Actors.TotlcObject;

/**
 * Abstract class for items that can be picked up by the player.
 * Include inventory items as well as health, keys, and powerups.
 */
public abstract class APickup extends TotlcObject {

    public APickup(AssetManager assetManager, Rectangle r) {
        super(assetManager, r);
    }

    public abstract void pickup();
}
