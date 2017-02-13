package com.totlc.levels;

import com.badlogic.gdx.assets.AssetManager;
import com.totlc.Actors.traps.ATrap;
import com.totlc.Actors.traps.TrapFactory;
import com.totlc.Actors.triggers.ATrigger;
import com.totlc.Actors.triggers.TriggerFactory;
import com.totlc.TradersOfTheLastCarp;

public class SpiderLevel extends ALevel {
    public SpiderLevel(AssetManager assetManager) {
        super(assetManager, ObjectiveVerifier.Objectives.SURVIVE);
        setNextLevel(StargazerLevel.class);
    }

    public void initLevel() {
        setPlayer(TradersOfTheLastCarp.player);
        setNameString("Spiders");

        ATrigger bt = TriggerFactory.createTrigger(TriggerFactory.BUTTON, getAssetManager(), 300, 300);
        ATrap atrap = TrapFactory.createTrap(TrapFactory.SPIDER_TRAP, getAssetManager(), 700, 400);
        bt.addTrap(atrap);

        addActor(bt);
        addActor(atrap);

        endInit();
    }
}
