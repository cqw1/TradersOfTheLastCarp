package com.totlc.Actors.items;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.math.Rectangle;

/**
 * Abstract class for usable inventory items.
 */
public abstract class AItem extends APickup {

    public AItem(AssetManager assetManager, Rectangle r) {
        super(assetManager, r);
    }

    public abstract void use();
}
