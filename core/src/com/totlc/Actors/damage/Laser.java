package com.totlc.Actors.damage;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.totlc.Actors.Player;
import com.totlc.Actors.effects.Sparks;
import com.totlc.Actors.enemies.AEnemy;
import com.totlc.Actors.terrain.AWall;
import com.totlc.AssetList;

public class Laser extends Damage {

    private static int damage = 1;
    private static float longSide = 100;
    private static float shortSide = 21;

    public Laser(AssetManager assetManager, float x, float y, int damageType) {
        super(assetManager, new Rectangle(x, y, longSide, shortSide), damage, damageType);

        setDamageType(damageType);
        setScaleFactor(1f);
        getHitBox().setOrigin(getX() + getWidth() / 2, getY() + getHeight() / 2);

        setTexture(new Texture(Gdx.files.internal(AssetList.PROJECTILE_LASER.toString())));
    }

    @Override
    public void act(float delta) {
        moveUnit(delta);
        if (isOutOfBounds()) {
            remove();
        }
    }

    @Override
    public void draw(Batch batch, float alpha) {
        batch.draw(getTexture(), getX(), getY(), getWidth() / 2, getHeight() / 2, getWidth(), getHeight(), getScaleFactor(), getScaleFactor(), getVelocityAngle(), 0, 0, 100, 21, false, false);
    }

    @Override
    public boolean collidesWith(Actor otherActor) {
        if (otherActor instanceof Player) {
            getStage().addActor(new Sparks(getAssetManager(), getX(), getY()));
            return true;
        } else if (otherActor instanceof AEnemy){
            getStage().addActor(new Sparks(getAssetManager(), getX(), getY()));
            return true;
        } else if (otherActor instanceof AWall){
            getStage().addActor(new Sparks(getAssetManager(), getX(), getY()));
            return true;
        }

        return false;
    }

    @Override
    public void endCollidesWith(Actor otherActor) {

    }
}
