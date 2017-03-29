package com.totlc.levels;

import com.badlogic.gdx.assets.AssetManager;
import com.totlc.Actors.enemies.AEnemy;
import com.totlc.Actors.enemies.GelatinKing;
import com.totlc.Actors.enemies.JustDessert;
import com.totlc.Actors.enemies.WallfacedProtector;
import com.totlc.Actors.enemies.movement.BasicMovement;
import com.totlc.Actors.enemies.movement.ProximityBasedAggro;
import com.totlc.Actors.enemies.movement.RandomMovement;
import com.totlc.Actors.items.APickup;
import com.totlc.Actors.items.Goldfish;
import com.totlc.Actors.items.Key;
import com.totlc.Actors.terrain.AWall;
import com.totlc.Actors.terrain.Rock;
import com.totlc.Actors.traps.*;
import com.totlc.Actors.triggers.*;
import com.totlc.Directionality;
import com.totlc.TradersOfTheLastCarp;
import com.totlc.levels.ObjectiveVerifier.*;

/**
 * Order of demo levels:
 *  Sandbox
 *  Spike trap
 *  Teleporter
 *  Spider repository and spiders
 *  Stargazer
 *  Flan + arrow trap (to exhibit splitting)
 *  Flamethrowers
 *  GelatinLevel
 */

public class SandBoxLevel extends ALevel{

    public SandBoxLevel(AssetManager assetManager) {
        super(assetManager, Objectives.UNLOCK);
        setNextLevel(TitleScreen.class);
    }

    public void initOtherLevelStuff() {
        setPlayer(TradersOfTheLastCarp.player);
        setNameString("SandBox");

//        TradersOfTheLastCarp.musicPlayer.setSong("test0");
//        TradersOfTheLastCarp.musicPlayer.play();

        AEnemy wallface = new WallfacedProtector(getAssetManager(), 500, 500, new BasicMovement(getPlayer()));
        addActor(wallface);

        ATrap boulder = new BoulderTrap(getAssetManager(), 1200, 300);
        addActor(boulder);

        EntrancePortal enter = new EntrancePortal(getAssetManager(), 500, 300);

        ExitPortal wormhole = new ExitPortal(getAssetManager(), 900, 100);
        addActor(wormhole);

        enter.addTrap(wormhole);
        addActor(enter);

        ATrigger trigger0 = new ButtonTrigger(getAssetManager(), 1000, 300);
        ATrigger trigger1 = new ButtonTrigger(getAssetManager(), 100, 300);
        trigger0.addTrap(boulder);
        addActor(trigger0);
        trigger1.addTrap(boulder);
        addActor(trigger1);

        endInit();
    }
}
