package com.totlc.Actors.projectiles;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.totlc.Actors.Player;
import com.totlc.Actors.effects.ArrowBreak;
import com.totlc.Actors.effects.Impact;
import com.totlc.Actors.enemies.AEnemy;
import com.totlc.Actors.terrain.AWall;
import com.totlc.AssetList;

public class Arrow extends Projectile {

    public Arrow(AssetManager assetManager, float x, float y, int damageType) {
        super(assetManager, new Rectangle(x, y, 80, 32));

        setDamageType(damageType);
        setAttack(1);
        setScaleFactor(0.75f);

        setTexture(new Texture(Gdx.files.internal(AssetList.PROJECTILE_ARROW.toString())));
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
        batch.draw(getTexture(), getX(), getY(), getWidth() / 2, getHeight() / 2, getWidth(), getHeight(), getScaleFactor(), getScaleFactor(), getVelocityAngle(), 0, 0, 128, 32, false, false);
    }

    @Override
    public boolean collidesWith(Actor otherActor) {
        if (otherActor instanceof Player) {
            getStage().addActor(new Impact(getAssetManager(), getX(), getY()));
            return true;
        } else if (otherActor instanceof AEnemy){
            if (((AEnemy) otherActor).isInvincible()){
                getStage().addActor(new ArrowBreak(getAssetManager(), getX(), getY()));
            } else{
                getStage().addActor(new Impact(getAssetManager(), getX(), getY()));
            }
            return true;
        } else if (otherActor instanceof AWall){
//            getStage().addActor(new ArrowBreak(getAssetManager(), getX(), getY()));
//            return true;
        }

        return false;
    }

    @Override
    public void endCollidesWith(Actor otherActor) {

    }
}
