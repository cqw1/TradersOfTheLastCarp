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
import com.totlc.Actors.triggers.ATrigger;
import com.totlc.Actors.triggers.ButtonTrigger;
import com.totlc.Actors.triggers.TeleportPad;
import com.totlc.Actors.triggers.TriggerFactory;
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
        setNextLevel(SpikeLevel.class);
    }

    public void initOtherLevelStuff() {
        setPlayer(TradersOfTheLastCarp.player);
        setNameString("SandBox");

        TradersOfTheLastCarp.musicPlayer.setSong("test0");
        TradersOfTheLastCarp.musicPlayer.play();


//        AEnemy wallface = new WallfacedProtector(getAssetManager(), 500, 500, new BasicMovement(getPlayer()));
//        addActor(wallface);

        ATrap arrow = new ArrowTrap(getAssetManager(), 500, 300);
        addActor(arrow);

        ATrap boulder = new BoulderTrap(getAssetManager(), 1200, 300);
        addActor(boulder);

        ExitPortal wormhole = new ExitPortal(getAssetManager(), 100, 100);
        addActor(wormhole);

        ATrigger trigger0 = new ButtonTrigger(getAssetManager(), 1000, 300);
        ATrigger trigger1 = new ButtonTrigger(getAssetManager(), 100, 300);
        trigger0.addTrap(arrow);
        trigger0.addTrap(boulder);
        addActor(trigger0);
        trigger1.addTrap(arrow);
        trigger1.addTrap(boulder);
        addActor(trigger1);

        endInit();
    }
}
