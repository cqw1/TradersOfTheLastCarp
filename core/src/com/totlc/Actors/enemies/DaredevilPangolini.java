package com.totlc.Actors.enemies;

import com.badlogic.gdx.Gdx;
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
    private static int atk = 1;

    private static float width = 64;
    private static float height = 96;

    private static float maxVel = 100;
    private static float speed = 40;
    private static float friction = 0.8f;

    public DaredevilPangolini(AssetManager assetManager, float x, float y, AMovement movement) {
        this(assetManager, new Rectangle(x, y, width, height), movement);
    }

    public DaredevilPangolini(AssetManager assetManager, Rectangle r, AMovement movement) {
        super(assetManager, r, movement, hp, atk);
        initMovement(friction, maxVel, speed);
        setTexture(new Texture(Gdx.files.internal(AssetList.PANGOLINI.toString())));
        setScale(1.2f);
        moveHitBox(8, 8);
    }

    @Override
    public void act(float deltaTime) {
        super.act(deltaTime);
    }

    @Override
    public void draw(Batch batch, float alpha) {
        drawHealth(batch, alpha, -(int)getHitBoxWidth() / 2, -(int)getHitBoxHeight() / 2);
        batch.draw(getTexture(), getX(), getY(), 0, 0, getWidth(), getHeight(), getScaleX(), getScaleY(), 0, 0, 0, 156, 226, false, false);
        drawStatuses(batch, alpha);
    }
}
