package com.totlc.Actors.tileset;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.totlc.AssetList;
import com.totlc.TradersOfTheLastCarp;

public class BasicTileSet extends Image {

    AssetManager assetManager;

    public BasicTileSet(AssetManager assetManager) {
        this.assetManager = assetManager;
        assetManager.load(AssetList.DEFAULT_TILESET.toString(), Texture.class);
    }

    public void draw(Batch batch, float delta) {
        if (assetManager.update()) {
            batch.draw((Texture) assetManager.get(AssetList.DEFAULT_TILESET.toString()), 0f, 0f, (float) TradersOfTheLastCarp.CONFIG_WIDTH, (float) TradersOfTheLastCarp.CONFIG_HEIGHT);
        }
    }
}
