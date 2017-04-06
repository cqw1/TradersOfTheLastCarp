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

        moveInfo = new MoveInfo(assetManager, font, 1.2f, CONFIG_WIDTH / 4, CONFIG_HEIGHT * 7/8);
        whipInfo = new WhipInfo(assetManager, font, 1.2f, CONFIG_WIDTH / 2, CONFIG_HEIGHT * 7/8);
        objectiveInfo = new ObjectiveInfo(assetManager, font, 1.2f, CONFIG_WIDTH * 3/4, CONFIG_HEIGHT * 7/8);
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
    private TextureAtlas arrowKeys;
    private int arrowKeyWidth;
    private int arrowKeyHeight;
    private float arrowKeyScale = 0.5f;

    private int verticalPadding = 130;
    private int horizontalPadding = 5;
    private float fontScale;
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
    }

    public void draw(Batch batch) {
        batch.draw(
                arrowKeys.findRegion("key_left"),
                x,
                y - (arrowKeys.getRegions().get(0).originalHeight),
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
                x + 1 + arrowKeyWidth * arrowKeyScale,
                y - (arrowKeys.getRegions().get(0).originalHeight  + 10) + arrowKeyHeight * arrowKeyScale,
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
                x + arrowKeyWidth * arrowKeyScale,
                y - (arrowKeys.getRegions().get(0).originalHeight),
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
                x + (2 * arrowKeyWidth * arrowKeyScale),
                y - (arrowKeys.getRegions().get(0).originalHeight),
                0,
                0,
                arrowKeyWidth,
                arrowKeyHeight,
                arrowKeyScale,
                arrowKeyScale,
                0
        );

        font.getData().setScale(fontScale);
        font.draw(batch, "MOVE", x - 2 + (arrowKeyWidth) * arrowKeyScale + horizontalPadding, y - verticalPadding);

    }

}

class WhipInfo {
    private BitmapFont font;
    private TextureAtlas arrowKeys;
    private int arrowKeyWidth;
    private int arrowKeyHeight;
    private float arrowKeyScale = 0.5f;

    private int verticalPadding = 130;
    private int horizontalPadding = 5;
    private float fontScale;
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
    }

    public void draw(Batch batch) {
        batch.draw(
                arrowKeys.findRegion("space"),
                x + 50,
                y - (arrowKeys.getRegions().get(0).originalHeight),
                0,
                0,
                arrowKeyWidth,
                arrowKeyHeight,
                arrowKeyScale,
                arrowKeyScale,
                0
        );

        font.getData().setScale(fontScale);
        font.draw(batch, "WHIP", x + 50 + horizontalPadding, y - verticalPadding);

    }

}

class ObjectiveInfo {
    private BitmapFont font;
    private Texture arrow;
    private float arrowScale = 0.5f;
    private int verticalPadding = 130;

    private float fontScale;
    private float x;
    private float y;

    public ObjectiveInfo(AssetManager assetManager, BitmapFont font, float fontScale, float x, float y) {
        this.font = font;
        this.fontScale = fontScale;
        this.x = x;
        this.y = y;

        arrow = assetManager.get(AssetList.ARROW.toString(), Texture.class);
    }

    public void draw(Batch batch) {
        batch.draw(
                arrow,
                x + arrow.getWidth() / 4,
                y - arrow.getHeight() / 2,
                0,
                0,
                arrow.getWidth(),
                arrow.getHeight(),
                arrowScale,
                arrowScale,
                0,
                0,
                0,
                arrow.getWidth(),
                arrow.getHeight(),
                false,
                false
        );

        font.getData().setScale(fontScale);
        font.draw(batch, "CHECK LEVEL OBJECTIVE", x, y - verticalPadding);

    }

}
