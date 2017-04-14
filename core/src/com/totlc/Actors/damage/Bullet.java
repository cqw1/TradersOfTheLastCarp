package com.totlc.Actors.damage;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.totlc.Actors.effects.Dust;
import com.totlc.Actors.effects.Sparks;
import com.totlc.Actors.enemies.AEnemy;
import com.totlc.Actors.players.Player;
import com.totlc.Actors.terrain.AWall;
import com.totlc.AssetList;

public class Bullet extends Damage {

    private static int damage = 1;
    private static float longSide = 151;
    private static float shortSide = 11;

    public Bullet(AssetManager assetManager, float x, float y, int damageType) {
        this(assetManager, new Rectangle(x, y, longSide, shortSide), damageType);
    }
    public Bullet(AssetManager assetManager, Rectangle r, int damageType) {
        super(assetManager, r, damage, 1);

        setDamageType(damageType);
        getHitBox().setOrigin(getX() + getWidth() / 2, getY() + getHeight() / 2);
        setScaleFactor(.5f);
        getHitBox().setScale(0.1f, 1);
        setTexture(new Texture(Gdx.files.internal(AssetList.BULLET.toString())));
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
        batch.draw(getTexture(), getX(), getY(), 0, 0, getWidth(), getHeight(), getScaleFactor(), getScaleFactor(), getVelocityAngle(), 0, 0, (int)longSide, (int)shortSide, false, false);
    }

    @Override
    public boolean collidesWith(Actor otherActor) {
        if (otherActor instanceof AWall){
            getStage().addActor(new Sparks(getAssetManager(), (float)getCenter().getX(), (float)getCenter().getY()));
            getStage().addActor(new Dust(getAssetManager(), (float)getCenter().getX(), (float)getCenter().getY()));
            return true;
        }
        return false;
    }

    @Override
    public void endCollidesWith(Actor otherActor) {

    }
}
