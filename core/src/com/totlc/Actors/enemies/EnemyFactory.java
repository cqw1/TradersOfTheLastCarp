package com.totlc.Actors.enemies;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.maps.MapProperties;
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
    public static final String HARPOONER = "HARPOONER";
    public static final String FISHERMAN = "FISHERMAN";
    public static final String DUMMY = "DUMMY";
    public static final String WALLFACED = "WALLFACED";
    public static final String CHAPERONE = "CHAPERONE";
    public static final String STORK = "STORK";

    public static AEnemy createDefaultEnemy(String type, AssetManager assetManager, float x, float y) throws NullPointerException {
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
        } else if (type.equals(HARPOONER)) {
            return new EnthralledHarpooner(assetManager, x, y, new BasicMovement(TradersOfTheLastCarp.player));
        } else if (type.equals(FISHERMAN)) {
            return new EnthralledFisherman(assetManager, x, y, new BasicMovement(TradersOfTheLastCarp.player));
        } else if (type.equals(DUMMY)) {
            return new Dummy(assetManager, x, y, new Immobile(TradersOfTheLastCarp.player));
        } else if (type.equals(WALLFACED)) {
            return new WallfacedProtector(assetManager, x, y, new BasicMovement(TradersOfTheLastCarp.player));
        } else if (type.equals(CHAPERONE)) {
            return new FellChaperone(assetManager, x, y, new ZoningMovement(TradersOfTheLastCarp.player));
        } else if (type.equals(STORK)) {
            return new StorkTrooper(assetManager,x ,y, new IntervalMovement(TradersOfTheLastCarp.player, 600, 200));
        }

        throw new NullPointerException("Received type: " + type);
    }

    public static AEnemy createEnemyFromMP(MapProperties mp, AssetManager assetManager) {
        AEnemy returnMe = createDefaultEnemy(mp.get("type", String.class), assetManager,
                mp.get("x", Float.class),
                mp.get("y", Float.class));


        // Customization done here
        if (mp.containsKey("movement")) {
            // Movement
            returnMe.setMovement(MovementFactory.createMovement(mp.get("movement", String.class)));
        }

        if (mp.containsKey("hp")) {
            // HP
            returnMe.setHpCurrent(mp.get("hp", Integer.class));
            returnMe.setHpMax(mp.get("hp", Integer.class));
        }

        if (mp.containsKey("invincible")) {
            // Invincibility
            returnMe.setInvincible(true);
        }

        return returnMe;
    }
}
