package com.totlc.Actors.UI;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.totlc.AssetList;
import com.totlc.TradersOfTheLastCarp;

import static com.totlc.TradersOfTheLastCarp.CONFIG_HEIGHT;
import static com.totlc.TradersOfTheLastCarp.CONFIG_WIDTH;

public class TutorialInfo extends Actor {

    private BitmapFont font = TradersOfTheLastCarp.systemFont0;
    private MoveInfo moveInfo;
    private WhipInfo whipInfo;
    private ObjectiveInfo objectiveInfo;

    public TutorialInfo(AssetManager assetManager) {
        font.getRegion().getTexture().setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);

        moveInfo = new MoveInfo(assetManager, font, 1.2f, CONFIG_WIDTH * 0.35f, CONFIG_HEIGHT * 0.75f);
        whipInfo = new WhipInfo(assetManager, font, 1.2f, CONFIG_WIDTH * 0.6f, CONFIG_HEIGHT * 0.75f);
        objectiveInfo = new ObjectiveInfo(assetManager, font, 1.2f, CONFIG_WIDTH * 0.85f, CONFIG_HEIGHT * 0.75f);
    }

    @Override
    public void draw(Batch batch, float alpha) {
        moveInfo.draw(batch);
        whipInfo.draw(batch);
        objectiveInfo.draw(batch);
    }
}

class MoveInfo {
    private BitmapFont font;
    private float fontScale;

    private TextureAtlas arrowKeys;
    private int arrowKeyWidth;
    private int arrowKeyHeight;
    private float arrowKeyWidthScaled;
    private float arrowKeyHeightScaled;
    private float arrowKeyScale = 0.5f;

    // Padding for the text so it centers below the icons.
    private int verticalPadding = 100;
    private int horizontalPadding = 27;

    // Draw the middle of the arrow keys at x, y
    private float x;
    private float y;

    public MoveInfo(AssetManager assetManager, BitmapFont font, float fontScale, float x, float y) {
        this.font = font;
        this.fontScale = fontScale;
        this.x = x;
        this.y = y;

        arrowKeys = assetManager.get(AssetList.ARROW_KEYS.toString(), TextureAtlas.class);
        arrowKeyWidth = arrowKeys.getRegions().get(0).originalWidth;
        arrowKeyHeight = arrowKeys.getRegions().get(0).originalHeight;
        arrowKeyWidthScaled = arrowKeys.getRegions().get(0).originalWidth * arrowKeyScale;
        arrowKeyHeightScaled = arrowKeys.getRegions().get(0).originalHeight * arrowKeyScale;
    }

    public void draw(Batch batch) {
        batch.draw(
                arrowKeys.findRegion("key_left"),
                x - (1.5f * arrowKeyWidthScaled),
                y - arrowKeyHeightScaled,
                0,
                0,
                arrowKeyWidth,
                arrowKeyHeight,
                arrowKeyScale,
                arrowKeyScale,
                0
        );

        batch.draw(
                arrowKeys.findRegion("key_up"),
                x + 1 - (0.5f  * arrowKeyWidthScaled),
                y - 5,
                0,
                0,
                arrowKeyWidth,
                arrowKeyHeight,
                arrowKeyScale,
                arrowKeyScale,
                0
        );

        batch.draw(
                arrowKeys.findRegion("key_bottom"),
                x - (0.5f * arrowKeyWidthScaled),
                y - arrowKeyHeightScaled,
                0,
                0,
                arrowKeyWidth,
                arrowKeyHeight,
                arrowKeyScale,
                arrowKeyScale,
                0
        );

        batch.draw(
                arrowKeys.findRegion("key_right"),
                x + (0.5f * arrowKeyWidthScaled),
                y - arrowKeyHeightScaled,
                0,
                0,
                arrowKeyWidth,
                arrowKeyHeight,
                arrowKeyScale,
                arrowKeyScale,
                0
        );

        font.getData().setScale(fontScale);
        font.draw(batch, "MOVE", x - horizontalPadding, y - verticalPadding);

    }

}

class WhipInfo {
    private BitmapFont font;
    private float fontScale;

    private TextureAtlas arrowKeys;
    private int arrowKeyWidth;
    private int arrowKeyHeight;
    private float arrowKeyWidthScaled;
    private float arrowKeyHeightScaled;
    private float arrowKeyScale = 0.5f;

    // Padding for the text so it centers below the icons.
    private int verticalPadding = 100;
    private int horizontalPadding = 22;

    // Draw the middle of the icon at x, y
    private float x;
    private float y;

    public WhipInfo(AssetManager assetManager, BitmapFont font, float fontScale, float x, float y) {
        this.font = font;
        this.fontScale = fontScale;
        this.x = x;
        this.y = y;

        arrowKeys = assetManager.get(AssetList.ARROW_KEYS.toString(), TextureAtlas.class);
        arrowKeyWidth = arrowKeys.getRegions().get(0).originalWidth;
        arrowKeyHeight = arrowKeys.getRegions().get(0).originalHeight;
        arrowKeyWidthScaled = arrowKeys.getRegions().get(0).originalWidth * arrowKeyScale;
        arrowKeyHeightScaled = arrowKeys.getRegions().get(0).originalHeight * arrowKeyScale;
    }

    public void draw(Batch batch) {
        batch.draw(
                arrowKeys.findRegion("space"),
                x - (arrowKeyWidthScaled * 0.5f),
                y - (arrowKeyHeightScaled * 0.5f),
                0,
                0,
                arrowKeyWidth,
                arrowKeyHeight,
                arrowKeyScale,
                arrowKeyScale,
                0
        );

        font.getData().setScale(fontScale);
        font.draw(batch, "WHIP", x - horizontalPadding, y - verticalPadding);

    }

}

class ObjectiveInfo {
    private BitmapFont font;
    private float fontScale;

    private Texture arrow;
    private float arrowScale = 0.5f;
    private int arrowWidth;
    private int arrowHeight;
    private float arrowWidthScaled;
    private float arrowHeightScaled;

    // Padding for the text so it centers below the icons.
    private int verticalPadding = 100;
    private int horizontalPadding = 130;

    // Draw the middle of the icon at x, y
    private float x;
    private float y;

    public ObjectiveInfo(AssetManager assetManager, BitmapFont font, float fontScale, float x, float y) {
        this.font = font;
        this.fontScale = fontScale;
        this.x = x;
        this.y = y;

        arrow = assetManager.get(AssetList.ARROW.toString(), Texture.class);
        arrowWidth = arrow.getWidth();
        arrowHeight = arrow.getHeight();
        arrowWidthScaled = arrowWidth * arrowScale;
        arrowHeightScaled = arrowHeight * arrowScale;
    }

    public void draw(Batch batch) {
        batch.draw(
                arrow,
                x - (arrowWidthScaled * 0.5f),
                y - (arrowHeightScaled * 0.5f),
                0,
                0,
                arrowWidth,
                arrowHeight,
                arrowScale,
                arrowScale,
                0,
                0,
                0,
                arrowWidth,
                arrowHeight,
                false,
                false
        );

        font.getData().setScale(fontScale);
        font.draw(batch, "CHECK LEVEL OBJECTIVE", x - horizontalPadding, y - verticalPadding);

    }

}
