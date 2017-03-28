package com.totlc.Actors.UI;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.*;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.totlc.Actors.carps.CrystalCarp;
import com.totlc.AssetList;
import com.totlc.TradersOfTheLastCarp;
import com.totlc.levels.ALevel;

public class ToolTip extends Actor {
    // Texture information.
    private NinePatch bar;
    private BitmapFont font;

    private String message;

    private float time = 0;

    private TextureAtlas currentAtlas;
    private Animation<TextureRegion> currentAnimation;

    // Number of lines the message takes up. Had to manually line break the message.
    private int numLines = 1;
    // Seconds to display the tooltip for
    private int duration;
    // Boolean flag for repeating animation.
    private boolean loop;

    // Seconds to delay until animating the tooltip. Should delay tooltip creation in constructor.
    private int delay;

    private static float scale = 0.75f;
    // Padding for the text within the black background.
    private int horizontalPadding = 25;
    private static float carpVerticalOffset = 5;
    private static float carpHorizontalOffset = 60;
    private CrystalCarp carp;

    // TODO: READ. general formula for height is 20 * (numLines + 1)
    public ToolTip(AssetManager assetManager, int x, int y, String message, int delay, int talkTime, int duration, boolean loop) {
        setX(x);
        setY(y);

        // Programmatically figure out width and height based on message.
        String[] lines = message.split("\\r?\\n");
        numLines = lines.length;
        int longestLength = 0;
        for (String line: lines) {
            longestLength = Math.max(longestLength, line.length());
        }

        setWidth((longestLength * 7) + (horizontalPadding * 2));
        setHeight((numLines + 1) * 20);

        this.message = message;
        this.duration = Math.max(duration, delay + talkTime);
        this.delay = delay;
        this.loop = loop;

        TextureAtlas carpTalkAtlas = assetManager.get(AssetList.CRYSTAL_CARP_TALK_BORDER.toString());

        this.carp = new CrystalCarp(assetManager,
                getX() + getWidth() / 2 + carpHorizontalOffset,
                getY() - carpTalkAtlas.getRegions().get(0).getRegionHeight() / 2 * scale + carpVerticalOffset,
                delay,
                talkTime,
                loop,
                scale
        );

        bar = new NinePatch(assetManager.get(AssetList.UI_BAR.toString(), Texture.class), 16, 16, 8, 8);
        font = TradersOfTheLastCarp.systemFont0;
        font.getRegion().getTexture().setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
    }

    @Override
    public void act(float deltaTime) {
        // deltaTime = time in seconds since last frame
        time += deltaTime;
        carp.act(deltaTime);

        if (time > duration) {
            this.remove();
            carp.remove();
        }
    }

    @Override
    public void draw(Batch batch, float alpha){
        if (time > delay) {
            // Draw message box.
            bar.draw(batch, getX(), getY(), getWidth(), getHeight());

            // Draw message.
            font.draw(batch, message, getX() + horizontalPadding, getY() + getHeight() / 2 + 5 * (numLines));
        }

        carp.draw(batch, alpha);


    }

}
