package com.totlc.levels;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.totlc.Actors.TotlcObject;
import com.totlc.Actors.UI.ButtonPrompt;
import com.totlc.Actors.UI.LevelOption;
import com.totlc.Actors.UI.MenuOption;
import com.totlc.AssetList;
import com.totlc.TradersOfTheLastCarp;
import com.totlc.Util;

import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.HashMap;

public class LevelSelect extends ALevel {
    public TotlcObject levelSelectScreen;

    private int row = 0;
    private int col = 0;
    private LevelOptionInfo [][] grid = new LevelOptionInfo[4][6];

    private ArrayList<MenuOption> menuOptions = new ArrayList<MenuOption>();
    private Point2D.Float optionsSize = new Point2D.Float(128f, 128f);
    private Point2D.Float gridStart = new Point2D.Float(260f, 550f);

    private ButtonPrompt spacePrompt, escPrompt;
    private float cursorScale = 0.5f;

    private AssetManager assetManager;

    public LevelSelect(AssetManager assetManager) {
        super(assetManager);
        TradersOfTheLastCarp.musicPlayer.stop();
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

        for (int r = 0; r < grid.length; r++) {
            for (int c = 0; c < grid[0].length; c++) {

                /*
                if (grid[r][c] == null) {
                    // Don't draw the asset because it doesn't have a corresponding level.
                    // Allows for irregular number of levels.
                    continue;
                }
                */

                final int finalRow = r;
                final int finalCol = c;

                LevelOption levelOption = new LevelOption(
                        assetManager,
                        AssetList.QUESTION_MARK_SELECT.toString(),
                        AssetList.QUESTION_MARK_SELECT_BORDER.toString(),
                        grid[r][c].levelClass,
                        grid[r][c].levelName,
                        (float) (gridStart.getX() + (c * 200)),
                        (float) (gridStart.getY() - (r * 150))) {


                    public void execute() {
                        TradersOfTheLastCarp.musicPlayer.playSong("test6", 0.3f);
                        setNextLevel(grid[finalRow][finalCol].levelClass);
                        initNextLevel();
                    }
                };

                grid[r][c].levelOption = levelOption;
                addActor(levelOption);

            }
        }

        grid[0][0].levelOption.setSelected(true);

        //Button Prompt
        spacePrompt = new ButtonPrompt(assetManager, AssetList.BUTTON_PROMPT_SPACE.toString(), TradersOfTheLastCarp.CONFIG_WIDTH - 250 * cursorScale - 50, 30) {
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

        spacePrompt.update();
        addActor(spacePrompt);

        escPrompt = new ButtonPrompt(assetManager, AssetList.BUTTON_PROMPT_ESC.toString(), TradersOfTheLastCarp.CONFIG_WIDTH - 250 * cursorScale - 50, 80) {
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

        escPrompt.update();
        addActor(escPrompt);
    }

    public void instantiateGrid() {
        ArrayList<LevelOptionInfo> levels = new ArrayList<LevelOptionInfo>();

        /**
         * TODO: Add in reverse order since we populate the grid by removing from the end. For that O(1) time lol.
         * TODO: This isn't really a todo. I just wanted the color to pop out.
         */
        levels.add(new LevelOptionInfo(null, Level20.class, Util.parseLevelString(AssetList.LEVEL20_TMX.toString())));
        levels.add(new LevelOptionInfo(null, Level19.class, Util.parseLevelString(AssetList.LEVEL19_TMX.toString())));
        levels.add(new LevelOptionInfo(null, Level18.class, Util.parseLevelString(AssetList.LEVEL18_TMX.toString())));
        levels.add(new LevelOptionInfo(null, Level17.class, Util.parseLevelString(AssetList.LEVEL17_TMX.toString())));
        levels.add(new LevelOptionInfo(null, Level16.class, Util.parseLevelString(AssetList.LEVEL16_TMX.toString())));
        levels.add(new LevelOptionInfo(null, Level15.class, Util.parseLevelString(AssetList.LEVEL15_TMX.toString())));
        levels.add(new LevelOptionInfo(null, Level14.class, Util.parseLevelString(AssetList.LEVEL14_TMX.toString())));
        levels.add(new LevelOptionInfo(null, Level13.class, Util.parseLevelString(AssetList.LEVEL13_TMX.toString())));
        levels.add(new LevelOptionInfo(null, Level12.class, Util.parseLevelString(AssetList.LEVEL12_TMX.toString())));
        levels.add(new LevelOptionInfo(null, Level11.class, Util.parseLevelString(AssetList.LEVEL11_TMX.toString())));
        levels.add(new LevelOptionInfo(null, Level10.class, Util.parseLevelString(AssetList.LEVEL10_TMX.toString())));
        levels.add(new LevelOptionInfo(null, Level09.class, Util.parseLevelString(AssetList.LEVEL09_TMX.toString())));
        levels.add(new LevelOptionInfo(null, Level08.class, Util.parseLevelString(AssetList.LEVEL08_TMX.toString())));
        levels.add(new LevelOptionInfo(null, Level07.class, Util.parseLevelString(AssetList.LEVEL07_TMX.toString())));
        levels.add(new LevelOptionInfo(null, Level06.class, Util.parseLevelString(AssetList.LEVEL06_TMX.toString())));
        levels.add(new LevelOptionInfo(null, Level05.class, Util.parseLevelString(AssetList.LEVEL05_TMX.toString())));
        levels.add(new LevelOptionInfo(null, Level04.class, Util.parseLevelString(AssetList.LEVEL04_TMX.toString())));
        levels.add(new LevelOptionInfo(null, Level03.class, Util.parseLevelString(AssetList.LEVEL03_TMX.toString())));
        levels.add(new LevelOptionInfo(null, Level02.class, Util.parseLevelString(AssetList.LEVEL02_TMX.toString())));
        levels.add(new LevelOptionInfo(null, Level01.class, Util.parseLevelString(AssetList.LEVEL01_TMX.toString())));
        levels.add(new LevelOptionInfo(null, Level00.class, "Baby Steps"));

        for (int r = 0; r < grid.length; r++) {
            for (int c = 0; c < grid[r].length; c++) {
                if (levels.size() > 0) {
                    grid[r][c] = levels.remove(levels.size() - 1);
                } else {
                    grid[r][c] = new LevelOptionInfo(null, null, "TODO");

                }

            }
        }
    }

    @Override
    public void initOtherLevelStuff() {

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
            row = (((row - 1) % grid.length) + grid.length) % grid.length;

            if (row != (oldRow - 1) && grid[row][col] == null) {
                // We wrapped around, but matrix isn't fully filled and there's no level in the expected position.
                boolean found = false;
                int checkRow = grid.length - 1;
                int checkCol = 1;
                while (!found) {
                    if (grid[checkRow][0] == null) {
                        // No levels in this row. Move up a row.
                        checkRow -= 1;
                    } else if (grid[checkRow][checkCol] == null) {
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
            grid[row][col].levelOption.setSelected(true);
            isHandled = true;
            Sound sound = Gdx.audio.newSound(Gdx.files.internal("sounds/UI_SFX_Set/switch7.wav"));
            sound.play(1.0f);
        }

        /**
         * Wrapping behavior priorities:
         *  1. Go down to next row to valid level.
         *  2. If last row, wrap to top
         *  3. If we were not last row and no valid level in same column, go to last valid level in next row.
         *  4. If we were not last row and no valid levels in next row (our matrix has unfilled rows), manually wrap to top.
         */

        if (keyCode == Input.Keys.DOWN) {
            row = (((row + 1) % grid.length) + grid.length) % grid.length;

            if (row == (oldRow + 1) && grid[row][col] == null) {
                // Haven't wrapped around, but matrix isn't fully filled and there's no level in the expected position.
                boolean found = false;
                int checkCol = 1;
                while (!found) {
                    if (grid[row][0] == null) {
                        // No levels in this row. Manually wrap to top.
                        row = 0;
                        found = true;
                    } else if (grid[row][checkCol] == null) {
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
            grid[row][col].levelOption.setSelected(true);
            isHandled = true;
            Sound sound = Gdx.audio.newSound(Gdx.files.internal("sounds/UI_SFX_Set/switch7.wav"));
            sound.play(1.0f);
        }

        /**
         * Wrapping behavior priorities:
         *  1. If at the beginning of a row, wrap to the end of the same row.
         *  2. If no valid level at the end of the same row, choose the last valid level in our row.
         */
        if (keyCode == Input.Keys.LEFT) {
            col = (((col - 1) % grid[0].length) + grid[0].length) % grid[0].length;

            if (grid[row][col] == null) {
                // Irregular matrix. Find last valid level in this row.
                for (int i = 0; i < grid[0].length; i++) {
                    if (grid[row][i] == null) {
                        // Went too far. Last column had valid level.
                        col = i - 1;
                        break;
                    }
                }
            }

            unselectAll();
            grid[row][col].levelOption.setSelected(true);
            isHandled = true;
            Sound sound = Gdx.audio.newSound(Gdx.files.internal("sounds/UI_SFX_Set/switch7.wav"));
            sound.play(1.0f);
        }

        /**
         * Wrapping behavior priorities:
         *  1. If at the end of a row, wrap to the beginning of the same row.
         *  2. If no valid level to the immediate right (irregular matrix), manually wrap to beginning.
         */
        if (keyCode == Input.Keys.RIGHT) {
            col = (((col + 1) % grid[0].length) + grid[0].length) % grid[0].length;

            if (grid[row][col] == null) {
                // Irregular matrix. Manually wrap to left.
                col = 0;
            }

            unselectAll();
            grid[row][col].levelOption.setSelected(true);
            isHandled = true;
            Sound sound = Gdx.audio.newSound(Gdx.files.internal("sounds/UI_SFX_Set/switch7.wav"));
            sound.play(1.0f);
        }


        if (keyCode == Input.Keys.SPACE) {
            grid[row][col].levelOption.execute();
            isHandled = true;
            Sound sound = Gdx.audio.newSound(Gdx.files.internal("sounds/positive0.wav"));
            sound.play(1.0f);
        }

        if (keyCode == Input.Keys.Q) {
            setNextLevel(SandBoxLevel.class);
            initNextLevel();
        }

        if (keyCode == Input.Keys.ESCAPE) {
            ALevel nextLevelObject = LevelFactory.createLevel(TitleScreen.class, assetManager);
            loadLevel(nextLevelObject);
            Sound sound = Gdx.audio.newSound(Gdx.files.internal("sounds/negative0.wav"));
            sound.play(1.0f);
        }

        return isHandled;
    }

    public void unselectAll() {
        for (int r = 0; r < grid.length; r++) {
            for (int c = 0; c < grid[0].length; c++) {
                if (grid[r][c] != null) {
                    grid[r][c].levelOption.setSelected(false);
                }
            }
        }
    }

    public boolean keyUp(int keyCode) {
        return true;
    }
}

/**
 * Only used in this file to encapsulate all the info for one level select item.
 */
class LevelOptionInfo {

    public LevelOption levelOption;
    public Class levelClass;
    public String levelName;

    public LevelOptionInfo(LevelOption levelOption, Class levelClass, String levelName) {
        this.levelOption = levelOption;
        this.levelClass = levelClass;
        this.levelName = levelName;
    }
}

