package com.totlc.Actors.items;

import com.badlogic.gdx.assets.AssetManager;

public class PickupFactory {

    public static final String TYPE = "ITEMS";
    public static final String HEALTH = "HEALTH";

    public static APickup createPickup(String type, AssetManager assetManager, float x, float y) {
        if (type.equals(HEALTH)) {
            return new Health(assetManager, x, y);
        }

        return null;
    }
}
