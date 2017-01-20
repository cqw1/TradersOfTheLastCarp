package com.totlc.levels;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.totlc.Actors.enemies.Spider;
import com.totlc.Actors.enemies.Stargazer;
import com.totlc.Actors.traps.ArrowTrap;
import com.totlc.Actors.triggers.ButtonTrigger;
import com.totlc.TradersOfTheLastCarp;
import com.totlc.Actors.Player;
import com.totlc.Actors.UI.LifeGauge;
import com.totlc.audio.MusicPlayer;

public class Level01 extends ALevel{

    public Level01(Player player, AssetManager assetManager) {
        super(player, assetManager);
        playSong("test0");

        ButtonTrigger testButton = new ButtonTrigger(assetManager, "dummy/0.png", TradersOfTheLastCarp.CONFIG_WIDTH/2, TradersOfTheLastCarp.CONFIG_HEIGHT/2);
        ArrowTrap arrowTrap = new ArrowTrap(assetManager, 700, 300);
        testButton.addTrap(arrowTrap);
        addActor(testButton);
        addActor(arrowTrap);

        Spider spider0 = new Spider(assetManager, 0, 0);
        Spider spider1 = new Spider(assetManager, 0, 0);
        Spider spider2 = new Spider(assetManager, 0, 0);
        Stargazer stargazer = new Stargazer(assetManager, TradersOfTheLastCarp.CONFIG_WIDTH / 3, TradersOfTheLastCarp.CONFIG_HEIGHT / 3);
        addActor(spider0);
        addActor(spider1);
        addActor(spider2);
        addActor(stargazer);


        LifeGauge hpBar = new LifeGauge(player, 0, TradersOfTheLastCarp.CONFIG_HEIGHT);
        hpBar.moveBy(0, -hpBar.getHeight());
        addActor(hpBar);
    }
}
