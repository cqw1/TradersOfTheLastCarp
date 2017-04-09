package com.totlc.Actors.traps;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.math.Rectangle;
import com.totlc.Actors.damage.Boulder;
import com.totlc.Actors.effects.Exclamation;
import com.totlc.Actors.effects.RockFall;
import com.totlc.TradersOfTheLastCarp;

public class BoulderTrap extends ATrap {

    // Boulder trap has no physical form.
    private static float width = 50;
    private static float height = 50;
    private static long delay = 2000;

    public BoulderTrap(AssetManager assetManager, float x, float y) {
        this(assetManager, new Rectangle(x, y, width, height), delay);
    }

    public BoulderTrap(AssetManager assetManager, Rectangle r, long delay) {
        super(assetManager, r, delay);
    }

    @Override
    public void activate() {
        getStage().addActor(new Boulder(getAssetManager(), getX(), getY(), 3, 0));
    }

    @Override
    public void setup() {
        if (!isSetup()) {
            // If someone's already triggered this trap
            getStage().addActor(new Exclamation(getAssetManager(), (float) getHitBoxCenter().getX() + getHitBoxWidth(), (float) getHitBoxCenter().getY() + getHitBoxHeight() / 3));
            getStage().addActor(new RockFall(getAssetManager(), (float) getHitBoxCenter().getX(), TradersOfTheLastCarp.CONFIG_HEIGHT));
            setStartTime(System.currentTimeMillis());
            setSetup(true);
        }
    }
}