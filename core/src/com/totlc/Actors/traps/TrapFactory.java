package com.totlc.Actors.traps;

import com.badlogic.gdx.assets.AssetManager;

public class TrapFactory {

    public static final String TYPE = "TRAPS";
    public static final String ARROW_TRAP = "ARROW_TRAP";
    public static final String SPIDER_TRAP = "SPIDER_TRAP";

    public static ATrap createTrap(String type, AssetManager assetManager, float x, float y) {
        if (type.equals(ARROW_TRAP)) {
            return new ArrowTrap(assetManager, x, y);
        } else if (type.equals(SPIDER_TRAP)) {
            return new SpiderRepository(assetManager, x, y);
        }

        return null;
    }
}
