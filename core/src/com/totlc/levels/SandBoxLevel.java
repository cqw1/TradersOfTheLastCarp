package com.totlc.levels;

import com.badlogic.gdx.assets.AssetManager;
import com.totlc.Actors.enemies.Spider;
import com.totlc.Actors.enemies.Stargazer;
import com.totlc.Actors.enemies.movement.BasicMovement;
import com.totlc.Actors.enemies.movement.IntervalMovement;
import com.totlc.Actors.tileset.BasicTileSet;
import com.totlc.Actors.traps.ArrowTrap;
import com.totlc.Actors.triggers.ButtonTrigger;
import com.totlc.TradersOfTheLastCarp;
import com.totlc.Actors.Player;
import com.totlc.levels.ObjectiveVerifier.*;

public class SandBoxLevel extends ALevel{

    public SandBoxLevel(Player player, AssetManager assetManager) {
        super(player, assetManager, Objectives.DESTROY);
    }

    public void initLevel(Player player) {
        super.initLevel(player);
        TradersOfTheLastCarp.musicPlayer.stop();
        TradersOfTheLastCarp.musicPlayer.setSong("test0");
        TradersOfTheLastCarp.musicPlayer.play();

        setNameString("TEST LEVEL");

        BasicTileSet bts = new BasicTileSet(getAssetManager());
        addActor(bts);

        //REQUIRED
        setPlayer(player);

        ButtonTrigger testButton = new ButtonTrigger(getAssetManager(), 400, 300);
        ButtonTrigger testButton2 = new ButtonTrigger(getAssetManager(), 700, 200);
        ButtonTrigger testButton3 = new ButtonTrigger(getAssetManager(), 900, 600);
        ArrowTrap arrowTrap = new ArrowTrap(getAssetManager(), 700, 600, (long) 0.5);
        testButton.addTrap(arrowTrap);
        testButton2.addTrap(arrowTrap);
        testButton3.addTrap(arrowTrap);
        addActor(testButton);
        addActor(testButton2);
        addActor(testButton3);
        addActor(arrowTrap);
        
        Spider spider0 = new Spider(getAssetManager(), 0, 0, new IntervalMovement(getPlayer()));
        Spider spider1 = new Spider(getAssetManager(), TradersOfTheLastCarp.CONFIG_WIDTH / 2, 0, new IntervalMovement(getPlayer()));
        Spider spider2 = new Spider(getAssetManager(), TradersOfTheLastCarp.CONFIG_WIDTH / 3, 0, new IntervalMovement(getPlayer()));
        Stargazer stargazer = new Stargazer(getAssetManager(), TradersOfTheLastCarp.CONFIG_WIDTH / 3, TradersOfTheLastCarp.CONFIG_HEIGHT / 3, new BasicMovement(getPlayer()));
        addActor(spider0);
        addActor(spider1);
        addActor(spider2);
        addActor(stargazer);

        //REQUIRED
        endInit();
    }
}
