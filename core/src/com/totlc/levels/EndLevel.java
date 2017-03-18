package com.totlc.levels;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.totlc.Actors.Player;
import com.totlc.Actors.UI.ButtonPrompt;
import com.totlc.Actors.tileset.EndScreen;
import com.totlc.AssetList;
import com.totlc.Directionality;
import com.totlc.TradersOfTheLastCarp;

public class EndLevel extends ALevel {

    private ButtonPrompt cursor;
    private float cursorScale = 0.5f;

    public EndLevel(AssetManager assetManager) {
        super(assetManager);
    }

    public void initOtherLevelStuff() {
        TradersOfTheLastCarp.musicPlayer.stop();
        TradersOfTheLastCarp.musicPlayer.setSong("test5");
        TradersOfTheLastCarp.musicPlayer.getCurrentSong().setLooping(true);
        TradersOfTheLastCarp.musicPlayer.play();

        addActor(new EndScreen(getAssetManager()));

        //Button Prompt
        cursor = new ButtonPrompt(getAssetManager(), AssetList.BUTTON_PROMPT_SPACE.toString(), TradersOfTheLastCarp.CONFIG_WIDTH - 300 * cursorScale - 50, 50) {
            private float baseY = getY();

            @Override
            public void draw(Batch batch, float alpha) {
                batch.draw(getAssetManager().get(this.asset, Texture.class), getX(), getY(), 300 * cursorScale, 120 * cursorScale);
            }

            @Override
            public void update() {}
        };

        cursor.update();
        addActor(cursor);
    }

    public void act(float deltaTime) {}

    @Override
    public boolean keyDown(int keycode) {
        if (keycode == Input.Keys.SPACE) {
            ALevel nextLevelObject = LevelFactory.createLevel(TitleScreen.class, getAssetManager());
            loadLevel(nextLevelObject);
        }

        return false;
    }

}
