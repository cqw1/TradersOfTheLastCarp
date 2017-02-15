package com.totlc.Actors.enemies;

import com.badlogic.gdx.assets.AssetManager;
import com.totlc.Actors.enemies.movement.*;
import com.totlc.TradersOfTheLastCarp;

public class EnemyFactory {

    public static final String TYPE = "ENEMIES";
    public static final String SPIDER = "SPIDER";
    public static final String FLAN = "FLAN";
    public static final String PANGOLINI = "PANGOLINI";
    public static final String STAR = "STAR";
    public static final String GELATIN = "GELATIN";
    public static final String GELATIN_KING = "GELATIN_KING";

    public static AEnemy createDefaultEnemy(String type, AssetManager assetManager, float x, float y) {
        if (type.equals(SPIDER)) {
            return new Spider(assetManager, x, y, new IntervalMovement(TradersOfTheLastCarp.player));
        } else if (type.equals(FLAN)) {
            return new JustDessert(assetManager, x, y, new ProximityBasedAggro(TradersOfTheLastCarp.player));
        } else if (type.equals(PANGOLINI)) {
            return new DaredevilPangolini(assetManager, x, y, new BasicMovement(TradersOfTheLastCarp.player));
        } else if (type.equals(STAR)) {
            return new Stargazer(assetManager, x, y, new BasicMovement(TradersOfTheLastCarp.player));
        } else if (type.equals(GELATIN_KING)) {
            return new GelatinKing(assetManager, x, y, new RandomMovement(TradersOfTheLastCarp.player), true);
        } else if (type.equals(GELATIN)) {
            return new GelatinKing(assetManager, x, y, new RandomMovement(TradersOfTheLastCarp.player));
        }

        return null;
    }

    public static AEnemy createCustomMovementEnemy(String type, AssetManager assetManager, float x, float y, String movement) {
        AEnemy returnMe = createDefaultEnemy(type, assetManager, x, y);
        returnMe.setMovement(MovementFactory.createMovement(movement));
        return returnMe;
    }
}
