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

public class TitleScreen extends ALevel {

    private final TotlcObject titleScreen;
    private int optionFocusIndex = 0;
    private ArrayList<MenuOption> menuOptions = new ArrayList<MenuOption>();
    private Point2D.Float optionsSize = new Point2D.Float(280f, 120f);
    private Point2D.Float optionsStart = new Point2D.Float((float) (TradersOfTheLastCarp.CONFIG_WIDTH / 2 - optionsSize.getX() / 2), (float) (TradersOfTheLastCarp.CONFIG_HEIGHT / 2 - optionsSize.getY() - 180f));

    public TitleScreen(AssetManager assetManager) {
        super(assetManager);
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
                batch.draw(getAssetManager().get(asset, Texture.class), (float) optionsStart.getX(), (float) optionsStart.getY(), 280f, 120f);
            }

            public void execute() {
                setNextLevel(Level01.class);
                initNextLevel();
            }
        });

        //Level-select
        menuOptions.add(new MenuOption(assetManager, AssetList.OPTION_LVLSELECT.toString()) {
            @Override
            public void draw(Batch batch, float alpha) {
                batch.draw(getAssetManager().get(asset, Texture.class), (float) optionsStart.getX(), (float) optionsStart.getY(), 280f, 120f);
            }

            public void execute() {
                setNextLevel(LevelSelect.class);
                initNextLevel();
            }
        });

        addActor(titleScreen);
        addActor(menuOptions.get(optionFocusIndex));
    }

    public void act(float delta) {
        for (Actor a: getActors()) {
            a.act(delta);
        }
    }

    @Override
    public void initLevel() {}

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

        return isHandled;
    }

    public boolean keyUp(int keyCode) {
        return true;
    }
}
