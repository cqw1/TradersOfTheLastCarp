package com.totlc.Actors.terrain;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Polygon;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.totlc.Actors.Character;
import com.totlc.Actors.TotlcObject;
import com.totlc.Directionality;

import java.util.Arrays;

public class AWall extends TotlcObject {

    public Directionality priority;

    public AWall(AssetManager assetManager, Rectangle r) {
        super(assetManager, r);
        priority = Directionality.RIGHT;
    }

    public AWall(AssetManager assetManager, Rectangle r, Directionality d) {
        this(assetManager, r);
        priority = d;
    }

    public void draw(Batch batch, float alpha) {}


    public boolean collidesWith(Actor otherActor) {

        if (otherActor instanceof Character) {
            Character other = (Character) otherActor;
            Polygon charPosition = other.getHitBox();

            Vector2 topL = new Vector2(getX(), getY() + getHeight());
            Vector2 bottomL = new Vector2(getX(), getY());
            Vector2 topR = new Vector2(getX() + getWidth(), getY() + getHeight());
            Vector2 bottomR = new Vector2(getX() + getWidth(), getY());

            boolean hasMoved = false;
            if (priority == Directionality.RIGHT ||
                    priority == Directionality.LEFT) {
                hasMoved = checkLeftRight(topR, topL, bottomR, bottomL, charPosition, other);
                if (!hasMoved)
                    checkTopBot(topR, topL, bottomR, bottomL, charPosition, other);
            } else {
                hasMoved = checkTopBot(topR, topL, bottomR, bottomL, charPosition, other);
                if (!hasMoved)
                    checkLeftRight(topR, topL, bottomR, bottomL, charPosition, other);
            }
        }

        return false;
    }

    public boolean checkLeftRight(Vector2 topR, Vector2 topL, Vector2 bottomR, Vector2 bottomL,
                               Polygon charPosition, Character other) {
        if (Intersector.intersectSegmentPolygon(topR, bottomR, charPosition)) {
            // Check Right side
            other.moveRel((getX() + getWidth()) - other.getHitBoxX(), 0);
            return true;
        }

        if (Intersector.intersectSegmentPolygon(topL, bottomL, charPosition)) {
            // Check Left side
            other.moveRel(getX() - (other.getHitBoxX() + other.getHitBoxWidth()), 0);
            return true;
        }

        return false;
    }

    public boolean checkTopBot(Vector2 topR, Vector2 topL, Vector2 bottomR, Vector2 bottomL,
                               Polygon charPosition, Character other) {
        if (Intersector.intersectSegmentPolygon(topL, topR, charPosition)) {
            // Check Top side
            other.moveRel(0, (getY() + getHeight()) - other.getHitBoxY());
            return true;
        }

        if (Intersector.intersectSegmentPolygon(bottomL, bottomR, charPosition)) {
            // Check Bottom side
            other.moveRel(0, getY() - (other.getHitBoxY() + other.getHitBoxHeight()));
            return true;
        }

        return false;
    }

    @Override
    public void endCollidesWith(Actor otherActor) {

    }
}
