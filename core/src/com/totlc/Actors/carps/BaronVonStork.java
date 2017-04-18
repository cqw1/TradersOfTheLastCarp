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

public class BaronVonStork extends Actor {

    public static final int HEIGHT = 256;
    public static final int WIDTH = 256;
    private int status;

    private TextureAtlas storkTalkAtlas;
    private Animation<TextureRegion> storkTalkAnimation;
    private float time = 0;

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

    public BaronVonStork(AssetManager assetManager, float x, float y, float delay, float talkTime, float scale) {
        setX(x);
        setY(y);

        setWidth(WIDTH);

        this.duration = Math.max(duration, delay + talkTime);
        this.talkTime = talkTime;
        this.delay = delay;
        this.scale = scale;

        storkTalkAtlas = assetManager.get(AssetList.VON_STORK.toString());
        storkTalkAnimation = new Animation<TextureRegion>(1 / 10f, storkTalkAtlas.getRegions(), Animation.PlayMode.LOOP);

        for (int i = 0; i < storkTalkAtlas.getRegions().size; i++){
            storkTalkAtlas.getRegions().get(i).getTexture().setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
        }
    }

    @Override
    public void act(float deltaTime) {
        // deltaTime = time in seconds since last frame
        time += deltaTime;

        if (time > delay && !isDoneTalking()) {
            animationTime += deltaTime;

            if (animationTime > talkTime) {
                setDoneTalking(true);
                animationTime = 0;
            }

            if (sound != null) {
                sound.play();
            }
        }

    }

    @Override
    public void draw(Batch batch, float alpha){
        // Draw stork.
        batch.draw(storkTalkAnimation.getKeyFrame(animationTime),
                getX(),
                getY(),
                storkTalkAtlas.getRegions().get(0).getRegionWidth() / 2,
                storkTalkAtlas.getRegions().get(0).getRegionHeight() / 2,
                (float) storkTalkAtlas.getRegions().get(0).getRegionWidth(),
                (float) storkTalkAtlas.getRegions().get(0).getRegionHeight(), scale, scale, 0);

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
