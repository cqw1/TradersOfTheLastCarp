package com.totlc.Actors.tileset;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.totlc.AssetList;
import com.totlc.TradersOfTheLastCarp;

public class BasicTileSet extends Image {

    AssetManager assetManager;
    Texture asset;

    public BasicTileSet(AssetManager assetManager) {
        this.assetManager = assetManager;
        this.asset = assetManager.get(AssetList.DEFAULT_TILESET.toString(), Texture.class);
        this.asset.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
        this.setZIndex(0);
    }

    public void draw(Batch batch, float delta) {
        batch.draw(this.asset, 0f, 0f, (float) TradersOfTheLastCarp.CONFIG_WIDTH, (float) TradersOfTheLastCarp.CONFIG_HEIGHT);
    }
}
