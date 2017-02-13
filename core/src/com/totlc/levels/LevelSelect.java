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
    private LevelOption [][] actorGrid = new LevelOption [2][6];
    private Class[][] classGrid = new Class[actorGrid.length][actorGrid[0].length];

    private ArrayList<MenuOption> menuOptions = new ArrayList<MenuOption>();
    private Point2D.Float optionsSize = new Point2D.Float(128f, 128f);
    private Point2D.Float gridStart = new Point2D.Float(236f, 500f);
    private AssetManager assetManager;

    public LevelSelect(AssetManager assetManager) {
        super(assetManager);

        this.assetManager = assetManager;
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

                if (classGrid[r][c] == null) {
                    // Don't draw the asset because it doesn't have a corresponding level.
                    // Allows for irregular number of levels.
                    actorGrid[r][c] = null;
                    continue;
                }

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
        classGrid[0][0] = SpikeLevel.class;
        classGrid[0][1] = TeleporterLevel.class;
        classGrid[0][2] = SpiderLevel.class;
        classGrid[0][3] = StarLevel.class;
        classGrid[0][4] = FlanLevel.class;
        classGrid[0][5] = FlameLevel.class;

        classGrid[1][0] = GelatinLevel.class;

        // Empty
        classGrid[1][1] = null;
        classGrid[1][2] = null;
        classGrid[1][3] = null;
        classGrid[1][4] = null;
        classGrid[1][5] = null;
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

        int oldRow = row;

        /**
         * Wrapping behavior priorities:
         *  1. Go up to prev row to valid level.
         *  2. If we were top row, wrap to bottom valid level.
         *  3. If we were top row and bottom row doesn't have valid level in our column (irregular matrix), go to last valid level in row.
         *  4. If we were top row and no valid level in bottom row (our matrix has unfilled rows), go up row by row until we hit a row with valid levels. Iterate through until we select the last valid level in that row.
         */
        if (keyCode == Input.Keys.UP) {
            row = (((row - 1) % actorGrid.length) + actorGrid.length) % actorGrid.length;

            if (row != (oldRow - 1) && actorGrid[row][col] == null) {
                // We wrapped around, but matrix isn't fully filled and there's no level in the expected position.
                boolean found = false;
                int checkRow = actorGrid.length - 1;
                int checkCol = 1;
                while (!found) {
                    if (actorGrid[checkRow][0] == null) {
                        // No levels in this row. Move up a row.
                        checkRow -= 1;
                    } else if (actorGrid[checkRow][checkCol] == null) {
                        // Went too far, is no longer a valid level.
                        // Set selected to be previous one.
                        row = checkRow;
                        col = checkCol - 1;
                        found = true;
                    } else {
                        // There are levels in this row but we haven't found the end yet.
                        // Find the last actual level in this row.
                        checkCol += 1;
                    }
                }
            }

            unselectAll();
            actorGrid[row][col].setSelected(true);
            isHandled = true;
        }

        /**
         * Wrapping behavior priorities:
         *  1. Go down to next row to valid level.
         *  2. If last row, wrap to top
         *  3. If we were not last row and no valid level in same column, go to last valid level in next row.
         *  4. If we were not last row and no valid levels in next row (our matrix has unfilled rows), manually wrap to top.
         */

        if (keyCode == Input.Keys.DOWN) {
            row = (((row + 1) % actorGrid.length) + actorGrid.length) % actorGrid.length;

            if (row == (oldRow + 1) && actorGrid[row][col] == null) {
                // Haven't wrapped around, but matrix isn't fully filled and there's no level in the expected position.
                boolean found = false;
                int checkCol = 1;
                while (!found) {
                    if (actorGrid[row][0] == null) {
                        // No levels in this row. Manually wrap to top.
                        row = 0;
                        found = true;
                    } else if (actorGrid[row][checkCol] == null) {
                        // Went too far, is no longer a valid level.
                        // Set selected to be previous one.
                        col = checkCol - 1;
                        found = true;
                    } else {
                        // There are levels in this row but we haven't found the end yet.
                        // Find the last actual level in this row.
                        checkCol += 1;
                    }
                }
            }

            unselectAll();
            actorGrid[row][col].setSelected(true);
            isHandled = true;
        }

        /**
         * Wrapping behavior priorities:
         *  1. If at the beginning of a row, wrap to the end of the same row.
         *  2. If no valid level at the end of the same row, choose the last valid level in our row.
         */
        if (keyCode == Input.Keys.LEFT) {
            col = (((col - 1) % actorGrid[0].length) + actorGrid[0].length) % actorGrid[0].length;

            if (actorGrid[row][col] == null) {
                // Irregular matrix. Find last valid level in this row.
                for (int i = 0; i < actorGrid[0].length; i++) {
                    if (actorGrid[row][i] == null) {
                        // Went too far. Last column had valid level.
                        col = i - 1;
                        break;
                    }
                }
            }

            unselectAll();
            actorGrid[row][col].setSelected(true);
            isHandled = true;
        }

        /**
         * Wrapping behavior priorities:
         *  1. If at the end of a row, wrap to the beginning of the same row.
         *  2. If no valid level to the immediate right (irregular matrix), manually wrap to beginning.
         */
        if (keyCode == Input.Keys.RIGHT) {
            col = (((col + 1) % actorGrid[0].length) + actorGrid[0].length) % actorGrid[0].length;

            if (actorGrid[row][col] == null) {
                // Irregular matrix. Manually wrap to left.
                col = 0;
            }

            unselectAll();
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

        if (keyCode == Input.Keys.ESCAPE) {
            ALevel nextLevelObject = LevelFactory.createLevel(TitleScreen.class, assetManager);
            loadLevel(nextLevelObject);
        }

        return isHandled;
    }

    public void unselectAll() {
        for (int r = 0; r < actorGrid.length; r++) {
            for (int c = 0; c < actorGrid[0].length; c++) {
                if (actorGrid[r][c] != null) {
                    actorGrid[r][c].setSelected(false);
                }
            }
        }
    }

    public boolean keyUp(int keyCode) {
        return true;
    }

}
