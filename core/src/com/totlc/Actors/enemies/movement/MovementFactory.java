package com.totlc.Actors.enemies.movement;

import com.totlc.TradersOfTheLastCarp;

public class MovementFactory {

    public static final String BASIC = "BASIC";
    public static final String IMMOBILE = "IMMOBILE";
    public static final String INTERVAL = "INTERVAL";

    public static AMovement createMovement(String type) {
        if (type.equals(BASIC)) {
            return new BasicMovement(TradersOfTheLastCarp.player);
        } else if (type.equals(IMMOBILE)) {
            return new Immobile(TradersOfTheLastCarp.player);
        } else if (type.equals(INTERVAL)) {
            return new IntervalMovement(TradersOfTheLastCarp.player);
        }

        return null;
    }
}
