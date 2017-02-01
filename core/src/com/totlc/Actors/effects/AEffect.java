package com.totlc.Actors.effects;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.math.Rectangle;
import com.totlc.Actors.TotlcObject;

import java.awt.geom.Point2D;

public abstract class AEffect extends TotlcObject {

    private Point2D textureDimensions;

    public AEffect(AssetManager assetManager, Rectangle r) {
        super(assetManager, r);
    }

    public Point2D getTextureDimensions() {
        return textureDimensions;
    }

    public void setTextureDimensions(Point2D textureDimensions) {
        this.textureDimensions = textureDimensions;
    }
}
