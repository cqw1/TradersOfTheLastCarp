package com.totlc.Actors.enemies;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Rectangle;
import com.totlc.Actors.enemies.movement.AMovement;
import com.totlc.AssetList;

public class Dummy extends AEnemy {

    // Stat constants.
    private static int id = -1;
    private static int hp = 2;
    private static int atk = 1;

    private static float width = 96;
    private static float height = 112;

    private static float maxVel = 100;
    private static float speed = 40;
    private static float friction = 0.8f;

    public Dummy(AssetManager assetManager, float x, float y, AMovement movement) {
        this(assetManager, new Rectangle(x, y, width, height), movement);
    }

    public Dummy(AssetManager assetManager, Rectangle r, AMovement movement) {
        super(assetManager, r, movement, hp, atk);
        initMovement(friction, maxVel, speed);
        setTexture(new Texture(Gdx.files.internal(AssetList.PLAYER_STAND_LEFT.toString())));
        setScale(1.1f, 1f);
    }

    @Override
    public void act(float deltaTime) {
        super.act(deltaTime);
    }

    @Override
    public void draw(Batch batch, float alpha) {
        drawHealth(batch, alpha, -(int)getHitBoxWidth() / 2, -(int)getHitBoxHeight() / 2);
        batch.draw(getTexture(), getX(), getY(), 0, 0, getWidth(), getHeight(), getScaleX(), getScaleY(), 0, 0, 0, 128, 128, false, false);
        drawStatuses(batch, alpha);
    }
}
