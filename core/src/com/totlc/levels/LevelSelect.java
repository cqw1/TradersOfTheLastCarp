package com.totlc.levels;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.totlc.Actors.TotlcObject;
import com.totlc.Actors.UI.MenuOption;
import com.totlc.AssetList;
import com.totlc.TradersOfTheLastCarp;

import java.awt.geom.Point2D;
import java.util.ArrayList;

public class LevelSelect extends ALevel {
    public TotlcObject levelSelectScreen;

    private int optionFocusIndex = 0;
    private int row = 0;
    private int col = 0;
    private Class[][] levelGrid = new Class[1][3];

    private ArrayList<MenuOption> menuOptions = new ArrayList<MenuOption>();
    private Point2D.Float optionsSize = new Point2D.Float(128f, 128f);
    private Point2D.Float optionsStart = new Point2D.Float(300f, 300f);

    public LevelSelect(AssetManager assetManager) {
        super(assetManager);

        levelSelectScreen = new TotlcObject(assetManager, new Rectangle()) {

            String asset = AssetList.LEVEL_SELECT_SCREEN.toString();

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

        levelGrid[0][0] = Level01.class;
        levelGrid[0][1] = Level02.class;
        levelGrid[0][2] = Level03.class;


        addActor(levelSelectScreen);
    }

    @Override
    public void initLevel() {

    }

    public void act(float delta) {
        for (Actor a: getActors()) {
            a.act(delta);
        }
    }

    public boolean keyDown(int keyCode) {
        boolean isHandled = false;

        if (keyCode == Input.Keys.UP) {
            optionFocusIndex = (optionFocusIndex + 1) % menuOptions.size();
            isHandled = true;
        }

        if (keyCode == Input.Keys.DOWN) {
            optionFocusIndex = Math.abs((optionFocusIndex - 1) % menuOptions.size());
            isHandled = true;
        }

        for (MenuOption option: menuOptions) {
            option.remove();
        }
        addActor(menuOptions.get(optionFocusIndex));

        if (keyCode == Input.Keys.SPACE) {
            menuOptions.get(optionFocusIndex).execute();
            isHandled = true;
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
