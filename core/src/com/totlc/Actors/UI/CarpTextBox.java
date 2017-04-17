package com.totlc.Actors.UI;

import com.badlogic.gdx.assets.AssetManager;
import com.totlc.Actors.carps.CrystalCarp;

public class CarpTextBox extends TextBox {

    public CarpTextBox(AssetManager assetManager, String message) {
        this(assetManager, message, 0, 5);
    }

    public CarpTextBox(AssetManager assetManager, String message, float delay, float duration) {
        super(assetManager, message, delay, duration, null);

        CrystalCarp carp = new CrystalCarp(assetManager,
                getX() + getWidth() - (CrystalCarp.WIDTH + 20),
                getY() + getHeight() / 2.0f - (CrystalCarp.HEIGHT / 2.0f * getScale()) - 13,
                delay,
                getTalkTime(),
                getScale()
        );
        setSpeaker(carp);
    }
}
