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

    private boolean open = false;

    public AWall(AssetManager assetManager, Rectangle r) {
        super(assetManager, r);
    }

    public void draw(Batch batch, float alpha) {}


    public boolean collidesWith(Actor otherActor) {
        return false;
    }

    @Override
    public void endCollidesWith(Actor otherActor) {

    }

    public boolean isOpen() {
        return open;
    }

    public void setOpen(boolean open) {
        this.open = open;
    }
}
