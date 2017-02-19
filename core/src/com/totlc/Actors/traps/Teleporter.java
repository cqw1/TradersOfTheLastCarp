package com.totlc.Actors.traps;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.math.Rectangle;
import com.totlc.Actors.effects.TeleportBeam;
import com.totlc.TradersOfTheLastCarp;

public class Teleporter extends ATrap {

    private static long delay = 250;

    public Teleporter(AssetManager assetManager, float x, float y) {
        super(assetManager, new Rectangle(x, y, 64, 64), delay);
    }

    @Override
    public void activate() {
        TradersOfTheLastCarp.player.moveAbs(getX() - TradersOfTheLastCarp.player.getWidth() * 0.5f, getY());
        getStage().addActor(new TeleportBeam(getAssetManager(), (float)getHitBoxCenter().getX(), getY()));
        Sound sound = Gdx.audio.newSound(Gdx.files.internal("sounds/laser0.mp3"));
        sound.play(1.0f);
    }
}
