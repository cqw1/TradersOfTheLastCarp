package com.totlc.levels;

import com.badlogic.gdx.assets.AssetManager;
import com.totlc.Actors.traps.ATrap;
import com.totlc.Actors.traps.FireTrap;
import com.totlc.Actors.triggers.ATrigger;
import com.totlc.Actors.triggers.TriggerFactory;
import com.totlc.TradersOfTheLastCarp;

public class FlameLevel extends ALevel {

    public FlameLevel(AssetManager assetManager) {
        super(assetManager, ObjectiveVerifier.Objectives.SURVIVE);
        setNextLevel(GelatinLevel.class);
    }

    @Override
    public void initOtherLevelStuff() {
        setPlayer(TradersOfTheLastCarp.player);
        setNameString("flame");

        ATrap trap = new FireTrap(getAssetManager(), 500, 500, 800, 200);
        addActor(trap);

        ATrigger trigger = TriggerFactory.createTrigger(TriggerFactory.BUTTON, getAssetManager(), 300, 500);
        trigger.addTrap(trap);
        addActor(trigger);

        endInit();
    }
}
