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
import org.omg.PortableInterceptor.SYSTEM_EXCEPTION;

import java.awt.geom.Point2D;

public class ArrowTrap extends ATrap{

    // Asset and animation constants.
    private TextureAtlas trapTextureAtlas;
    private Animation<TextureRegion> trapAnimation;
    private long delay; // in millis
    private long displayEyebrows = 2000;

    private long startTime;

    public ArrowTrap(AssetManager assetManager, float x, float y, long delay) {
        super(assetManager, new Rectangle(x, y, 124, 192), delay);

        this.delay = delay;

        trapTextureAtlas = assetManager.get(AssetList.ARROW_TRAP.toString());
        trapAnimation = new Animation<TextureRegion>(1 / 12f, trapTextureAtlas.getRegions());
    }

    @Override
    public void setup() {
        if (!isSetup()) {
            // If someone's already triggered this trap
            startTime = System.currentTimeMillis();
            setSetup(true);
        }
    }

    @Override
    public void activate() {
        Point2D center = getCenter();
        //Left/right-wards arrow
        getStage().addActor(ProjectileFactory.createProjectile(ProjEnum.ARROW, new Point2D.Double(-500, 0), getAssetManager(), (float) center.getX(), (float) center.getY(), 0));
        getStage().addActor(ProjectileFactory.createProjectile(ProjEnum.ARROW, new Point2D.Double(500, 0), getAssetManager(), (float) center.getX(), (float) center.getY(), 0));

        //Up/down arrow
        getStage().addActor(ProjectileFactory.createProjectile(ProjEnum.ARROW, new Point2D.Double(0, 500), getAssetManager(), (float) center.getX(), (float) center.getY(), 0));
        getStage().addActor(ProjectileFactory.createProjectile(ProjEnum.ARROW, new Point2D.Double(0, -500), getAssetManager(), (float) center.getX(), (float) center.getY(), 0));
    }

    @Override
    public void act(float deltaTime){
        if (isSetup() && !isActive()) {
            if (System.currentTimeMillis() < (startTime + delay)) {
                // Still waiting for delay.
                return;
            } else {
                setActive(true);
                activate();
            }
        }

        increaseAnimationTime(deltaTime);
        if (isActive() && System.currentTimeMillis() > (startTime + delay + displayEyebrows)) {
            // If the trap was active and we've already passed our delay and the allotted time for displaying eyebrows.
            setActive(false);
            setSetup(false);
        }
    }

    @Override
    public void draw(Batch batch, float alpha) {

        if (isActive()) {
//            batch.draw(trapAnimation.getKeyFrame(getAnimationTime(), false), getX(), getY());
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
