package com.totlc.Actors.projectiles;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.totlc.Actors.Player;
import com.totlc.Actors.enemies.Enemy;
import com.totlc.Actors.totlcObject;
import com.totlc.AssetList;

public class Arrow extends Projectile {

    public Arrow(AssetManager assetManager, float x, float y) {
        super(assetManager, x, y);

        setWidth(128);
        setHeight(32);
        setHitBox(new Rectangle(getX(), getY(), getWidth(), getHeight()));
        setAttack(1);
        setScaleFactor(0.75f);

        assetManager.load(AssetList.PROJECTILE_ARROW.toString(), Texture.class);
        setTexture(new Texture(Gdx.files.internal(AssetList.PROJECTILE_ARROW.toString())));
    }

    @Override
    public void act(float delta) {
        moveUnit(delta);
        System.out.println("VEL " + getVel() + "; " + getVelocityAngle());
        if (isOutOfBounds()) {
            remove();
        }
    }

    @Override
    public void draw(Batch batch, float alpha) {
        if (getAssetManager().update()) {
            batch.draw(getTexture(), getX(), getY(), getWidth() / 2, getHeight() / 2, getWidth() * getScaleFactor(), getHeight() * getScaleFactor(), 1f, 1f, getVelocityAngle(), 0, 0, 128, 32, false, false);
        }
    }

    @Override
    public boolean collidesWith(Actor otherActor) {
        if (otherActor instanceof Player ||
                otherActor instanceof Enemy) {
            return true;
        }

        return false;
    }

    @Override
    public void endCollidesWith(Actor otherActor) {

    }
}
