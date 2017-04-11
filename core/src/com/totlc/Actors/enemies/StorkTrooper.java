package com.totlc.Actors.enemies;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Rectangle;
import com.totlc.Actors.enemies.movement.AMovement;

public class StorkTrooper extends AEnemy {

    // Stat constants.
    private static int id = 9;
    private static int basehp = 2;
    private static int atk = 1;

    public StorkTrooper(AssetManager assetManager, Rectangle r, AMovement movement, int hp, int atk) {
        super(assetManager, r, movement, hp, atk);
    }

    @Override
    public void draw(Batch batch, float alpha) {

    }
}
