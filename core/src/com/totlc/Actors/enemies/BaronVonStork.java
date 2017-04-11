package com.totlc.Actors.enemies;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Rectangle;
import com.totlc.Actors.enemies.movement.AMovement;

public class BaronVonStork extends AEnemy{
    // Stat constants.
    private static int id = 11;
    private static int basehp = 8;
    private static int atk = 2;

    public BaronVonStork(AssetManager assetManager, Rectangle r, AMovement movement, int hp, int atk) {
        super(assetManager, r, movement, hp, atk);
    }

    @Override
    public void draw(Batch batch, float alpha) {

    }
}
