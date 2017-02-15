package com.totlc.Actors.traps;

import com.badlogic.gdx.assets.AssetManager;

public class TrapFactory {

    public static final String TYPE = "TRAPS";
    public static final String ARROW_TRAP = "ARROW_TRAP";
    public static final String SPIDER_TRAP = "SPIDER_TRAP";
    public static final String TELEPORTER = "TELEPORTER";
    public static final String SPIKE_TRAP = "SPIKE_TRAP";
    public static final String EXIT_PORTAL = "EXIT_PORTAL";
    public static final String FIRE_TRAP = "FIRE_TRAP";

    public static ATrap createTrap(String type, AssetManager assetManager, float x, float y) {
        if (type.equals(ARROW_TRAP)) {
            return new ArrowTrap(assetManager, x, y);
        } else if (type.equals(SPIDER_TRAP)) {
            return new SpiderRepository(assetManager, x, y);
        } else if (type.equals(TELEPORTER)) {
            return new Teleporter(assetManager, x, y);
        } else if (type.equals(SPIKE_TRAP)) {
            return new SpikeTrap(assetManager, x, y);
        } else if (type.equals(EXIT_PORTAL)) {
            return new ExitPortal(assetManager, x, y);
        } else if (type.equals(FIRE_TRAP)) {
            return new FireTrap(assetManager, x, y);
        }

        System.out.println("ERROR: RECEIVED TYPE - " + type);
        return null;
    }

    public static ATrap createCustomDelayTrap(String type, AssetManager assetManager, float x, float y, long delay) {
        ATrap trap = createTrap(type, assetManager, x, y);
        trap.setDelay(delay);

        return trap;
    }
}
