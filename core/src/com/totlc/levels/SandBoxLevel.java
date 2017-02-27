package com.totlc.levels;

import com.badlogic.gdx.assets.AssetManager;
import com.totlc.Actors.enemies.GelatinKing;
import com.totlc.Actors.enemies.JustDessert;
import com.totlc.Actors.enemies.movement.ProximityBasedAggro;
import com.totlc.Actors.enemies.movement.RandomMovement;
import com.totlc.Actors.items.APickup;
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

        ATrigger trigger = new TeleportPad(getAssetManager(), 500, 500);
        Teleporter t = new Teleporter(getAssetManager(), 100, 100);

        trigger.addTrap(t);
        addActor(trigger);
        addActor(t);

        APickup key = new Key(getAssetManager(), 700, 700);
        addActor(key);

        AWall rock = new Rock(getAssetManager(), 800, 300);
        addActor(rock);

        endInit();
    }
}
