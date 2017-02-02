package com.totlc.Actors.enemies;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.totlc.Actors.enemies.movement.AMovement;
import com.totlc.AssetList;

/**
 * Enemy that rolls to move and attack.
 * Stands idly when not rolling.
 * When rolling, becomes invulnerable to damage.
 * Alternates between rolling and idle phases.
 */
public class DaredevilPangolini extends AEnemy {

    // Stat constants.
    private static int id = 3;
    private static int hp = 3;
    private static int atk = 2;

    private static float width = 72;
    private static float height = 108;

    private static float maxVel = 100;
    private static float speed = 40;
    private static float friction = 0.8f;

    public DaredevilPangolini(AssetManager assetManager, float x, float y, AMovement movement) {
        super(assetManager, new Rectangle(x, y, width, height), movement, hp, atk);
        initMovement(friction, maxVel, speed);
    }

    public DaredevilPangolini(AssetManager assetManager, Rectangle r, AMovement movement) {
        super(assetManager, r, movement, hp, atk);
        initMovement(friction, maxVel, speed);
    }

    @Override
    public void act(float deltaTime) {
        super.act(deltaTime);
    }

    @Override
    public void draw(Batch batch, float alpha) {
        drawHealth(batch, alpha, -(int)getHitBoxWidth() / 2, -(int)getHitBoxHeight() / 2);
        batch.draw((Texture) getAssetManager().get(AssetList.PANGOLINI.toString()), getX(), getY(), getWidth(), getHeight());
        drawStatuses(batch, alpha);
    }
}
