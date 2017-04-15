package com.totlc.Actors.damage;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.*;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.totlc.Actors.effects.WaterBurst;
import com.totlc.Actors.enemies.AEnemy;
import com.totlc.Actors.players.Player;
import com.totlc.Actors.terrain.AWall;
import com.totlc.AssetList;

public class Bubble extends Damage {

    private TextureAtlas bubbleAtlas;
    private Animation<TextureRegion> bubbleAnimation;
    private Sound bubbleSound;

    private long stunDuration;

    private static float width = 66, height = 64;

    public Bubble(AssetManager assetManager, float x, float y, int attack, int damageType, long stunDuration) {
        super(assetManager, new Rectangle(x, y, width, height), attack, damageType);

        bubbleAtlas = assetManager.get(AssetList.SPIT_BUBBLE.toString());
        bubbleAnimation = new Animation<TextureRegion>(1 / 12f, bubbleAtlas.getRegions());
        bubbleSound = Gdx.audio.newSound(Gdx.files.internal("sounds/splash1.mp3"));
        getHitBox().setOrigin(getX() + getWidth() / 2, getY() + getHeight() / 2);
        setStunDuration(stunDuration);
    }

    @Override
    public void act(float delta) {
        moveUnit(delta);
        increaseAnimationTime(delta);
        if (isOutOfBounds()) {
            remove();
        }
    }

    @Override
    public void draw(Batch batch, float alpha) {
        batch.draw(bubbleAnimation.getKeyFrame(getAnimationTime(), true), getX(), getY());
    }

    @Override
    public boolean collidesWith(Actor otherActor) {
        if (otherActor instanceof AEnemy){
            bubbleSound.play(1);
            getStage().addActor(new WaterBurst(getAssetManager(), (float)getHitBoxCenter().getX(), (float)getHitBoxCenter().getY()));
            return true;
        } else if (otherActor instanceof AWall){
            bubbleSound.play(1);
            getStage().addActor(new WaterBurst(getAssetManager(), (float)getHitBoxCenter().getX(), (float)getHitBoxCenter().getY()));
            return true;
        }
        return false;
    }

    @Override
    public void endCollidesWith(Actor otherActor) {

    }

    public long getStunDuration() {
        return stunDuration;
    }

    public void setStunDuration(long stunDuration) {
        this.stunDuration = stunDuration;
    }
}
