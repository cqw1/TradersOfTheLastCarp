package com.totlc.levels;

import com.badlogic.gdx.assets.AssetManager;
import com.totlc.Actors.enemies.JustDessert;
import com.totlc.Actors.enemies.movement.ProximityBasedAggro;
import com.totlc.Actors.traps.ATrap;
import com.totlc.Actors.traps.TrapFactory;
import com.totlc.Actors.triggers.ATrigger;
import com.totlc.Actors.triggers.TriggerFactory;
import com.totlc.TradersOfTheLastCarp;
import com.totlc.levels.ObjectiveVerifier.*;

public class SandBoxLevel extends ALevel{

    public SandBoxLevel(AssetManager assetManager) {
        super(assetManager, Objectives.DESTROY);
        setNextLevel(TitleScreen.class);
    }

    public void initLevel() {
        setPlayer(TradersOfTheLastCarp.player);
        setNameString("SandBox");

        TradersOfTheLastCarp.musicPlayer.setSong("test0");
        TradersOfTheLastCarp.musicPlayer.play();

        ATrap atrap = TrapFactory.createTrap(TrapFactory.SPIDER_TRAP, getAssetManager(), 700, 400);
        addActor(atrap);

        ATrigger bt = TriggerFactory.createTrigger(TriggerFactory.BUTTON, getAssetManager(), 300, 300);
        bt.addTrap(atrap);
        addActor(bt);

        JustDessert j = new JustDessert(getAssetManager(), 200, 450, 3, new ProximityBasedAggro(getPlayer()));
        addActor(j);

        JustDessert j3 = new JustDessert(getAssetManager(), 200, 450, 7, new ProximityBasedAggro(getPlayer()));
        addActor(j3);

        endInit();
    }
}
