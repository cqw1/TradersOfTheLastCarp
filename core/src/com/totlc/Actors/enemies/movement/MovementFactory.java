package com.totlc.Actors.enemies.movement;

import com.totlc.TradersOfTheLastCarp;

public class MovementFactory {

    public static final String BASIC = "BASIC";
    public static final String IMMOBILE = "IMMOBILE";
    public static final String INTERVAL = "INTERVAL";
    public static final String RANDOM = "RANDOM";
    public static final String PROXIMITY = "PROXIMITY";

    public static AMovement createMovement(String type) throws NullPointerException {
        if (type.equals(BASIC)) {
            return new BasicMovement(TradersOfTheLastCarp.player);
        } else if (type.equals(IMMOBILE)) {
            return new Immobile(TradersOfTheLastCarp.player);
        } else if (type.equals(INTERVAL)) {
            return new IntervalMovement(TradersOfTheLastCarp.player);
        } else if (type.equals(RANDOM)) {
            return new RandomMovement(TradersOfTheLastCarp.player);
        } else if (type.equals(PROXIMITY)) {
            return new ProximityBasedAggro(TradersOfTheLastCarp.player);
        }

        throw new NullPointerException("Received type: " + type);
    }
}
