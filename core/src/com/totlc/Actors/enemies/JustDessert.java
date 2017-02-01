package com.totlc.Actors.enemies;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Rectangle;
import com.totlc.Actors.enemies.movement.AMovement;
import com.totlc.AssetList;

/**
 * Slime enemy that splits upon taking damage. Copies have half health.
 * Size scales with health.
 * If only one health remains when hit, it dies.
 * Moves around randomly in a small area. Will jump at player if they get close.
 * Jump leaves behind a sticky syrup puddle that slows movement.
 */
public class JustDessert extends AEnemy{

    // Stat constants.
    private static int id = 2;
    private static int hp = 7;
    private static int atk = 1;

    public JustDessert(AssetManager assetManager, Rectangle r, AMovement movement) {super(assetManager, r, movement);}

    @Override
    public void act(float deltaTime) {
        if (checkStun()) {
            return;
        }

        getMovement().move(this, deltaTime);
    }

    @Override
    public void draw(Batch batch, float alpha) {
        drawHealth(batch, alpha, (int)getWidth() / 2, 12);
        batch.draw((Texture) getAssetManager().get(AssetList.FLAN.toString()), getX(), getY(), getWidth(), getHeight());
    }
}
