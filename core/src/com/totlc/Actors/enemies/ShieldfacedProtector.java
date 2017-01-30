package com.totlc.Actors.enemies;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Rectangle;

/**
 * Large enemy that is shielded from the front.
 * Must be damaged from other sides.
 * Moves relatively slowly and has only a melee attack.
 */
public class ShieldfacedProtector extends AEnemy {

    // Stat constants.
    private static int id = 4;
    private static int hp = 2;
    private static int atk = 3;

    public ShieldfacedProtector(AssetManager assetManager, Rectangle r) {
        super(assetManager, r);
    }

    @Override
    public void act(float deltaTime) {

    }

    @Override
    public void draw(Batch batch, float alpha) {

    }
}
