package com.totlc.Actors.traps;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Rectangle;
import com.totlc.Actors.damage.Boulder;
import com.totlc.Actors.effects.Exclamation;
import com.totlc.Actors.effects.RockFall;
import com.totlc.AssetList;
import com.totlc.Directionality;
import com.totlc.TradersOfTheLastCarp;

import java.awt.*;

public class BoulderTrap extends ATrap {

    // Boulder trap has no physical form.
    private static float width = 50;
    private static float height = 50;
    private static long delay = 2000;
    private Directionality direction;

    public BoulderTrap(AssetManager assetManager, float x, float y) {
        this(assetManager, new Rectangle(x, y, width, height), delay);
    }

    public BoulderTrap(AssetManager assetManager, Rectangle r, long delay) {
        super(assetManager, r, delay);
    }

    public BoulderTrap(AssetManager assetManager, float x, float y, Directionality d) {
        this(assetManager, x, y);
        this.direction = d;
    }

    @Override
    public void activate() {
        if (direction == null) {
            getStage().addActor(new Boulder(getAssetManager(), getX(), getY(), 3, 0));
        } else {
            Boulder b = new Boulder(getAssetManager(), getX(), getY(), 3, 0);
            if (direction.isFacingRight()) {
                b.setBoulderVel(new Point(300, 0));
            }
            getStage().addActor(b);
        }
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

    public void setDirection(String d) {
        this.direction = Directionality.valueOf(d);
    }
}