package com.totlc.Actors.UI;

import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.*;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.totlc.AssetList;
import com.totlc.levels.ALevel;
import com.totlc.levels.ObjectiveVerifier.*;

/**
 * UI Actor that displays level #, objectives, and objective information.
 */
public class LevelInfo extends Actor {

    // Texture information.
    private NinePatch bar;
    private TextureRegion objIcon;
    private BitmapFont font;

    private int iconSize = 64;

    // Level to display information for.
    private ALevel level;

    private objectives objective;

    public LevelInfo(ALevel level, int x, int y){
        setX(x);
        setY(y);
        setWidth(200);
        setHeight(64);
        setLevel(level);
        bar = new NinePatch(new Texture(AssetList.UI_BAR.toString()), 16, 16, 8, 8);
        setObjective(level.getObjective());
        TextureAtlas atlas = new TextureAtlas(AssetList.ICON_PACK.toString());
        switch (getObjective().getID()){
            case 0:
                // Survive.
                objIcon = atlas.findRegion("time_icon");
                break;
            case 1:
                // Unlock.
                objIcon = atlas.findRegion("lock_icon");
                break;
            case 2:
                // Destroy.
                objIcon = atlas.findRegion("skull_icon");
                break;
        }
        font = new BitmapFont(new FileHandle(AssetList.LOVELO_FONT.toString()),
                new FileHandle(AssetList.LOVELO_IMAGE.toString()), false);
    }

    @Override
    public void draw(Batch batch, float alpha){
        // Draw base texture.
        bar.draw(batch, getX(), getY(), getWidth(), getHeight());
        // TODO: Draw level strings.
        font.getData().setScale(1.0f);
        font.draw(batch, level.getNameString(), getX(), getY() + getHeight()  * 0.8f);

        int objPadding = 20;
        // Draw Objective Icon.
        batch.draw(objIcon, getX(), getY() - objPadding, iconSize, iconSize);

        // TODO: Draw objective information.
        font.getData().setScale(0.8f);
        font.draw(batch, level.getObjectiveInfo(), getX() + iconSize - objPadding / 2, getY() + objPadding);
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
