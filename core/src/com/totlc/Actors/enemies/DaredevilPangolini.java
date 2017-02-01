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
    private static int hp = 1;
    private static int atk = 2;

    public DaredevilPangolini(AssetManager assetManager, Rectangle r, AMovement movement) {
        super(assetManager, r, movement);
        this.setMaxVel(100);
        this.setHpMax(hp);
        this.setHpCurrent(hp);
        this.setAttack(atk);
        this.setSpeed(40);
        this.setFriction(0.99f);
    }

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
        batch.draw((Texture) getAssetManager().get(AssetList.PANGOLINI.toString()), getX(), getY(), getWidth(), getHeight());
    }
}
