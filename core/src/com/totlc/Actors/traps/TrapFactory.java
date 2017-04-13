package com.totlc.Actors.traps;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.maps.MapProperties;
import com.totlc.Directionality;

public class TrapFactory {

    public static final String TYPE = "TRAPS";
    public static final String ARROW_TRAP = "ARROW_TRAP";
    public static final String SPIDER_TRAP = "SPIDER_TRAP";
    public static final String TELEPORTER = "TELEPORTER";
    public static final String SPIKE_TRAP = "SPIKE_TRAP";
    public static final String EXIT_PORTAL = "EXIT_PORTAL";
    public static final String FIRE_TRAP = "FIRE_TRAP";
    public static final String BOULDER_TRAP = "BOULDER_TRAP";
    public static final String LASER_TRAP = "LASER_TRAP";


    public static ATrap createTrap(String type, AssetManager assetManager, float x, float y) throws NullPointerException {
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
        } else if (type.equals(BOULDER_TRAP)) {
            return new BoulderTrap(assetManager, x, y);
        } else if (type.equals(LASER_TRAP)) {
            return new LaserTotem(assetManager, x, y);
        }

        throw new NullPointerException("Received type: " + type);
    }

    public static ATrap createTrapFromMP(MapProperties mp, AssetManager assetManager) {
        // Create the basic trap
        ATrap returnMe = createTrap(mp.get("type", String.class), assetManager,
                mp.get("x", Float.class),
                mp.get("y", Float.class));

        // Customization
        if (mp.containsKey("delay")) {
            // Set the delay
            returnMe.setDelay(mp.get("delay", Integer.class));
        }

        if (mp.get("type", String.class).equals(FIRE_TRAP)) {
            if (mp.containsKey("direction")) {
                ((FireTrap)returnMe).initDirection(Directionality.valueOf(mp.get("direction", String.class)));
            }
        }

        if (mp.get("type", String.class).equals(EXIT_PORTAL)) {
            if (mp.containsKey("direction")) {
                ((ExitPortal) returnMe).setModifiedDirection(mp.get("direction", String.class));
            }
        }

        if (mp.get("type", String.class).equals(BOULDER_TRAP)) {
            if (mp.containsKey("direction")) {
                ((BoulderTrap) returnMe).setDirection(mp.get("direction", String.class));
            }
        }

        return returnMe;
    }
}
