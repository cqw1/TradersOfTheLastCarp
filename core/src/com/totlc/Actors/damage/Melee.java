package com.totlc.Actors.damage;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.scenes.scene2d.Actor;

public class Melee extends Damage{

    private long startTime, duration;

    public Melee(AssetManager assetManager, float x, float y, float width, float height, long duration, int attack, int damageType) {
        super(assetManager, new Rectangle(x, y, width, height), attack, damageType);
        this.duration = duration;
        this.startTime = System.currentTimeMillis();
    }

    @Override
    public void act(float deltaTime){
        if (System.currentTimeMillis() - startTime > this.duration){
            remove();
        }
    }

    @Override
    public void draw(Batch batch, float alpha) {
        // No draw.
    }

    public boolean collidesWith(Actor otherActor) { return false; }


    public void endCollidesWith(Actor otherActor) {

    }
}
