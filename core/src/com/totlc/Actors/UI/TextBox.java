package com.totlc.Actors.UI;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.*;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.totlc.AssetList;
import com.totlc.TradersOfTheLastCarp;

import static com.totlc.TradersOfTheLastCarp.CONFIG_WIDTH;

public class TextBox extends Actor {
    // Texture information.
    private NinePatch box;
    private BitmapFont font;

    private String message;

    private float time = 0;

    // Number of lines the message takes up. Had to manually line break the message.
    private int numLines = 1;
    // Seconds to display the tooltip for
    private float duration;
    // Boolean flag for repeating animation.

    // Seconds to delay until the textbox shows up
    private float delay;

    private static float scale = 0.9f;
    // Padding for the text within the black background.
    private int horizontalPadding = 50;

    private float talkingIntervalTime = 0.15f; // In seconds
    private float talkTime; // In seconds
    private String[] words;
    private int wordIndex;

    private Sound sound = Gdx.audio.newSound(Gdx.files.internal(AssetList.BUBBLES.toString()));

    private ButtonPrompt spacePrompt;
    private float buttonScale = 0.5f;

    private final int WALL_WIDTH = 64;

    private Actor speaker;


    // TODO: READ. general formula for height is 20 * (numLines + 1)


    public TextBox(AssetManager assetManager, String message) {
        this(assetManager, message, 0, 5, null);
    }
    /**
     *
     * @param assetManager
     * @param message String message to display in the textbox
     * @param delay Seconds until the textbox shows
     * @param duration Seconds how long the text box appears for. The max of talking + delay, or duration
     */
    public TextBox(AssetManager assetManager, String message, float delay, float duration, Actor speaker) {
        TradersOfTheLastCarp.textBoxShowing = true;
        setX(WALL_WIDTH);
        setY(WALL_WIDTH);

        setWidth(CONFIG_WIDTH - 2 * WALL_WIDTH);
        setHeight(200);

        // Programmatically figure out width and height based on message.
        String[] lines = message.split("\\r?\\n");
        numLines = lines.length;
        int longestLength = 0;
        for (String line: lines) {
            longestLength = Math.max(longestLength, line.length());
        }


        this.words = message.split(" ");

        this.talkTime = words.length * talkingIntervalTime;
        this.message = message;
        this.duration = Math.max(duration, delay + talkTime);
        this.delay = delay;


        this.speaker = speaker;

        box = new NinePatch(assetManager.get(AssetList.UI_BOX.toString(), Texture.class), 32, 32, 32, 32);
        font = TradersOfTheLastCarp.systemFont0L;

        font.getRegion().getTexture().setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);

        sound.play(0.8f);

        float speakerWidth = 0;
        if (speaker != null) {
            speakerWidth = speaker.getWidth();
        }

        spacePrompt = new ButtonPrompt(assetManager, AssetList.BUTTON_PROMPT_SPACE_SKIP.toString(), TradersOfTheLastCarp.CONFIG_WIDTH - WALL_WIDTH - speakerWidth - 250 * buttonScale - 50 , WALL_WIDTH + 30) {
            private float baseY = getY();

            @Override
            public void draw(Batch batch, float alpha) {
                if (System.currentTimeMillis() % 1000 <= 200) {
                    return;
                }
                batch.draw(getAsset(), getX(), getY(), 300 * buttonScale, 120 * buttonScale);
            }

            @Override
            public void update() {
//                setY(baseY - (optionFocusIndex - 1) * 120 * cursorScale);
            }
        };
    }

    @Override
    public void act(float deltaTime) {
        // deltaTime = time in seconds since last frame
        time += deltaTime;
        if (speaker != null) {
            speaker.act(deltaTime);
        }
        spacePrompt.act(deltaTime);

        if (time > duration) {
            removeTextBox();
        }

        wordIndex = Math.min((int) Math.floor(time / talkingIntervalTime), words.length);
    }

    @Override
    public void draw(Batch batch, float alpha){
        if (time > delay) {
            // Draw message box.
            box.draw(batch, getX(), getY(), getWidth(), getHeight());

            String message = "";
            for (int i = 0; i < wordIndex; i++) {
                message += words[i] + " ";
            }

            // Draw message.
            font.getData().setScale(0.3f);
            font.draw(batch, message, getX() + horizontalPadding, getY() + getHeight() / 2 + 5 * (numLines));
            font.getData().setScale(1.0f);
        }

        if (speaker != null) {
            speaker.draw(batch, alpha);
        }
        spacePrompt.draw(batch, alpha);

    }

    public void removeTextBox() {
        this.remove();
        if (speaker != null) {
            speaker.remove();
        }
        sound.stop();
        sound.dispose();
        TradersOfTheLastCarp.textBoxShowing = false;
    }

    public float getScale() {
        return scale;
    }

    public float getTalkTime() {
        return talkTime;
    }

    public void setSpeaker(Actor speaker) {
        this.speaker = speaker;
    }

}
