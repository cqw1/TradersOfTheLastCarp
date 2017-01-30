package com.totlc.levels;

import com.badlogic.gdx.assets.AssetManager;
import com.totlc.Actors.enemies.Spider;
import com.totlc.Actors.enemies.Stargazer;
import com.totlc.Actors.terrain.NextStage;
import com.totlc.Actors.tileset.BasicTileSet;
import com.totlc.Actors.traps.ArrowTrap;
import com.totlc.Actors.triggers.ButtonTrigger;
import com.totlc.TradersOfTheLastCarp;
import com.totlc.Actors.Player;
import com.totlc.levels.ObjectiveVerifier.*;

public class Level01 extends ALevel{

    public Level01(Player player, AssetManager assetManager) {
        super(player, assetManager,
                new NextStage(assetManager, ALevel.DEFAULT_WALLSIZE, player.getHeight()),
                new Level02(player, assetManager),
                objectives.DESTROY);
    }

    public void initLevel(Player player) {
        playSong("test0");
        setNameString("TEST LEVEL");

        BasicTileSet bts = new BasicTileSet(getAssetManager());
        addActor(bts);

        //REQUIRED
        setPlayer(player);

        ButtonTrigger testButton = new ButtonTrigger(getAssetManager(), 400, 300);
        ButtonTrigger testButton2 = new ButtonTrigger(getAssetManager(), 700, 200);
        ButtonTrigger testButton3 = new ButtonTrigger(getAssetManager(), 900, 600);
        ArrowTrap arrowTrap = new ArrowTrap(getAssetManager(), 700, 600);
        testButton.addTrap(arrowTrap);
        testButton2.addTrap(arrowTrap);
        testButton3.addTrap(arrowTrap);
        addActor(testButton);
        addActor(testButton2);
        addActor(testButton3);
        addActor(arrowTrap);

        Spider spider0 = new Spider(getAssetManager(), 0, 0);
        Spider spider1 = new Spider(getAssetManager(), TradersOfTheLastCarp.CONFIG_WIDTH / 2, 0);
        Spider spider2 = new Spider(getAssetManager(), TradersOfTheLastCarp.CONFIG_WIDTH / 3, 0);
        Stargazer stargazer = new Stargazer(getAssetManager(), TradersOfTheLastCarp.CONFIG_WIDTH / 3, TradersOfTheLastCarp.CONFIG_HEIGHT / 3);
        addActor(spider0);
        addActor(spider1);
        addActor(spider2);
        addActor(stargazer);

        //REQUIRED
        endInit();
    }
}
