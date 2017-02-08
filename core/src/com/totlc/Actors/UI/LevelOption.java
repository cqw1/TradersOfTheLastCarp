package com.totlc.Actors.UI;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.totlc.AssetList;

public abstract class LevelOption extends Actor {

    private AssetManager assetManager;
    private String unselectedAsset = AssetList.QUESTION_MARK_SELECT.toString();
    private String selectedAsset = AssetList.QUESTION_MARK_SELECT_BORDER.toString();
    private Class levelClass;
    private float x;
    private float y;
    private boolean isSelected = false;


    public LevelOption(AssetManager assetManager, String unselectedAsset, String selectedAsset, Class levelClass, float x, float y) {
        this.unselectedAsset = unselectedAsset;
        this.selectedAsset = selectedAsset;
        this.assetManager = assetManager;
        this.levelClass = levelClass;
        this.x = x;
        this.y = y;
    }

    @Override
    public void draw(Batch batch, float alpha) {
        if (isSelected) {
            batch.draw(assetManager.get(selectedAsset, Texture.class), x, y);
        } else {
            batch.draw(assetManager.get(unselectedAsset, Texture.class), x, y);
        }
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }

    public abstract void execute();
}
