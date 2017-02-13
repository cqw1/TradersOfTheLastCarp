package com.totlc.Actors.traps;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.totlc.Actors.damage.DamageEnum;
import com.totlc.Actors.damage.DamageFactory;
import com.totlc.AssetList;

import java.awt.geom.Point2D;

public class ArrowTrap extends ATrap{

    // Asset and animation constants.
    private TextureAtlas trapTextureAtlas;
    private Animation<TextureRegion> trapAnimation;
    private static long delay = 650; // in millis
    private long displayEyebrows = 2000;
    private static float width = 124;
    private static float height = 192;

    public ArrowTrap(AssetManager assetManager, float x, float y) {
        this(assetManager, new Rectangle(x, y, width, height), delay);
    }

    public ArrowTrap(AssetManager assetManager, Rectangle r, long delay) {
        super(assetManager, r, delay);

        trapTextureAtlas = assetManager.get(AssetList.ARROW_TRAP.toString());
        trapAnimation = new Animation<TextureRegion>(1 / 12f, trapTextureAtlas.getRegions());
    }

    @Override
    public void activate() {
        Point2D center = getCenter();
        //Left/right-wards arrow
        getStage().addActor(DamageFactory.createDamage(DamageEnum.ARROW, new Point2D.Double(-500, 0), getAssetManager(), (float) getHitBoxCenter().getX(), (float) getHitBoxCenter().getY(), 0));
        getStage().addActor(DamageFactory.createDamage(DamageEnum.ARROW, new Point2D.Double(500, 0), getAssetManager(), (float) getHitBoxCenter().getX(), (float) getHitBoxCenter().getY(), 0));

        //Up/down arrow
        getStage().addActor(DamageFactory.createDamage(DamageEnum.ARROW, new Point2D.Double(0, 500), getAssetManager(), (float) getHitBoxCenter().getX(), (float) getHitBoxCenter().getY(), 0));
        getStage().addActor(DamageFactory.createDamage(DamageEnum.ARROW, new Point2D.Double(0, -500), getAssetManager(), (float) getHitBoxCenter().getX(), (float) getHitBoxCenter().getY(), 0));
    }

    @Override
    public void act(float deltaTime){
        super.act(deltaTime);
        increaseAnimationTime(deltaTime);
    }

    @Override
    public void draw(Batch batch, float alpha) {
        if (isActive() && System.currentTimeMillis() < (getStartTime() + getDelay() + displayEyebrows)) {
            batch.draw(trapTextureAtlas.getRegions().get(1), getX(), getY());
        } else {
            batch.draw(trapTextureAtlas.getRegions().get(0), getX(), getY());
        }
    }

    @Override
    public boolean collidesWith(Actor otherActor) {
        return false;
    }

    @Override
    public void endCollidesWith(Actor otherActor) {

    }
}
