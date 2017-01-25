package com.totlc.levels;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.totlc.Actors.enemies.Spider;
import com.totlc.Actors.enemies.Stargazer;
import com.totlc.Actors.tileset.BasicTileSet;
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
        setNameString("TEST LEVEL");

        setObjective(objectives.DESTROY);

        BasicTileSet bts = new BasicTileSet(assetManager);
        addActor(bts);

        addActor(getPlayer());

        ButtonTrigger testButton = new ButtonTrigger(assetManager, 400, 300);
        ButtonTrigger testButton2 = new ButtonTrigger(assetManager, 700, 200);
        ButtonTrigger testButton3 = new ButtonTrigger(assetManager, 900, 600);
        ArrowTrap arrowTrap = new ArrowTrap(assetManager, 700, 600);
        testButton.addTrap(arrowTrap);
        testButton2.addTrap(arrowTrap);
        testButton3.addTrap(arrowTrap);
        addActor(testButton);
        addActor(testButton2);
        addActor(testButton3);
        addActor(arrowTrap);

        Spider spider0 = new Spider(assetManager, 0, 0);
        Spider spider1 = new Spider(assetManager, TradersOfTheLastCarp.CONFIG_WIDTH / 2, 0);
        Spider spider2 = new Spider(assetManager, TradersOfTheLastCarp.CONFIG_WIDTH / 3, 0);
        Stargazer stargazer = new Stargazer(assetManager, TradersOfTheLastCarp.CONFIG_WIDTH / 3, TradersOfTheLastCarp.CONFIG_HEIGHT / 3);
//        addActor(spider0);
//        addActor(spider1);
//        addActor(spider2);
//        addActor(stargazer);
        getPlayer().setZIndex(999);

        initUI();
    }
}
