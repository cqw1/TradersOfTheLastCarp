package com.totlc.Actors.enemies;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Rectangle;
import com.totlc.Actors.enemies.movement.AMovement;

public class GelatinKing extends AEnemy {

    public GelatinKing(AssetManager assetManager, Rectangle r, AMovement movement, int hp, int atk) {
        super(assetManager, r, movement, hp, atk);
    }

    @Override
    public void draw(Batch batch, float alpha) {

    }
}
