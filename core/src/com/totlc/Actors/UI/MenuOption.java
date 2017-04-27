package com.totlc.Actors.UI;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.Actor;

public abstract class MenuOption extends Actor {
    public Texture asset;
    private AssetManager assetManager;

    public MenuOption(AssetManager assetManager, String asset) {
        this.asset = assetManager.get(asset, Texture.class);
        this.assetManager = assetManager;
        this.asset.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
    }

    @Override
    public abstract void draw(Batch batch, float alpha);

    public abstract void execute();

    public Texture getAsset() {
        return asset;
    }

    public void setAsset(Texture asset) {
        this.asset = asset;
    }
}
