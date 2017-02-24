package com.totlc.Actors.items;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.maps.MapProperties;

public class PickupFactory {

    public static final String TYPE = "ITEMS";
    public static final String HEALTH = "HEALTH";
    public static final String GOLDFISH = "GOLDFISH";
    public static final String KEY = "KEY";

    public static APickup createPickup(String type, AssetManager assetManager, float x, float y) throws NullPointerException {
        if (type.equals(HEALTH)) {
            return new Health(assetManager, x, y);
        } else if (type.equals(GOLDFISH)) {
            return new Goldfish(assetManager, x, y);
        } else if (type.equals(KEY)) {
            return new Key(assetManager, x, y);
        }

        throw new NullPointerException("Received type: " + type);
    }

    public static APickup createPickupFromMP(MapProperties mp, AssetManager assetManager) {
        APickup returnMe = createPickup(mp.get("type", String.class), assetManager,
                mp.get("x", Float.class),
                mp.get("y", Float.class));

        return returnMe;
    }
}
