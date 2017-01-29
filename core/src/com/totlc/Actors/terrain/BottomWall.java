package com.totlc.Actors.terrain;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.totlc.Actors.Character;
import com.totlc.Actors.TotlcObject;

public class BottomWall extends AWall {

    public BottomWall(AssetManager assetManager, Rectangle r) {
        super(assetManager, r);
    }

    @Override
    public void draw(Batch batch, float alpha) {

    }

    @Override
    public boolean collidesWith(Actor otherActor) {
        if (otherActor instanceof Character) {
            Character other = (Character) otherActor;

            other.moveRel(0, (getY() + getHeight()) - other.getHitBoxY());
        }

        return false;
    }

    @Override
    public void endCollidesWith(Actor otherActor) {

    }
}
