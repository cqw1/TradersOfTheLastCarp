package com.totlc.Actors.traps;

import com.badlogic.gdx.assets.AssetManager;

public class TrapFactory {

    public static final String TYPE = "TRAPS";
    public static final String ARROW_TRAP = "ARROW_TRAP";
    public static final String SPIDER_TRAP = "SPIDER_TRAP";
    public static final String TELEPORTER = "TELEPORTER";
    public static final String SPIKE_TRAP = "SPIKE_TRAP";

    public static ATrap createTrap(String type, AssetManager assetManager, float x, float y) {
        if (type.equals(ARROW_TRAP)) {
            return new ArrowTrap(assetManager, x, y);
        } else if (type.equals(SPIDER_TRAP)) {
            return new SpiderRepository(assetManager, x, y);
        } else if (type.equals(TELEPORTER)) {
            return new Teleporter(assetManager, x, y);
        } else if (type.equals(SPIKE_TRAP)) {
            return new SpikeTrap(assetManager, x, y);
        }

        return null;
    }
}
