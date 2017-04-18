package com.totlc.levels;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.ParticleEffect;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.totlc.Actors.UI.ButtonPrompt;
import com.totlc.Actors.carps.CrystalCarp;
import com.totlc.Actors.effects.AEffect;
import com.totlc.Actors.effects.Confetti;
import com.totlc.Actors.tileset.EndScreen;
import com.totlc.AssetList;
import com.totlc.TradersOfTheLastCarp;

import static com.totlc.TradersOfTheLastCarp.CONFIG_HEIGHT;
import static com.totlc.TradersOfTheLastCarp.CONFIG_WIDTH;

public class EndLevel extends ALevel {

    private ButtonPrompt cursor;
    private float cursorScale = 0.5f;

    private CrystalCarp carp;
    private AEffect confetti;

    public EndLevel(AssetManager assetManager) {
        super(assetManager);

        carp = new CrystalCarp(
                assetManager,
                CONFIG_WIDTH / 2 - CrystalCarp.WIDTH / 2,
                CONFIG_HEIGHT / 4 - CrystalCarp.HEIGHT / 2,
                1,
                0.5f,
                1f
        );
        carp.setVoice(AssetList.FINE.toString());
        confetti = new Confetti(assetManager, getPlayer());
    }

    public void initOtherLevelStuff() {
        TradersOfTheLastCarp.musicPlayer.stop();
        TradersOfTheLastCarp.musicPlayer.setSong("victorydrums");
        TradersOfTheLastCarp.musicPlayer.getCurrentSong().setLooping(true);
        TradersOfTheLastCarp.musicPlayer.play();

        addActor(new EndScreen(getAssetManager()));
        addActor(carp);
        addActor(confetti);

        //Button Prompt
        cursor = new ButtonPrompt(getAssetManager(), AssetList.BUTTON_PROMPT_SPACE.toString(), CONFIG_WIDTH - 300 * cursorScale - 50, 50) {
            private float baseY = getY();

            @Override
            public void draw(Batch batch, float alpha) {
                if (System.currentTimeMillis() % 1000 <= 200) {
                    return;
                }
                batch.draw(getAsset(), getX(), getY(), 300 * cursorScale, 120 * cursorScale);
            }

            @Override
            public void update() {}
        };

        cursor.update();
        addActor(cursor);
    }

    @Override
    public void act(float delta) {
        // Let the actors update themselves
        for (Actor a : getActors()) {
            a.act(delta);
        }
    }

    @Override
    public boolean keyDown(int keycode) {
        if (keycode == Input.Keys.SPACE) {
            ALevel nextLevelObject = LevelFactory.createLevel(TitleScreen.class, getAssetManager());
            loadLevel(nextLevelObject);
        }

        return false;
    }

}
