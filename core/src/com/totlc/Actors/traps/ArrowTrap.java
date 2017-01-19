package com.totlc.Actors.traps;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.totlc.Actors.projectiles.ProjEnum;
import com.totlc.Actors.projectiles.ProjectileFactory;
import com.totlc.AssetList;

import java.awt.geom.Point2D;

public class ArrowTrap extends ATrap{

    public ArrowTrap(AssetManager assetManager, float x, float y) {
        super(assetManager, x, y);

        setHeight(96);
        setWidth(64);
        setHitBox(new Rectangle(getX(), getY(), getWidth(), getHeight()));

        assetManager.load(AssetList.ARROW_TRAP.toString(), Texture.class);
        setTexture(new Texture(Gdx.files.internal(AssetList.ARROW_TRAP.toString())));

        setDelay(0.5);
    }

    public void activate() {
        Point2D center = getCenter();

        //Left/right-wards arrow
        getStage().addActor(ProjectileFactory.createProjectile(ProjEnum.ARROW, new Point2D.Double(-200, 0), getAssetManager(), (float) center.getX(), (float) center.getY()));
        getStage().addActor(ProjectileFactory.createProjectile(ProjEnum.ARROW, new Point2D.Double(200, 0), getAssetManager(), (float) center.getX(), (float) center.getY()));

        //Up/down arrow
        getStage().addActor(ProjectileFactory.createProjectile(ProjEnum.ARROW, new Point2D.Double(0, 200), getAssetManager(), (float) center.getX(), (float) center.getY()));
        getStage().addActor(ProjectileFactory.createProjectile(ProjEnum.ARROW, new Point2D.Double(0, -200), getAssetManager(), (float) center.getX(), (float) center.getY()));
    }

    @Override
    public void draw(Batch batch, float alpha) {
        if (getAssetManager().update()) {
            batch.draw(getTexture(), getX(), getY());
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
