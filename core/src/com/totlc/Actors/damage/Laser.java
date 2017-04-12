package com.totlc.Actors.damage;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.ParticleEffect;
import com.badlogic.gdx.graphics.g2d.ParticleEmitter;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.totlc.Actors.players.Player;
import com.totlc.Actors.effects.Dust;
import com.totlc.Actors.effects.Sparks;
import com.totlc.Actors.enemies.AEnemy;
import com.totlc.Actors.terrain.AWall;
import com.totlc.AssetList;

public class Laser extends Damage {

    private static int damage = 1;
    private static float longSide = 100;
    private static float shortSide = 20;
    
    private TextureAtlas particleAtlas;
    private ParticleEffect laser;

    public Laser(AssetManager assetManager, float x, float y, int damageType) {
        super(assetManager, new Rectangle(x, y, longSide, shortSide), damage, damageType);

        setDamageType(damageType);
        setScaleFactor(0.8f);
        getHitBox().setOrigin(getX() + getWidth() / 2, getY() + getHeight() / 2);
        getHitBox().setScale(0.2f, 1);
        setTexture(new Texture(Gdx.files.internal(AssetList.PROJECTILE_LASER.toString())));

        particleAtlas = assetManager.get(AssetList.PARTICLES.toString());
        laser = new ParticleEffect();
        laser.load(Gdx.files.internal(AssetList.PARTICLE_LASER.toString()), particleAtlas);

        laser.start();
        laser.setPosition((float)getHitBoxCenter().getX(), (float)getHitBoxCenter().getY());
    }

    @Override
    public void act(float delta) {
        moveUnit(delta);
        laser.setPosition((float)getHitBoxCenter().getX(), (float)getHitBoxCenter().getY());
        for (ParticleEmitter p : this.laser.getEmitters()){
            p.getAngle().setHighMax(getVelocityAngle());
            p.getAngle().setHighMin(getVelocityAngle());
            p.getAngle().setLow(getVelocityAngle());
            p.getRotation().setHighMax(getVelocityAngle() + 90);
            p.getRotation().setHighMin(getVelocityAngle() + 90);
            p.getRotation().setLow(getVelocityAngle() + 90);
        }
        if (isOutOfBounds()) {
            remove();
        }
    }

    @Override
    public void draw(Batch batch, float alpha) {
        laser.draw(batch, Gdx.graphics.getDeltaTime());
        batch.draw(getTexture(), getX(), getY(), getWidth() / 2, getHeight() / 2, getWidth(), getHeight(), getScaleFactor(), getScaleFactor(), getVelocityAngle(), 0, 0, (int) longSide, (int)shortSide, false, false);
    }

    @Override
    public boolean collidesWith(Actor otherActor) {
        if (otherActor instanceof Player) {
            getStage().addActor(new Sparks(getAssetManager(), (float)getCenter().getX(), (float)getCenter().getY()));
            return true;
        } else if (otherActor instanceof AEnemy){
            getStage().addActor(new Sparks(getAssetManager(), (float)getCenter().getX(), (float)getCenter().getY()));
            return true;
        } else if (otherActor instanceof AWall){
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
