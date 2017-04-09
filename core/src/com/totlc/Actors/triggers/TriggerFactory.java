package com.totlc.Actors.triggers;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.MapProperties;
import com.badlogic.gdx.math.Rectangle;

public class TriggerFactory {

    public static final String TYPE = "TRIGGERS";
    public static final String BUTTON = "BUTTON";
    public static final String TELE_PAD = "TELEPORT_PAD";
    public static final String ENTRANCE_PORTAL = "ENTRANCE_PORTAL";
    public static final String INTERVAL = "INTERVAL";

    public static ATrigger createTrigger(String type, AssetManager assetManager, float x, float y) throws NullPointerException {
        if (type.equals(BUTTON)) {
            return new ButtonTrigger(assetManager, x, y);
        } else if (type.equals(TELE_PAD)) {
            return new TeleportPad(assetManager, x, y);
        } else if (type.equals(ENTRANCE_PORTAL)) {
            return new EntrancePortal(assetManager, x, y);
        } else if (type.equals(INTERVAL)) {
            return new IntervalTrigger(assetManager, x, y);
        }

        throw new NullPointerException("Received type: " + type);
    }

    public static ATrigger createTriggerFromMP(MapProperties mp, AssetManager assetManager) {
        ATrigger returnMe = createTrigger(mp.get("type", String.class), assetManager,
                mp.get("x", Float.class),
                mp.get("y", Float.class));

        //Customizable properties go here
        if (mp.containsKey("delay")) {
            ((IntervalTrigger) returnMe).setInterval(mp.get("delay", Integer.class));
            ((IntervalTrigger) returnMe).setStartTime(mp.get("delay", Integer.class));
        }

        if (mp.containsKey("start")) {
            ((IntervalTrigger) returnMe).setStartTime(mp.get("start", Integer.class));
        }

        return returnMe;
    }
}
