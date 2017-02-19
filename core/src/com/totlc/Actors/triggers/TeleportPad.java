package com.totlc.Actors.triggers;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.totlc.Actors.effects.TeleportBeam;
import com.totlc.AssetList;

public class TeleportPad extends ATrigger {

    // Asset and animation constants.
    private TextureAtlas buttonTextureAtlas;
    private Animation<TextureRegion> padAnimation;

    private static long hideDelay = 2000;
    private long triggerTime;

    private static float width = 76;
    private static float height = 36;

    public TeleportPad(AssetManager assetManager, float x, float y) {
        super(assetManager, new Rectangle(x, y, width, height));

        buttonTextureAtlas = assetManager.get(AssetList.TELEPORT_PAD.toString());
        padAnimation = new Animation<TextureRegion>(1 / 16f, buttonTextureAtlas.getRegions());
    }

    public TeleportPad(AssetManager assetManager, Rectangle r) {
        super(assetManager, r);

        buttonTextureAtlas = assetManager.get(AssetList.TELEPORT_PAD.toString());
        padAnimation = new Animation<TextureRegion>(1 / 16f, buttonTextureAtlas.getRegions());
    }

    @Override
    public void act(float deltaTime){
        increaseAnimationTime(deltaTime);
        if (!isTriggered()) {
            if(System.currentTimeMillis() - this.triggerTime >= hideDelay){
                setAnimationTime(0);
            }
        } else{
            this.triggerTime = System.currentTimeMillis();
        }
    }

    public void draw(Batch batch, float delta) {
        if (System.currentTimeMillis() - this.triggerTime < hideDelay) {
            batch.draw(padAnimation.getKeyFrame(getAnimationTime(), false), getX(), getY());
        }
    }

    @Override
    public void handleTrigger(boolean b, Actor a) {
        super.handleTrigger(b, a);
        getStage().addActor(new TeleportBeam(getAssetManager(), (float)getHitBoxCenter().getX(), (float)getHitBoxCenter().getY()));
    }
}
