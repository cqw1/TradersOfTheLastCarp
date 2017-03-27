package com.totlc.levels;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.totlc.Actors.Player;
import com.totlc.Actors.TotlcObject;
import com.totlc.Actors.UI.ButtonPrompt;
import com.totlc.Actors.players.*;
import com.totlc.AssetList;
import com.totlc.TradersOfTheLastCarp;

import java.awt.geom.Point2D;
import java.util.ArrayList;

public class CharacterSelect extends ALevel{

    // Hard coded value for playable characters. Remove when all characters are implemented.
    private static int playableCharacters = 2;

    TotlcObject characterSelectScreen;
    ArrayList<Class> availablePlayers = new ArrayList<Class>() {{
        add(Louisiana.class);
        add(Colorado.class);
        add(MrFischl.class);
        add(Ontario.class);
        add(Texas.class);
    }};
    ArrayList<PlayableCharacter> currentCharacters = new ArrayList<PlayableCharacter>();
    ArrayList<Point2D.Float> characterGridPoints = new ArrayList<Point2D.Float>();
    int currentlySelected = 0;
    Point2D.Float characterSelectStart = new Point2D.Float((float) (TradersOfTheLastCarp.CONFIG_WIDTH * (1 / 24.0)),
            (float)(TradersOfTheLastCarp.CONFIG_HEIGHT * (2 / 4.0)));
    ButtonPrompt cursor, names;
    private float cursorScale = 0.5f;
    TotlcObject characterHover;
    TotlcObject characterSelected;

