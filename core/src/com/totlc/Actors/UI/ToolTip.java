package com.totlc.Actors.UI;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.*;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.totlc.AssetList;
import com.totlc.TradersOfTheLastCarp;
import com.totlc.levels.ALevel;

public class ToolTip extends Actor {
    // Status of carp
    private final int TALKING = 0;
    private final int TURNING = 1;
    private int status;

    // Texture information.
    private NinePatch bar;
    private BitmapFont font;

    private int iconSize = 48;

    private String message;

    private TextureAtlas carpTalkAtlas, carpTurnAtlas;
    private Animation<TextureRegion> carpTalkAnimation, carpTurnAnimation;
    private float time = 0;

    private TextureAtlas currentAtlas;
    private Animation<TextureRegion> currentAnimation;

    // Time spend talking and turning respectively in seconds.
    private int talkTime = 2;
    private int turnTime = 2;
    private float animationTime = 0; // Time spend in current animation.

    // Number of lines the message takes up. Had to manually line break the message.
    private int numLines = 1;
    // Seconds to display the tooltip for
    private int duration;

    // Seconds to delay until displaying the tooltip
    private int delay;

    private float scale = 0.75f;
    private int horizontalPadding = 25;


    // TODO: READ. general formula for height is 20 * (numLines + 1)
    public ToolTip(AssetManager assetManager, int x, int y, String message, int delay, int duration){
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
        this.duration = duration;
        this.delay = delay;

        carpTalkAtlas = assetManager.get(AssetList.CRYSTAL_CARP_TALK.toString());
        carpTalkAnimation = new Animation<TextureRegion>(1 / 10f, carpTalkAtlas.getRegions());

        carpTurnAtlas = assetManager.get(AssetList.CRYSTAL_CARP_TURN.toString());
        carpTurnAnimation = new Animation<TextureRegion>(1 / 10f, carpTurnAtlas.getRegions());

        status = TALKING;
        currentAnimation = carpTalkAnimation;
        currentAtlas = carpTalkAtlas;


        bar = new NinePatch(assetManager.get(AssetList.UI_BAR.toString(), Texture.class), 16, 16, 8, 8);
        font = TradersOfTheLastCarp.systemFont0;
        font.getRegion().getTexture().setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
    }

    @Override
    public void act(float deltaTime) {
        // deltaTime = time in seconds since last frame
        time += deltaTime;

        if (time > delay) {
            animationTime += deltaTime;
            /*
            if (status == TALKING) {
                if (animationTime > talkTime) {
                    animationTime = 0;
                    currentAnimation = carpTurnAnimation;
                    currentAtlas = carpTurnAtlas;
                    status = (status + 1) % 2;
                }

            } else if (status == TURNING) {
                if (animationTime > turnTime) {
                    animationTime = 0;
                    currentAnimation = carpTalkAnimation;
                    currentAtlas = carpTalkAtlas;
                    status = (status + 1) % 2;
                }
            }
            */
        }


        if (time > (delay + duration)) {
            // Check if we've stayed for as long as specified. If so, remove from screen.
            this.remove();
        }

    }

    @Override
    public void draw(Batch batch, float alpha){
        if (time > delay) {
            // Draw base texture.
            bar.draw(batch, getX(), getY(), getWidth(), getHeight());

            // Draw message.
            font.draw(batch, message, getX() + horizontalPadding, getY() + getHeight() / 2 + 5 * (numLines));

            // Draw carp.
            batch.draw(currentAnimation.getKeyFrame(time, true),
                    getX() - currentAtlas.getRegions().get(0).getRegionWidth() / 2,
                    getY() - currentAtlas.getRegions().get(0).getRegionHeight() / 2,
                    0,
                    0,
                    (float) currentAtlas.getRegions().get(0).getRegionWidth(),
                    (float) currentAtlas.getRegions().get(0).getRegionHeight(), scale, scale, 0);
        }
    }
}
