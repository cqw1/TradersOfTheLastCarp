package com.totlc.Actors.UI;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.totlc.AssetList;
import com.totlc.TradersOfTheLastCarp;

public abstract class LevelOption extends Actor {

    private AssetManager assetManager;
    private String unselectedAsset = AssetList.QUESTION_MARK_SELECT.toString();
    private String selectedAsset = AssetList.QUESTION_MARK_SELECT_BORDER.toString();
    private Class levelClass;
    private String nameString;
    private float x;
    private float y;
    private boolean isSelected = false;
    private BitmapFont font;


    public LevelOption(AssetManager assetManager, String unselectedAsset, String selectedAsset, Class levelClass, String nameString, float x, float y) {
        this.unselectedAsset = unselectedAsset;
        this.selectedAsset = selectedAsset;
        this.assetManager = assetManager;
        this.levelClass = levelClass;
        this.nameString = nameString;
        this.x = x;
        this.y = y;

        font = TradersOfTheLastCarp.systemFont0;
        font.getRegion().getTexture().setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
    }

    @Override
    public void draw(Batch batch, float alpha) {
        String asset;
        if (isSelected) {
            asset = selectedAsset;
        } else {
            asset = unselectedAsset;
        }

//        batch.draw(assetManager.get(asset, Texture.class), x, y);
        batch.draw(
                assetManager.get(asset, Texture.class),
                x,
                y,
                0,
                0,
                (float)assetManager.get(asset, Texture.class).getWidth(),
                (float)assetManager.get(asset, Texture.class).getHeight(),
                0.5f,
                0.5f,
                0,
                0,
                0,
                assetManager.get(asset, Texture.class).getWidth(),
                assetManager.get(asset, Texture.class).getHeight(),
                false,
                false
        );

        font.getData().setScale(1.0f);
        font.draw(batch, nameString, x, y + 90);
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }

    public abstract void execute();
}
