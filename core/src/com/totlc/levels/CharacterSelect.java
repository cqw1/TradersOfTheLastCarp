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
import com.totlc.Actors.players.Colorado;
import com.totlc.Actors.players.Louisiana;
import com.totlc.Actors.players.PlayableCharacter;
import com.totlc.AssetList;
import com.totlc.TradersOfTheLastCarp;

import java.awt.geom.Point2D;
import java.util.ArrayList;

public class CharacterSelect extends ALevel{

    TotlcObject characterSelectScreen;
    ArrayList<Class> availablePlayers = new ArrayList<Class>() {{
        add(Louisiana.class);
        add(Colorado.class);
    }};
    ArrayList<PlayableCharacter> currentCharacters = new ArrayList<PlayableCharacter>();
    int currentlySelected = 0;
    Point2D.Float characterSelectStart = new Point2D.Float((float) (TradersOfTheLastCarp.CONFIG_WIDTH * (1 / 6.0)),
            (float)(TradersOfTheLastCarp.CONFIG_HEIGHT * (1 / 4.0)));
    ButtonPrompt cursor;
    private float cursorScale = 0.5f;

    public CharacterSelect(AssetManager assetManager) {
        super(assetManager);

        characterSelectScreen = new TotlcObject(assetManager, new Rectangle()) {

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
        addActor(characterSelectScreen);

        float availableRoom = (float) (TradersOfTheLastCarp.CONFIG_WIDTH - 2.0f * characterSelectStart.getX());
        float spacePerCharacter = availableRoom / availablePlayers.size();
        float adjustedStart = (float) (((spacePerCharacter - Player.player_width) / 2) + characterSelectStart.getX());
        int i = 0;
        for (Class c: availablePlayers) {
            if (c.equals(TradersOfTheLastCarp.playerClass)) {
                currentlySelected = i;
            }

            try {
                PlayableCharacter p = (PlayableCharacter) c.getConstructor(AssetManager.class).newInstance(assetManager);
                p.setMaxVel(0.001f);
                p.moveAbs(adjustedStart, (float) characterSelectStart.getY());
                adjustedStart += spacePerCharacter;
                addActor(p);
                currentCharacters.add(p);
            } catch (Exception n) {
                n.printStackTrace();
            }
            i++;
        }

        currentCharacters.get(currentlySelected).setMovingDown(true);

        cursor = new ButtonPrompt(assetManager, AssetList.BUTTON_PROMPT_SPACE.toString(), TradersOfTheLastCarp.CONFIG_WIDTH - 250 * cursorScale - 50, 20) {
            private float baseY = getY();

            @Override
            public void draw(Batch batch, float alpha) {
                batch.draw(getAssetManager().get(this.asset, Texture.class), getX(), getY(), 300 * cursorScale, 120 * cursorScale);
            }

            @Override
            public void update() {
//                setY(baseY - (optionFocusIndex - 1) * 120 * cursorScale);
            }
        };

        cursor.update();
        addActor(cursor);
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
            currentlySelected = Math.abs((currentlySelected - 1) % availablePlayers.size());
            isHandled = true;
            Sound sound = Gdx.audio.newSound(Gdx.files.internal("sounds/UI_SFX_Set/switch7.wav"));
            sound.play(1.0f);
            currentCharacters.get(currentlySelected).setMovingDown(true);
        }

        if (keyCode == Input.Keys.RIGHT) {
            currentCharacters.get(currentlySelected).setMovingDown(false);
            currentlySelected = (currentlySelected + 1) % availablePlayers.size();
            isHandled = true;
            Sound sound = Gdx.audio.newSound(Gdx.files.internal("sounds/UI_SFX_Set/switch7.wav"));
            sound.play(1.0f);
            currentCharacters.get(currentlySelected).setMovingDown(true);
        }

        if (keyCode == Input.Keys.SPACE) {
            TradersOfTheLastCarp.playerClass = availablePlayers.get(currentlySelected);
            setNextLevel(TitleScreen.class);
            initNextLevel();
        }

        return isHandled;
    }

    public boolean keyUp(int keyCode) {
        return true;
    }

    @Override
    public void initOtherLevelStuff() {}
}
