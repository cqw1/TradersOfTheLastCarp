package com.totlc.levels;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.totlc.Actors.TotlcObject;
import com.totlc.Actors.UI.LevelOption;
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
    private LevelOption [][] actorGrid = new LevelOption [1][3];
    private Class[][] classGrid = new Class[actorGrid.length][actorGrid[0].length];

    private ArrayList<MenuOption> menuOptions = new ArrayList<MenuOption>();
    private Point2D.Float optionsSize = new Point2D.Float(128f, 128f);
    private Point2D.Float gridStart = new Point2D.Float(300f, 500f);

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

        addActor(levelSelectScreen);

        instantiateGrid();

        for (int r = 0; r < classGrid.length; r++) {
            for (int c = 0; c < classGrid[0].length; c++) {
                final int finalRow = r;
                final int finalCol = c;

                LevelOption levelOption = new LevelOption(
                        assetManager,
                        AssetList.QUESTION_MARK_SELECT.toString(),
                        AssetList.QUESTION_MARK_SELECT_BORDER.toString(),
                        classGrid[r][c],
                        (float) (gridStart.getX() + (c * 200)),
                        (float) (gridStart.getY() - (r * 200))) {


                    public void execute() {
                        setNextLevel(classGrid[finalRow][finalCol]);
                        initNextLevel();
                    }
                };

                actorGrid[r][c] = levelOption;
                addActor(levelOption);

            }
        }

        actorGrid[0][0].setSelected(true);
    }

    public void instantiateGrid() {
        classGrid[0][0] = Level01.class;
        classGrid[0][1] = Level02.class;
        classGrid[0][2] = Level03.class;
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

        unselectAll();

        if (keyCode == Input.Keys.UP) {
            row = (row - 1) % actorGrid.length;
            actorGrid[row][col].setSelected(true);
            isHandled = true;
        }

        if (keyCode == Input.Keys.DOWN) {
            row = (row + 1) % actorGrid.length;
            actorGrid[row][col].setSelected(true);
            isHandled = true;
        }

        if (keyCode == Input.Keys.LEFT) {
            col = (col - 1) % actorGrid[0].length;
            actorGrid[row][col].setSelected(true);
            isHandled = true;
        }

        if (keyCode == Input.Keys.RIGHT) {
            col = (col + 1) % actorGrid[0].length;
            actorGrid[row][col].setSelected(true);
            isHandled = true;
        }


        if (keyCode == Input.Keys.SPACE) {
            actorGrid[row][col].execute();
            isHandled = true;
        }

        if (keyCode == Input.Keys.Q) {
            setNextLevel(SandBoxLevel.class);
            initNextLevel();
        }

        return isHandled;
    }

    public void unselectAll() {
        for (int r = 0; r < actorGrid.length; r++) {
            for (int c = 0; c < actorGrid[0].length; c++) {
                actorGrid[r][c].setSelected(false);

            }
        }
    }

    public boolean keyUp(int keyCode) {
        return true;
    }

}
