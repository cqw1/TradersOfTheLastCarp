package com.totlc.Actors.UI;

import com.badlogic.gdx.assets.AssetManager;
import com.totlc.Actors.carps.BaronVonStork;
import com.totlc.AssetList;

public class StorkTextBox extends TextBox {
    private float scale = 1.0f;

    public StorkTextBox(AssetManager assetManager, String message) {
        this(assetManager, message, 0, 5);
    }

    public StorkTextBox(AssetManager assetManager, String message, float delay, float duration) {
        super(assetManager, message, delay, duration, null);

        BaronVonStork stork = new BaronVonStork(assetManager,
                getX() + getWidth() - (BaronVonStork.WIDTH + 20),
                getY() + 1,
                delay,
                getTalkTime(),
                scale
        );
        setSpeaker(stork);
        setSound(AssetList.DEEPQUACKREPEAT.toString());
        setSoundVolume(1.0f);
    }
}
