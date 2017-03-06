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

    private String message;

    private TextureAtlas carpTalkAtlas, carpTurnAtlas;
    private Animation<TextureRegion> carpTalkAnimation, carpTurnAnimation;
    private float time = 0;

    private TextureAtlas currentAtlas;
    private Animation<TextureRegion> currentAnimation;

    // Time to spend talking and turning respectively in seconds.
    private int talkTime;
    private boolean doneTalking = false;
    private float animationTime = 0; // Time spent in current animation.

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

    // TODO: READ. general formula for height is 20 * (numLines + 1)
    public ToolTip(AssetManager assetManager, int x, int y, String message, int delay, int talkTime, int duration, boolean loop){
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
        this.talkTime = talkTime;
        this.delay = delay;
        this.loop = loop;

        carpTalkAtlas = assetManager.get(AssetList.CRYSTAL_CARP_TALK_BORDER.toString());
        carpTalkAnimation = new Animation<TextureRegion>(1 / 10f, carpTalkAtlas.getRegions(), Animation.PlayMode.LOOP);

        carpTurnAtlas = assetManager.get(AssetList.CRYSTAL_CARP_TURN_BORDER.toString());
        carpTurnAnimation = new Animation<TextureRegion>(1 / 16f, carpTurnAtlas.getRegions(), Animation.PlayMode.NORMAL);

        for (int i = 0; i < carpTurnAtlas.getRegions().size; i++){
            carpTurnAtlas.getRegions().get(i).getTexture().setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
        }
        for (int i = 0; i < carpTalkAtlas.getRegions().size; i++){
            carpTalkAtlas.getRegions().get(i).getTexture().setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
        }

        status = TURNING;
        currentAnimation = carpTurnAnimation;
        currentAtlas = carpTurnAtlas;

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

            // Start commenting out here to stop the turn/talk switching animation.
            if (status == TALKING) {
                if (animationTime > talkTime) {
                    if (!loop) {
                        setDoneTalking(true);
                    }
                    animationTime = 0;
                    currentAnimation = carpTurnAnimation;
                    currentAnimation.setPlayMode(Animation.PlayMode.REVERSED);
                    currentAtlas = carpTurnAtlas;
                    status = (status + 1) % 2;
                }

            } else if (status == TURNING) {
                if (currentAnimation.isAnimationFinished(animationTime) && !isDoneTalking()) {
                    animationTime = 0;
                    if(currentAnimation.getPlayMode().equals(Animation.PlayMode.REVERSED)){
                        currentAnimation.setPlayMode(Animation.PlayMode.NORMAL);
                    } else{
                        currentAnimation = carpTalkAnimation;
                        currentAtlas = carpTalkAtlas;
                        status = (status + 1) % 2;
                    }
                }
            }
            // Stop commenting out here.
        }


        if (time > (duration) && currentAnimation.isAnimationFinished(animationTime)) {
            // Check if we've stayed for as long as specified. If so, remove from screen.
            this.remove();
        }

    }

    @Override
    public void draw(Batch batch, float alpha){
        if (time > delay && status == TALKING) {
            // Draw message box.
            bar.draw(batch, getX(), getY(), getWidth(), getHeight());

            // Draw message.
            font.draw(batch, message, getX() + horizontalPadding, getY() + getHeight() / 2 + 5 * (numLines));
        }

        // Draw carp.
        batch.draw(currentAnimation.getKeyFrame(animationTime),
                getX() + getWidth() / 2 + carpHorizontalOffset,
                getY() - currentAtlas.getRegions().get(0).getRegionHeight() / 2 * scale + carpVerticalOffset,
                currentAtlas.getRegions().get(0).getRegionWidth() / 2 * scale,
                currentAtlas.getRegions().get(0).getRegionHeight() / 2 * scale,
                (float) currentAtlas.getRegions().get(0).getRegionWidth(),
                (float) currentAtlas.getRegions().get(0).getRegionHeight(), scale, scale, 0);
    }

    public boolean isDoneTalking() {
        return doneTalking;
    }

    // Reset to false to enable talking again.
    public void setDoneTalking(boolean doneTalking) {
        this.doneTalking = doneTalking;
    }

}
