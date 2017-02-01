package com.totlc.Actors.terrain;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.totlc.Actors.Character;
import com.totlc.Actors.TotlcObject;
import com.totlc.AssetList;
import com.totlc.TradersOfTheLastCarp;

public class LeftWall extends AWall {

    public LeftWall(AssetManager assetManager, Rectangle r) {
        super(assetManager, r);
    }

    @Override
    public void draw(Batch batch, float alpha) {
        batch.draw((Texture) getAssetManager().get(AssetList.WALL_LEFT.toString()), getX(), getY(), getWidth(), getHeight());
    }

    @Override
    public boolean collidesWith(Actor otherActor) {
        if (otherActor instanceof Character) {
            Character other = (Character) otherActor;

            other.moveRel((getX() + getWidth()) - other.getHitBoxX(), 0);
        }

        return false;
    }

    @Override
    public void endCollidesWith(Actor otherActor) {

    }
}
