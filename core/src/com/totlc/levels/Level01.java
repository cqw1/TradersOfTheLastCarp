package com.totlc.levels;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.totlc.Actors.triggers.ButtonTrigger;
import com.totlc.TradersOfTheLastCarp;
import com.totlc.Actors.Player;
import com.totlc.Actors.UI.LifeGauge;
import com.totlc.audio.MusicPlayer;

public class Level01 extends ALevel{

    public Level01(Player player, AssetManager assetManager) {
        super(player, assetManager);
        playSong("test1");

        ButtonTrigger testButton = new ButtonTrigger(assetManager, "dummy/0.png", TradersOfTheLastCarp.CONFIG_WIDTH/2, TradersOfTheLastCarp.CONFIG_HEIGHT/2);
        addActor(testButton);

        LifeGauge hpBar = new LifeGauge(player, 0, TradersOfTheLastCarp.CONFIG_HEIGHT);
        hpBar.moveBy(0, -hpBar.getHeight());
        addActor(hpBar);
    }
}