    public CharacterSelect(AssetManager assetManager) {
        super(assetManager);

        characterSelectScreen = new TotlcObject(assetManager, new Rectangle()) {

            String asset = AssetList.CHARACTER_SELECT_SCREEN.toString();

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
        addActor(characterSelectScreen);

        float availableRoom = (float) (TradersOfTheLastCarp.CONFIG_WIDTH - 2.0f * characterSelectStart.getX());
        float spacePerCharacter = availableRoom / availablePlayers.size();
        float adjustedStart = (float) (((spacePerCharacter - Player.player_width) / 2) + characterSelectStart.getX());
        int i = 0;
        for (Class c: availablePlayers) {
            if (c.equals(TradersOfTheLastCarp.playerClass)) {
                currentlySelected = i;

                // draw the character that is currently selected
                characterSelected = new TotlcObject(assetManager, new Rectangle(adjustedStart, (float) characterSelectStart.getY(), 512, 512)) {
                    @Override
                    public void draw(Batch batch, float alpha) {
                        batch.draw(getTexture(), getX(), getY(), 0, 0, 128, 128, 1f, 1f, 0, 0, 0, (int) getWidth(), (int) getHeight(), false, false);
                    }

                    @Override
                    public boolean collidesWith(Actor otherActor) {
                        return false;
                    }

                    @Override
                    public void endCollidesWith(Actor otherActor) {

                    }
                };
                characterSelected.setTexture(new Texture(Gdx.files.internal(AssetList.GREEN_SELECT_BORDER.toString())));
                addActor(characterSelected);
                characterSelected.setZIndex(2);

                // draw the hover
                characterHover = new TotlcObject(assetManager, new Rectangle(adjustedStart, (float) characterSelectStart.getY(), 512, 512)) {
                    @Override
                    public void draw(Batch batch, float alpha) {
                        batch.draw(getTexture(), getX(), getY(), 0, 0, 128, 128, 1f, 1f, 0, 0, 0, (int) getWidth(), (int) getHeight(), false, false);
                    }

                    @Override
                    public boolean collidesWith(Actor otherActor) {
                        return false;
                    }

                    @Override
                    public void endCollidesWith(Actor otherActor) {

                    }
                };
                characterHover.setTexture(new Texture(Gdx.files.internal(AssetList.GREEN_SELECT.toString())));
                addActor(characterHover);
                characterHover.setZIndex(2);
            }

            try {
                PlayableCharacter p = (PlayableCharacter) c.getConstructor(AssetManager.class).newInstance(assetManager);
                p.setMaxVel(0.001f);
                p.moveAbs(adjustedStart, (float) characterSelectStart.getY());
                characterGridPoints.add(new Point2D.Float(adjustedStart, (float) characterSelectStart.getY()));
                adjustedStart += spacePerCharacter;
                addActor(p);
                currentCharacters.add(p);
            } catch (Exception n) {
                n.printStackTrace();
            }
            i++;
        }

        currentCharacters.get(currentlySelected).setMovingDown(true);

        cursor = new ButtonPrompt(assetManager, AssetList.BUTTON_PROMPT_SPACE.toString(), TradersOfTheLastCarp.CONFIG_WIDTH - 250 * cursorScale - 50, 30) {
            private float baseY = getY();

            @Override
            public void draw(Batch batch, float alpha) {
                if (System.currentTimeMillis() % 1000 <= 200) {
                    return;
                }
                batch.draw(getAssetManager().get(this.asset, Texture.class), getX(), getY(), 300 * cursorScale, 120 * cursorScale);
            }

            @Override
            public void update() {
//                setY(baseY - (optionFocusIndex - 1) * 120 * cursorScale);
            }
        };

        cursor.update();
        addActor(cursor);

        names = new ButtonPrompt(assetManager, AssetList.NAME_PLATES.toString(), TradersOfTheLastCarp.CONFIG_WIDTH / 2 - 375, 80) {
            private float baseY = getY();

            @Override
            public void draw(Batch batch, float alpha) {
                batch.draw(getAssetManager().get(this.asset, TextureAtlas.class).getRegions().get(currentlySelected), getX(), getY(), 750, 300);
            }

            @Override
            public void update() {
//                setY(baseY - (optionFocusIndex - 1) * 120 * cursorScale);
            }
        };

        names.update();
        addActor(names);
    }

    public void act(float delta) {
        for (Actor a: getActors()) {
            a.act(delta);
        }
    }

    public boolean keyDown(int keyCode) {
        boolean isHandled = false;
        if (keyCode == Input.Keys.LEFT) {
            currentCharacters.get(currentlySelected).setMovingDown(false);
            currentlySelected = (currentlySelected - 1 + playableCharacters) % playableCharacters;
            characterHover.moveAbs((float) characterGridPoints.get(currentlySelected).getX(), (float) characterGridPoints.get(currentlySelected).getY());
            isHandled = true;
            Sound sound = Gdx.audio.newSound(Gdx.files.internal("sounds/UI_SFX_Set/switch7.wav"));
            sound.play(1.0f);
            currentCharacters.get(currentlySelected).setMovingDown(true);
        }

        if (keyCode == Input.Keys.RIGHT) {
            currentCharacters.get(currentlySelected).setMovingDown(false);
            currentlySelected = (currentlySelected + 1) % playableCharacters;
            characterHover.moveAbs((float) characterGridPoints.get(currentlySelected).getX(), (float) characterGridPoints.get(currentlySelected).getY());
            isHandled = true;
            Sound sound = Gdx.audio.newSound(Gdx.files.internal("sounds/UI_SFX_Set/switch7.wav"));
            sound.play(1.0f);
            currentCharacters.get(currentlySelected).setMovingDown(true);
        }

        if (keyCode == Input.Keys.SPACE) {
            TradersOfTheLastCarp.playerClass = availablePlayers.get(currentlySelected);
            characterSelected.moveAbs((float) characterGridPoints.get(currentlySelected).getX(), (float) characterGridPoints.get(currentlySelected).getY());
        }

        if (keyCode == Input.Keys.ESCAPE) {
            setNextLevel(TitleScreen.class);
            initNextLevel();
            Sound sound = Gdx.audio.newSound(Gdx.files.internal("sounds/negative0.wav"));
            sound.play(1.0f);
        }

        return isHandled;
    }

    public boolean keyUp(int keyCode) {
        return true;
    }

    @Override
    public void initOtherLevelStuff() {}
}
