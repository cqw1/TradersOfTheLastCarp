package com.totlc.levels;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.totlc.Actors.Player;
import com.totlc.Actors.TotlcObject;
import com.totlc.Actors.UI.ButtonPrompt;
import com.totlc.Actors.UI.MenuOption;
import com.totlc.AssetList;
import com.totlc.TradersOfTheLastCarp;

import java.awt.geom.Point2D;
import java.util.ArrayList;

public class TitleScreen extends ALevel {

    private final TotlcObject titleScreen;
    private ButtonPrompt cursor;
    private float cursorScale = 0.5f;
    private int optionFocusIndex = 0;
    private ArrayList<MenuOption> menuOptions = new ArrayList<MenuOption>();
    private Point2D.Float optionsSize = new Point2D.Float(330f, 180f);
    private Point2D.Float optionsStart = new Point2D.Float((float) (TradersOfTheLastCarp.CONFIG_WIDTH * 0.8 - optionsSize.getX() / 2 + 10), (float) (TradersOfTheLastCarp.CONFIG_HEIGHT / 2 - optionsSize.getY() - 165f));

    public TitleScreen(AssetManager assetManager) {
        super(assetManager);
        try {
            TradersOfTheLastCarp.player = (Player) TradersOfTheLastCarp.playerClass.getConstructor(AssetManager.class).newInstance(assetManager);
        } catch (Exception e) {
            e.printStackTrace();
        }
        TradersOfTheLastCarp.musicPlayer.playSong("test7", 1f);
        TradersOfTheLastCarp.musicPlayer.getCurrentSong().setLooping(true);
        titleScreen = new TotlcObject(assetManager, new Rectangle()) {

            String asset = AssetList.TITLE_SCREEN.toString();

            @Override
            public void draw(Batch batch, float alpha) {
                batch.draw(getAssetManager().get(asset, Texture.class), 0f, 0f, (float) TradersOfTheLastCarp.CONFIG_WIDTH, (float) TradersOfTheLastCarp.CONFIG_HEIGHT);
            }

            @Override
            public boolean collidesWith(Actor otherActor) {
                return false;
            }

            @Override
            public void endCollidesWith(Actor otherActor) {}
        };

        //Quick-play
        menuOptions.add(new MenuOption(assetManager, AssetList.OPTION_QUICKPLAY.toString()) {
            @Override
            public void draw(Batch batch, float alpha) {
                batch.draw(getAssetManager().get(asset, Texture.class), (float) optionsStart.getX(), (float) optionsStart.getY(), (float)optionsSize.getX(), (float)optionsSize.getY());
            }

            public void execute() {
                setNextLevel(Level00.class);
                TradersOfTheLastCarp.musicPlayer.playSong("test6", 0.3f);
                initNextLevel();
            }
        });

        //Level-select
        menuOptions.add(new MenuOption(assetManager, AssetList.OPTION_LVLSELECT.toString()) {
            @Override
            public void draw(Batch batch, float alpha) {
                batch.draw(getAssetManager().get(asset, Texture.class), (float) optionsStart.getX(), (float) optionsStart.getY(), (float)optionsSize.getX(), (float)optionsSize.getY());
            }

            public void execute() {
                setNextLevel(LevelSelect.class);
                initNextLevel();
            }
        });

        //Character-select
        menuOptions.add(new MenuOption(assetManager, AssetList.OPTION_CHARSELECT.toString()) {
            @Override
            public void draw(Batch batch, float alpha) {
                batch.draw(getAssetManager().get(asset, Texture.class), (float) optionsStart.getX(), (float) optionsStart.getY(), (float)optionsSize.getX(), (float)optionsSize.getY());
            }

            public void execute() {
                setNextLevel(CharacterSelect.class);
                initNextLevel();
            }
        });

        addActor(titleScreen);

        //Button Prompt
        cursor = new ButtonPrompt(assetManager, AssetList.BUTTON_PROMPT_SPACE.toString(), TradersOfTheLastCarp.CONFIG_WIDTH - 250 * cursorScale - 50, 20) {
            private float baseY = getY();

            @Override
            public void draw(Batch batch, float alpha) {
                if (System.currentTimeMillis() % 1000 <= 200) {
                    return;
                }
                batch.draw(getAsset(), getX(), getY(), 300 * cursorScale, 120 * cursorScale);
            }

            @Override
            public void update() {
//                setY(baseY - (optionFocusIndex - 1) * 120 * cursorScale);
            }
        };

        cursor.update();
        addActor(cursor);
        addActor(menuOptions.get(optionFocusIndex));
    }

    public void act(float delta) {
        for (Actor a: getActors()) {
            a.act(delta);
        }
    }

    @Override
    public void initOtherLevelStuff() {}

    public boolean keyDown(int keyCode) {
        boolean isHandled = false;

        if (keyCode == Input.Keys.UP) {
            optionFocusIndex = (optionFocusIndex - 1 + menuOptions.size()) % menuOptions.size();
            isHandled = true;
            Sound sound = Gdx.audio.newSound(Gdx.files.internal("sounds/UI_SFX_Set/switch7.wav"));
            sound.play(1.0f);
        }

        if (keyCode == Input.Keys.DOWN) {
            optionFocusIndex = (optionFocusIndex + 1) % menuOptions.size();
            isHandled = true;
            Sound sound = Gdx.audio.newSound(Gdx.files.internal("sounds/UI_SFX_Set/switch7.wav"));
            sound.play(1.0f);
        }

        cursor.update();

        for (MenuOption option: menuOptions) {
            option.remove();
        }
        addActor(menuOptions.get(optionFocusIndex));

        if (keyCode == Input.Keys.SPACE) {
            menuOptions.get(optionFocusIndex).execute();
            isHandled = true;
            Sound sound = Gdx.audio.newSound(Gdx.files.internal("sounds/UI_SFX_Set/switch5.wav"));
            sound.play(1.0f);
        }

        if (keyCode == Input.Keys.Q) {
            setNextLevel(SandBoxLevel.class);
            initNextLevel();
        }

        return isHandled;
    }

    public boolean keyUp(int keyCode) {
        return true;
    }
}
