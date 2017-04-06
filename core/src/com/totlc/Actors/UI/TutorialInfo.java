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

    private BitmapFont font;
    private AssetManager assetManager;
    private TextureAtlas arrowKeys;
    private Texture arrow;
    private float arrowKeyScale = 0.5f;
    private float arrowScale = 0.5f;
    private int arrowKeyWidth;
    private int arrowKeyHeight;

    private int verticalPadding = 20;
    private int horizontalPadding = 5;

    public TutorialInfo(AssetManager assetManager) {
        font = TradersOfTheLastCarp.systemFont0;
        font.getRegion().getTexture().setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
        this.assetManager = assetManager;

        arrow = assetManager.get(AssetList.ARROW.toString(), Texture.class);
        arrowKeys = assetManager.get(AssetList.ARROW_KEYS.toString(), TextureAtlas.class);
        arrowKeyWidth = arrowKeys.getRegions().get(0).originalWidth;
        arrowKeyHeight = arrowKeys.getRegions().get(0).originalHeight;
    }

    @Override
    public void draw(Batch batch, float alpha) {
        font.getData().setScale(1.2f);
        font.draw(batch, "MOVE", CONFIG_WIDTH / 4 + (arrowKeyWidth) * arrowKeyScale + horizontalPadding, CONFIG_HEIGHT * 3/4 - verticalPadding);

        batch.draw(
                arrowKeys.findRegion("key_left"),
                CONFIG_WIDTH / 4,
                CONFIG_HEIGHT * 7/8 - (arrowKeys.getRegions().get(0).originalHeight),
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
                CONFIG_WIDTH / 4 + 1 + arrowKeyWidth * arrowKeyScale,
                CONFIG_HEIGHT * 7/8 - (arrowKeys.getRegions().get(0).originalHeight  + 10) + arrowKeyHeight * arrowKeyScale,
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
                CONFIG_WIDTH / 4 + arrowKeyWidth * arrowKeyScale,
                CONFIG_HEIGHT * 7/8 - (arrowKeys.getRegions().get(0).originalHeight),
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
                CONFIG_WIDTH / 4 + (2 * arrowKeyWidth * arrowKeyScale),
                CONFIG_HEIGHT * 7/8 - (arrowKeys.getRegions().get(0).originalHeight),
                0,
                0,
                arrowKeyWidth,
                arrowKeyHeight,
                arrowKeyScale,
                arrowKeyScale,
                0
        );

        batch.draw(
                arrowKeys.findRegion("space"),
                CONFIG_WIDTH / 2 + 50,
                CONFIG_HEIGHT * 7/8  - (arrowKeys.getRegions().get(0).originalHeight),
                0,
                0,
                arrowKeyWidth,
                arrowKeyHeight,
                arrowKeyScale,
                arrowKeyScale,
                0
        );
        font.getData().setScale(1.2f);
        font.draw(batch, "WHIP", CONFIG_WIDTH / 2 + 50 + horizontalPadding, CONFIG_HEIGHT * 3/4 - verticalPadding);

        batch.draw(
                arrow,
                CONFIG_WIDTH * 3/4 + arrow.getWidth() / 4,
                CONFIG_HEIGHT * 7/8 - arrow.getHeight() / 2,
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

        font.getData().setScale(1.2f);
        font.draw(batch, "CHECK LEVEL OBJECTIVE", CONFIG_WIDTH * 3/4, CONFIG_HEIGHT * 3/4 - verticalPadding);
    }
}

