package com.totlc.Actors.UI;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;


public abstract class ButtonPrompt extends Actor {
    public AssetManager assetManager;
    public Texture asset;

    public TextureAtlas assetAtlas;

    public ButtonPrompt(AssetManager assetManager, String asset, float x, float y) {
        this.asset = assetManager.get(asset, Texture.class);
        this.assetAtlas = null;
        this.assetManager = assetManager;
        this.asset.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
        setX(x);
        setY(y);
    }

    public ButtonPrompt(AssetManager assetManager, TextureAtlas asset, float x, float y) {
        this.asset = null;
        this.assetAtlas = asset;
        this.assetManager = assetManager;
        setX(x);
        setY(y);
    }

    @Override
    public abstract void draw(Batch batch, float alpha);

    public abstract void update();

    public Texture getAsset() {
        return asset;
    }

    public void setAsset(TextureRegion asset) {
        this.asset = asset.getTexture();
    }

    public void setAsset(Texture asset) {
        this.asset = asset;
    }

    public TextureAtlas getAssetAtlas() {
        return assetAtlas;
    }

    public void setAssetAtlas(TextureAtlas assetAtlas) {
        this.assetAtlas = assetAtlas;
    }

    public AssetManager getAssetManager() {
        return assetManager;
    }

    public void setAssetManager(AssetManager assetManager) {
        this.assetManager = assetManager;
    }
}
