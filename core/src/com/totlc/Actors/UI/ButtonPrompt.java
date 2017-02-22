package com.totlc.Actors.UI;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.Actor;

public abstract class ButtonPrompt extends Actor {
    public String asset;
    private AssetManager assetManager;

    public ButtonPrompt(AssetManager assetManager, String asset, float x, float y) {
        this.asset = asset;
        this.assetManager = assetManager;
        setX(x);
        setY(y);
    }

    @Override
    public abstract void draw(Batch batch, float alpha);

    public abstract void update();
}
