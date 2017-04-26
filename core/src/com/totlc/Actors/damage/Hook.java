package com.totlc.Actors.damage;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.totlc.Actors.Character;
import com.totlc.Actors.players.Player;
import com.totlc.AssetList;
import com.totlc.TradersOfTheLastCarp;

import java.awt.geom.Point2D;

public class Hook extends Damage {

    private ShapeRenderer lineRenderer = new ShapeRenderer();
    private Character origin, target;
    private float range, angle;
    private float reelSpeed = 1000;
    private boolean outgoing;

    private static float width = 74, height = 39;

    // Not generated with factory.
    public Hook(AssetManager assetManager, Character origin, float x, float y, int attack, int damageType, float range, Point2D initialVelocity) {
        super(assetManager, new Rectangle(x, y, width, height), attack, damageType);
        this.target = null;
        this.range = range;
        this.origin = origin;
        this.outgoing = true;

        setDamageType(damageType);
        setScaleFactor(1f);
        getHitBox().setOrigin(getX() + getWidth() / 2, getY() + getHeight() / 2);

        setTexture(assetManager.get(AssetList.FISHHOOK.toString(), Texture.class));
        setVel(initialVelocity);
        this.angle = getVelocityAngle();
        getHitBox().setScale(0.8f, 0.8f);
        getHitBox().rotate(getVelocityAngle());

        lineRenderer.setProjectionMatrix(TradersOfTheLastCarp.camera.combined);
    }

    @Override
    public void act(float delta) {
        moveUnit(delta);
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
                if (this.target.getStage() != null){
                    this.target.returnIntoBounds(formerX, formerY);
                }
            }
            if (new Point2D.Double(getHitBoxCenter().getX(), getHitBoxCenter().getY()).distance(this.origin.getCenter()) <= getWidth()) {
                remove();
            }
        }
        if (isOutOfBounds()) {
            remove();
        }
    }


    @Override
    public void draw(Batch batch, float alpha) {
        batch.end();
        lineRenderer.begin(ShapeRenderer.ShapeType.Line);
        lineRenderer.setColor(Color.BLACK);
        lineRenderer.line(getHitBox().getTransformedVertices()[6], getHitBox().getTransformedVertices()[7], (float) origin.getHitBoxCenter().getX(), (float) origin.getHitBoxCenter().getY());
        lineRenderer.end();
        batch.begin();
        batch.draw(getTexture(), getX(), getY(), getWidth() / 2, getHeight() / 2, getWidth(), getHeight(), getScaleFactor(), getScaleFactor(), angle, 0, 0, 74, 39, false, false);
    }

    @Override
    public boolean collidesWith(Actor otherActor) {
        if (otherActor instanceof Player) {
            this.target = (Character) otherActor;
            this.outgoing = false;
            this.target.moveRel((float)(getHitBoxCenter().getX() - target.getHitBoxCenter().getX()),
                    (float)(getHitBoxCenter().getY() - target.getHitBoxCenter().getY()));
        }
        return false;
    }

    @Override
    public void endCollidesWith(Actor otherActor) {

    }
}
