package com.totlc.Actors.UI;

import com.badlogic.gdx.assets.AssetManager;
import com.totlc.Actors.carps.BaronVonStork;
import com.totlc.AssetList;

public class StorkTextBox extends TextBox {
    private float scale = 0.5f;

    public StorkTextBox(AssetManager assetManager, String message) {
        this(assetManager, message, 0, 5);
    }

    public StorkTextBox(AssetManager assetManager, String message, float delay, float duration) {
        super(assetManager, message, delay, duration, null);

        BaronVonStork stork = new BaronVonStork(assetManager,
                getX() + getWidth() - (BaronVonStork.WIDTH + 20),
                getY() + getHeight() / 2.0f - (BaronVonStork.HEIGHT / 2.0f) - 10,
                delay,
                getTalkTime(),
                scale
        );
        setSpeaker(stork);
        setSound(AssetList.DEEPQUACKREPEAT.toString());
        setSoundVolume(1.0f);
    }
}
