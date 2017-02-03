package com.totlc.Actors.triggers;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.math.Rectangle;

public class TriggerFactory {

    public static final String TYPE = "TRIGGERS";
    public static final String BUTTON = "BUTTON";

    public static ATrigger createTrigger(String type, AssetManager assetManager, float x, float y) {
        if (type.equals(BUTTON)) {
            return new ButtonTrigger(assetManager, x, y);
        }

        return null;
    }

    public static ATrigger createCustomTrigger(String type, AssetManager assetManager, Rectangle r) {
        if (type.equals(BUTTON)) {
            return new ButtonTrigger(assetManager, r);
        }

        return null;
    }
}
