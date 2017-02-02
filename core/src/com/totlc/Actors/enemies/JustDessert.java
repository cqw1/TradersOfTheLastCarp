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

    //Size constants.
    private static float width = 64, height = 64;

    /**
     * Constructor for a default slime
     *
     * @param assetManager
     * @param x
     * @param y
     */
    public JustDessert(AssetManager assetManager, float x, float y, AMovement movement) {
        super(assetManager, new Rectangle(x, y, width, height), movement, hp, atk);
    }

    /**
     * Constructor for custom-sized enemy
     *
     * @param assetManager
     * @param r
     * @param movement
     */
    public JustDessert(AssetManager assetManager, Rectangle r, AMovement movement) {super(assetManager, r, movement, hp, atk);}

    @Override
    public void act(float deltaTime) {
        super.act(deltaTime);
    }

    @Override
    public void draw(Batch batch, float alpha) {
        drawHealth(batch, alpha, -(int)getHitBoxWidth() / 2, -(int)getHitBoxHeight() / 2);
        batch.draw((Texture) getAssetManager().get(AssetList.FLAN.toString()), getX(), getY(), getWidth(), getHeight());
    }
}
