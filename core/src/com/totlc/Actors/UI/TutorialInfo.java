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
    private float arrowKeyScale = 0.5f;
    private int arrowKeyWidth;
    private int arrowKeyHeight;

    public TutorialInfo(AssetManager assetManager) {
        font = TradersOfTheLastCarp.systemFont0;
        font.getRegion().getTexture().setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
        this.assetManager = assetManager;

        arrowKeys = assetManager.get(AssetList.ARROW_KEYS.toString(), TextureAtlas.class);
        arrowKeyWidth = arrowKeys.getRegions().get(0).originalWidth;
        arrowKeyHeight = arrowKeys.getRegions().get(0).originalHeight;
    }

    @Override
    public void draw(Batch batch, float alpha) {
        font.getData().setScale(1.2f);
        font.draw(batch, "Move", CONFIG_WIDTH / 4, CONFIG_HEIGHT / 2);

        batch.draw(
                arrowKeys.findRegion("key_left"),
                CONFIG_WIDTH / 4 + 100,
                CONFIG_HEIGHT / 2 - (arrowKeys.getRegions().get(0).originalHeight / 4 + 10),
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
                CONFIG_WIDTH / 4 + 101 + arrowKeyWidth * arrowKeyScale,
                CONFIG_HEIGHT / 2 - (arrowKeys.getRegions().get(0).originalHeight / 4 + 20) + arrowKeyHeight * arrowKeyScale,
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
                CONFIG_WIDTH / 4 + 100 + arrowKeyWidth * arrowKeyScale,
                CONFIG_HEIGHT / 2 - (arrowKeys.getRegions().get(0).originalHeight / 4 + 10),
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
                CONFIG_WIDTH / 4 + 100 + (2 * arrowKeyWidth * arrowKeyScale),
                CONFIG_HEIGHT / 2 - (arrowKeys.getRegions().get(0).originalHeight / 4 + 10),
                0,
                0,
                arrowKeyWidth,
                arrowKeyHeight,
                arrowKeyScale,
                arrowKeyScale,
                0
        );

        font.getData().setScale(1.2f);
        font.draw(batch, "WHIP", CONFIG_WIDTH * 3 / 4, CONFIG_HEIGHT / 2);
        batch.draw(
                arrowKeys.findRegion("space"),
                CONFIG_WIDTH * 3 / 4 + 60,
                CONFIG_HEIGHT / 2 - (arrowKeys.getRegions().get(0).originalHeight / 4 + 10),
                0,
                0,
                arrowKeyWidth,
                arrowKeyHeight,
                arrowKeyScale,
                arrowKeyScale,
                0
        );
    }
}

