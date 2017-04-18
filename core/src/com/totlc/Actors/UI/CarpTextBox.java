package com.totlc.Actors.UI;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Sound;
import com.totlc.Actors.carps.CrystalCarp;
import com.totlc.AssetList;

public class CarpTextBox extends TextBox {

    private float scale = 0.9f;

    public CarpTextBox(AssetManager assetManager, String message) {
        this(assetManager, message, 0, 5);
    }

    public CarpTextBox(AssetManager assetManager, String message, float delay, float duration) {
        super(assetManager, message, delay, duration, null);


        CrystalCarp carp = new CrystalCarp(assetManager,
                getX() + getWidth() - (CrystalCarp.WIDTH + 20),
                getY() + getHeight() / 2.0f - (CrystalCarp.HEIGHT / 2.0f * scale) - 13,
                delay,
                getTalkTime(),
                scale
        );
        setSpeaker(carp);
        setSound(AssetList.BUBBLES.toString());
        setSoundVolume(0.8f);
    }
}
