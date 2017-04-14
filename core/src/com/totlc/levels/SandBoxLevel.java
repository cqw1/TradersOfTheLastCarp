package com.totlc.levels;

import com.badlogic.gdx.assets.AssetManager;
import com.totlc.Actors.enemies.*;
import com.totlc.Actors.enemies.movement.*;
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

        TradersOfTheLastCarp.musicPlayer.stop();
        TradersOfTheLastCarp.musicPlayer.setSong("test0");
        TradersOfTheLastCarp.musicPlayer.getCurrentSong().setLooping(true);
        TradersOfTheLastCarp.musicPlayer.play();

//        ATrap boulder = new BoulderTrap(getAssetManager(), 300, 500);
//        addActor(boulder);

//        EntrancePortal enter = new EntrancePortal(getAssetManager(), 500, 300);
//
//        ExitPortal wormhole = new ExitPortal(getAssetManager(), 100, 600, Directionality.RIGHT);
//        addActor(wormhole);
//
//        enter.addTrap(wormhole);
//        addActor(enter);

//        ATrigger trigger0 = new ButtonTrigger(getAssetManager(), 1000, 300);
//        ATrigger trigger1 = new ButtonTrigger(getAssetManager(), 100, 300);
//        trigger0.addTrap(boulder);
//        addActor(trigger0);
//        trigger1.addTrap(boulder);
//        addActor(trigger1);

//        AEnemy wallface = new WallfacedProtector(getAssetManager(), 500, 500, new BasicMovement(getPlayer()));
//        addActor(wallface);

//        ATrap laser = new LaserTotem(getAssetManager(), 500, 500, 1000, 90);
//        addActor(laser);
//        trigger0.addTrap(laser);

//        AEnemy fisherman = new EnthralledFisherman(getAssetManager(), 200, 200, new BasicMovement(getPlayer()));
//        addActor(fisherman);
//
//        AEnemy harpooner = new EnthralledHarpooner(getAssetManager(), 700, 700, new BasicMovement(getPlayer()));
//        addActor(harpooner);
//

//        AEnemy chaperone = new FellChaperone(getAssetManager(), 700, 300, new ZoningMovement(getPlayer()));
//        addActor(chaperone);
//
//        AEnemy pangolini = new DaredevilPangolini(getAssetManager(), 900, 100, new BasicMovement(getPlayer()));
//        addActor(pangolini);

        AWall rock = new Rock(getAssetManager(), 300, 300);
        addActor(rock);

        AEnemy stork0 = new StorkTrooper(getAssetManager(), 900, 100, new IntervalMovement(getPlayer(), 600, 200));
        addActor(stork0);

        AEnemy stork1 = new StorkTrooper(getAssetManager(), 900, 300, new IntervalMovement(getPlayer(), 600, 200));
        addActor(stork1);

        AEnemy stork2 = new StorkTrooper(getAssetManager(), 900, 500, new IntervalMovement(getPlayer(), 600, 200));
        addActor(stork2);
//
//        AEnemy stork3 = new StorkTrooper(getAssetManager(), 1200, 500, new AvoidantMovement(getPlayer()));
//        addActor(stork3);
//
//        AEnemy stork4 = new StorkTrooper(getAssetManager(), 1200, 300, new AvoidantMovement(getPlayer()));
//        addActor(stork4);

        AEnemy hstork = new HeavyStorkTrooper(getAssetManager(), 1000, 500, new IntervalMovement(getPlayer(), 2000, 1000));
        addActor(hstork);

        endInit();
    }
}
