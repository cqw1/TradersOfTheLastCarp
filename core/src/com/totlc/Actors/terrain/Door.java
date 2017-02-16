package com.totlc.Actors.terrain;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Rectangle;
import com.totlc.AssetList;
import com.totlc.Directionality;

public class Door extends AWall {
    public Door(AssetManager assetManager, Rectangle r) {
        super(assetManager, r);
    }

    public Door(AssetManager assetManager, Rectangle r, Directionality d) {
        super(assetManager, r, d);
    }

    @Override
    public void draw(Batch batch, float alpha) {
        batch.draw(getAssetManager().get(AssetList.WALL_RIGHT.toString(), Texture.class), getX(), getY(), getWidth(), getHeight());
    }
}
