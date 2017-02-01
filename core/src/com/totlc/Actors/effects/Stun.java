package com.totlc.Actors.effects;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.g2d.*;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.totlc.Actors.TotlcObject;
import com.totlc.AssetList;
import com.totlc.TradersOfTheLastCarp;

import java.awt.geom.Point2D;

public class Stun extends AEffect {

    // Texture information
    private TextureAtlas particleAtlas;
    private ParticleEffect stun;
    private Point2D textureDimensions;

    // Bookkeeping variables.
    private long duration;
    private long startTime;
    TotlcObject followMe;

    private boolean drawText = false;
    private float removeDelay = 800;

    // Font information.
    private BitmapFont font;

    public Stun(AssetManager assetManager, TotlcObject actor, long d) {
        super(assetManager, new Rectangle(actor.getX(), actor.getY(), 1, 1));
        this.followMe = actor;
        loadAssets(assetManager, d);
    }

    public Stun(AssetManager assetManager, TotlcObject actor, long d, boolean drawText) {
        super(assetManager, new Rectangle(actor.getX(), actor.getY(), 1, 1));
        setDrawText(drawText);
        this.followMe = actor;
        loadAssets(assetManager, d);
    }

    @Override
    public void act(float deltaTime){
        increaseAnimationTime(deltaTime);
        moveAbs((float)followMe.getCenter().getX() + (float)textureDimensions.getX(), (float)followMe.getCenter().getY() + followMe.getHitBoxHeight() * 2.0f);
        stun.setPosition(getX(), getY());
        if (System.currentTimeMillis() - startTime > (getDuration())){
            drawText = false;
            if (System.currentTimeMillis() - startTime > (getDuration() + removeDelay)) {
                remove();
            }
        }
    }

    @Override
    public void draw(Batch batch, float alpha) {
        // Draw indicator.
        stun.draw(batch, Gdx.graphics.getDeltaTime());

        // Draw text.
        if (isDrawText()){
            font.getData().setScale(1.0f);
            double timeRemaining = Math.ceil((getDuration() - (System.currentTimeMillis() - startTime)) / 100) / 10;
            font.draw(batch, "" + timeRemaining, getX() + (float)textureDimensions.getX() * 0.5f, getY() + (float)textureDimensions.getY() * 0.5f);
        }
    }

    @Override
    public boolean collidesWith(Actor otherActor) {
        return false;
    }

    @Override
    public void endCollidesWith(Actor otherActor) {

    }

    private void loadAssets(AssetManager assetManager, long d){
        setDuration(d);
        startTime = System.currentTimeMillis();
        // Load assets.
        font = TradersOfTheLastCarp.systemFont0;
        particleAtlas = assetManager.get(AssetList.PARTICLES.toString());
        stun = new ParticleEffect();
        stun.setPosition(getX(), getY());
        stun.load(Gdx.files.internal(AssetList.STUN.toString()), particleAtlas);
        ParticleEmitter e = stun.findEmitter("dizzy");
        textureDimensions = new Point2D.Float(e.getScale().getHighMax(), e.getScale().getHighMax());
        // Configure effect duration.
        e.getLife().setHigh(getDuration() + removeDelay / 2);
        e.getLife().setLow(getDuration() + removeDelay / 2);

        e.getRotation().setHigh(720 * (getDuration() / 1000));
        e.getRotation().setLow(0);
        e.getAngle().setHigh(720 * (getDuration() / 1000));
        e.getAngle().setLow(0);
//        System.out.println("O[" + followMe.getX() + ", " + followMe.getY() + "]");
//        System.out.println("C[" + followMe.getCenter().getX() + ", " + followMe.getCenter().getY() + "]");
        stun.start();
    }

    public long getDuration() {
        return duration;
    }

    public void setDuration(long duration) {
        this.duration = duration;
    }

    public boolean isDrawText() {
        return drawText;
    }

    public void setDrawText(boolean drawText) {
        this.drawText = drawText;
    }
}
