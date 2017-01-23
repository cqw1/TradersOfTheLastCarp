package com.totlc.Actors.traps;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.totlc.Actors.projectiles.ProjEnum;
import com.totlc.Actors.projectiles.ProjectileFactory;
import com.totlc.AssetList;

import java.awt.geom.Point2D;

public class ArrowTrap extends ATrap{

    // Asset and animation constants.
    private TextureAtlas trapTextureAtlas;
    private Animation<TextureRegion> trapAnimation;

    private long startTime;

    public ArrowTrap(AssetManager assetManager, float x, float y) {
        super(assetManager, x, y);

        setHeight(192);
        setWidth(124);
        initHitBox();

        assetManager.load(AssetList.ARROW_TRAP.toString(), TextureAtlas.class);
        setDelay(0.5);
    }

    public void activate() {
        Point2D center = getCenter();
        setActive(true);
        startTime = System.currentTimeMillis();
        //Left/right-wards arrow
        getStage().addActor(ProjectileFactory.createProjectile(ProjEnum.ARROW, new Point2D.Double(-500, 0), getAssetManager(), (float) center.getX(), (float) center.getY(), 0));
        getStage().addActor(ProjectileFactory.createProjectile(ProjEnum.ARROW, new Point2D.Double(500, 0), getAssetManager(), (float) center.getX(), (float) center.getY(), 0));

        //Up/down arrow
        getStage().addActor(ProjectileFactory.createProjectile(ProjEnum.ARROW, new Point2D.Double(0, 500), getAssetManager(), (float) center.getX(), (float) center.getY(), 0));
        getStage().addActor(ProjectileFactory.createProjectile(ProjEnum.ARROW, new Point2D.Double(0, -500), getAssetManager(), (float) center.getX(), (float) center.getY(), 0));
    }

    @Override
    public void act(float deltaTime){
        increaseAnimationTime(deltaTime);
        if(isActive() && System.currentTimeMillis() - startTime > 1000){
            setActive(false);
        }
    }

    @Override
    public void draw(Batch batch, float alpha) {
        AssetManager assetManager = getAssetManager();
        if (assetManager.update() && !assetsLoaded()) {
            // Done loading. Instantiate all assets
            setAssetsLoaded(true);

            trapTextureAtlas = assetManager.get(AssetList.ARROW_TRAP.toString());
            trapAnimation = new Animation<TextureRegion>(1 / 12f, trapTextureAtlas.getRegions());
        }

        if (assetsLoaded()) {
            if(isActive()){
                batch.draw(trapAnimation.getKeyFrame(getAnimationTime(), false), getX(), getY());
            } else{
                batch.draw(trapTextureAtlas.getRegions().first(), getX(), getY());
            }
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
