package com.totlc.Actors.carps;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.totlc.AssetList;

public class CrystalCarp extends Actor {

    public static final int HEIGHT = 256;
    public static final int WIDTH = 256;
    // Status of carp
    private final int TALKING = 0;
    private final int TURNING = 1;
    private int status;

    private TextureAtlas carpTalkAtlas, carpTurnAtlas;
    private Animation<TextureRegion> carpTalkAnimation, carpTurnAnimation;
    private float time = 0;

    private TextureAtlas currentAtlas;
    private Animation<TextureRegion> currentAnimation;

    // Time to spend talking and turning respectively in seconds.
    private float talkTime;
    private boolean doneTalking = false;
    private float animationTime = 0; // Time spent in current animation.

    // Seconds to delay until animating the tooltip. Should delay tooltip creation in constructor.
    private float delay; // in seconds

    // Seconds to display the tooltip for
    private float duration; // in seconds


    private float scale;

    // Optional sound to play when the carp talks.
    private Sound sound;

    public CrystalCarp(AssetManager assetManager, float x, float y, float delay, float talkTime, float scale) {
        setX(x);
        setY(y);

        this.duration = Math.max(duration, delay + talkTime);
        this.talkTime = talkTime;
        this.delay = delay;
        this.scale = scale;

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
                    setDoneTalking(true);
                    animationTime = 0;
                    currentAnimation = carpTurnAnimation;
                    currentAnimation.setPlayMode(Animation.PlayMode.REVERSED);
                    currentAtlas = carpTurnAtlas;
                    status = (status + 1) % 2;
                }

            } else if (status == TURNING) {
                if (currentAnimation.isAnimationFinished(animationTime) && !isDoneTalking()) {
                    animationTime = 0;

                    if (currentAnimation.getPlayMode().equals(Animation.PlayMode.REVERSED)) {
                        currentAnimation.setPlayMode(Animation.PlayMode.NORMAL);
                    } else {
                        currentAnimation = carpTalkAnimation;
                        currentAtlas = carpTalkAtlas;
                        status = (status + 1) % 2;

                        if (sound != null) {
                            sound.play();
                        }
                    }
                }
            }
            // Stop commenting out here.
        }

    }

    @Override
    public void draw(Batch batch, float alpha){
        // Draw carp.
        batch.draw(currentAnimation.getKeyFrame(animationTime),
                getX(),
                getY(),
                currentAtlas.getRegions().get(0).getRegionWidth() / 2,
                currentAtlas.getRegions().get(0).getRegionHeight() / 2,
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

    public void setVoice(String soundName) {
        this.sound = Gdx.audio.newSound(Gdx.files.internal(soundName));
    }
}
