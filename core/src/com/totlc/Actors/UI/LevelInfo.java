package com.totlc.Actors.UI;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.*;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.totlc.AssetList;
import com.totlc.TradersOfTheLastCarp;
import com.totlc.levels.ALevel;
import com.totlc.levels.ObjectiveVerifier.*;

/**
 * UI Actor that displays level #, objectives, and objective information.
 */
public class LevelInfo extends Actor {

    // Texture information.
    private NinePatch bar;

    private BitmapFont font;

    private int iconSize = 48;

    // Level to display information for.
    private ALevel level;

    private objectives objective;

    public LevelInfo(AssetManager assetManager, ALevel level, int x, int y){
        setX(x);
        setY(y);
        setWidth(200);
        setHeight(64);
        setLevel(level);
        bar = new NinePatch(assetManager.get(AssetList.UI_BAR.toString(), Texture.class), 16, 16, 8, 8);
        setObjective(level.getObjective());
        font = TradersOfTheLastCarp.systemFont0;
        font.getRegion().getTexture().setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
    }

    @Override
    public void draw(Batch batch, float alpha){
        int objPadding = 20;
        // Draw base texture.
        bar.draw(batch, getX(), getY(), getWidth(), getHeight());
        // Draw level strings.
        font.getData().setScale(1.2f);
        font.draw(batch, level.getNameString(), getX() + iconSize * 0.36f + objPadding, getY() + getHeight() * 0.75f);
        font.getData().setScale(0.8f);
        font.draw(batch, level.getInfoString() + " " + level.getObjectiveInfo(), getX() + iconSize * 0.42f + objPadding, getY() + getHeight() * 0.42f);

        // Draw Objective Icon.
        batch.draw(level.getObjIcon(), getX()+ iconSize * 0.2f - objPadding, getY() + iconSize * 0.2f - objPadding, iconSize, iconSize);
    }

    public ALevel getLevel() {
        return level;
    }

    public void setLevel(ALevel level) {
        this.level = level;
    }

    public objectives getObjective() {
        return objective;
    }

    public void setObjective(objectives objective) {
        this.objective = objective;
    }
}
