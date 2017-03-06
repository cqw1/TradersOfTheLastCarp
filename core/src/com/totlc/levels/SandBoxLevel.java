package com.totlc.levels;

import com.badlogic.gdx.assets.AssetManager;
import com.totlc.Actors.enemies.AEnemy;
import com.totlc.Actors.enemies.GelatinKing;
import com.totlc.Actors.enemies.JustDessert;
import com.totlc.Actors.enemies.movement.ProximityBasedAggro;
import com.totlc.Actors.enemies.movement.RandomMovement;
import com.totlc.Actors.items.APickup;
import com.totlc.Actors.items.Goldfish;
import com.totlc.Actors.items.Key;
import com.totlc.Actors.terrain.AWall;
import com.totlc.Actors.terrain.Rock;
import com.totlc.Actors.traps.ATrap;
import com.totlc.Actors.traps.FireTrap;
import com.totlc.Actors.traps.Teleporter;
import com.totlc.Actors.traps.TrapFactory;
import com.totlc.Actors.triggers.ATrigger;
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

        AWall rock = new Rock(getAssetManager(), 800, 300, 1);
        AWall rock1 = new Rock(getAssetManager(), 500, 300, 0.5f);
        AWall rock2= new Rock(getAssetManager(), 1000, 300, 0.8f);
        addActor(rock);
        addActor(rock1);
        addActor(rock2);

        AEnemy flan = new JustDessert(getAssetManager(), 300, 300, 1, new ProximityBasedAggro(getPlayer()));
        AEnemy flan1 = new JustDessert(getAssetManager(), 300, 300, 3, new ProximityBasedAggro(getPlayer()));
        AEnemy flan2 = new JustDessert(getAssetManager(), 300, 300, 7, new ProximityBasedAggro(getPlayer()));
        addActor(flan);
        addActor(flan1);
        addActor(flan2);

        endInit();
    }
}
