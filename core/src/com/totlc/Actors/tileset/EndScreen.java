package com.totlc.Actors.tileset;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.totlc.Actors.carps.CrystalCarp;
import com.totlc.AssetList;
import com.totlc.TradersOfTheLastCarp;

import static com.totlc.TradersOfTheLastCarp.CONFIG_HEIGHT;
import static com.totlc.TradersOfTheLastCarp.CONFIG_WIDTH;

public class EndScreen extends Image {
    AssetManager assetManager;

    public EndScreen(AssetManager assetManager) {
        this.assetManager = assetManager;
        this.setZIndex(0);

    }

    public void draw(Batch batch, float delta) {
        batch.draw((Texture) assetManager.get(AssetList.END_CREDITS.toString()), 0f, 0f, (float) CONFIG_WIDTH, (float) TradersOfTheLastCarp.CONFIG_HEIGHT);
    }
}
