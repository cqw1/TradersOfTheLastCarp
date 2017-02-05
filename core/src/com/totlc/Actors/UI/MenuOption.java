package com.totlc.Actors.UI;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.Actor;

public abstract class MenuOption extends Actor {

    public String asset;
    private AssetManager assetManager;

    public MenuOption(AssetManager assetManager, String asset) {
        this.asset = asset;
        this.assetManager = assetManager;
    }

    @Override
    public abstract void draw(Batch batch, float alpha);

    public abstract void execute();
}
