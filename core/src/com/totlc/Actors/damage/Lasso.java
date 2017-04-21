package com.totlc.Actors.damage;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.totlc.Actors.Character;
import com.totlc.Actors.enemies.AEnemy;
import com.totlc.Actors.terrain.AWall;
import com.totlc.Actors.terrain.Rock;
import com.totlc.AssetList;
import com.totlc.TradersOfTheLastCarp;

import java.awt.geom.Point2D;

public class Lasso extends Damage{

    private ShapeRenderer lineRenderer = new ShapeRenderer();
    private TextureAtlas lassoAtlas;
    private Animation<TextureRegion> lassoAnimation;

    private Character origin, target;
    private float range, angle;
    private float reelSpeed = 800;
    private boolean outgoing;

    private long stunDuration = 1000;

    private static float width = 80, height = 35;

    // Not generated with factory.
    public Lasso(AssetManager assetManager, Character origin, float x, float y, int attack, int damageType, float range, Point2D initialVelocity) {
        super(assetManager, new Rectangle(x, y, width, height), attack, damageType);
        this.target = null;
        this.range = range;
        this.origin = origin;
        this.outgoing = true;

        setDamageType(damageType);
        setScaleFactor(1f);
        getHitBox().setOrigin(getX() + getWidth() / 2, getY() + getHeight() / 2);
        getHitBox().setScale(0.9f, 0.3f);

        lassoAtlas = assetManager.get(AssetList.LASSO.toString());
        lassoAnimation = new Animation<TextureRegion>(1 / 24f, lassoAtlas.getRegions());
        setVel(initialVelocity);
        this.angle = getVelocityAngle();
        getHitBox().rotate(getVelocityAngle());

        lineRenderer.setProjectionMatrix(TradersOfTheLastCarp.camera.combined);
    }

    @Override
    public void act(float delta) {
        moveUnit(delta);
        increaseAnimationTime(delta);
        if (this.outgoing) {
            if (new Point2D.Double(getX(), getY()).distance(this.origin.getCenter()) >= range){
                this.outgoing = false;
            }
        } else{
            Point2D targetVector = new Point2D.Double(origin.getX() - getX(), origin.getY() - getY());
            float n = (float) Math.sqrt(Math.pow(targetVector.getX(), 2) + Math.pow(targetVector.getY(), 2));
            if (n != 0) {
                targetVector.setLocation(targetVector.getX() / n * reelSpeed, targetVector.getY() / n * reelSpeed);
            } else{
                targetVector.setLocation(0, 0);
            }
            setVel(targetVector);
            if (this.target != null){
                float formerX = getX();
                float formerY = getY();
                this.target.moveRel((float)(getHitBoxCenter().getX() - target.getHitBoxCenter().getX()),
                        (float)(getHitBoxCenter().getY() - target.getHitBoxCenter().getY()));
                if (new Point2D.Double(getHitBoxCenter().getX(),
                        getHitBoxCenter().getY()).distance(this.origin.getHitBoxCenter()) <= Math.max(target.getHitBoxWidth() / 2, target.getHitBoxHeight() / 2) + origin.getHitBoxHeight() / 2 + 20) {
                    remove();
                }
//                this.target.returnIntoBounds(formerX, formerY);
            } else if (new Point2D.Double(getHitBoxCenter().getX(), getHitBoxCenter().getY()).distance(this.origin.getCenter()) <= getWidth()) {
                remove();
            }
        }
    }


    @Override
    public void draw(Batch batch, float alpha) {
        batch.end();
        lineRenderer.begin(ShapeRenderer.ShapeType.Line);
        lineRenderer.setColor(Color.BLACK);
        lineRenderer.line(getHitBox().getTransformedVertices()[6], getHitBox().getTransformedVertices()[7], (float) origin.getHitBoxCenter().getX() - 8, (float) origin.getHitBoxCenter().getY() - 28);
        lineRenderer.end();
        batch.begin();
        if (this.target == null){
            batch.draw(lassoAnimation.getKeyFrame(getAnimationTime(), true), getX(), getY(), getWidth() / 2, getHeight() / 2, getWidth(), getHeight(), getScaleFactor(), getScaleFactor(), angle);
        } else {
            batch.draw(lassoAnimation.getKeyFrame(0, false), getX(), getY(), getWidth() / 2, getHeight() / 2, getWidth(), getHeight(), getScaleFactor(), getScaleFactor(), angle);
        }
    }

    @Override
    public boolean collidesWith(Actor otherActor) {
        if (otherActor instanceof AEnemy) {
            this.target = (AEnemy) otherActor;
            this.outgoing = false;
            this.target.moveRel((float)(getHitBoxCenter().getX() - target.getHitBoxCenter().getX()),
                    (float)(getHitBoxCenter().getY() - target.getHitBoxCenter().getY()));
        } else if (otherActor instanceof AWall) {
            if (!(otherActor instanceof Rock)){
                this.outgoing = false;
            }
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
