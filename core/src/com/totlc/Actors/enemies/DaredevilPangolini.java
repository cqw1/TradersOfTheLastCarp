package com.totlc.Actors.enemies;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Rectangle;

/**
 * Enemy that rolls to move and attack.
 * Stands idly when not rolling.
 * When rolling, becomes invulnerable to damage.
 * Alternates between rolling and idle phases.
 */
public class DaredevilPangolini extends AEnemy {

    // Stat constants.
    private static int id = 3;
    private static int hp = 1;
    private static int atk = 2;

    public DaredevilPangolini(AssetManager assetManager, Rectangle r) {
        super(assetManager, r);
    }

    @Override
    public void act(float deltaTime) {

    }

    @Override
    public void draw(Batch batch, float alpha) {

    }
}
