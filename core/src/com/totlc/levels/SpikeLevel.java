package com.totlc.levels;

import com.badlogic.gdx.assets.AssetManager;
import com.totlc.Actors.traps.ATrap;
import com.totlc.Actors.traps.TrapFactory;
import com.totlc.Actors.triggers.ATrigger;
import com.totlc.Actors.triggers.ButtonTrigger;
import com.totlc.TradersOfTheLastCarp;

public class SpikeLevel extends ALevel {
    public SpikeLevel(AssetManager assetManager) {
        super(assetManager, ObjectiveVerifier.Objectives.SURVIVE);
        setNextLevel(TeleporterLevel.class);
    }

    @Override
    public void initLevel() {
        setPlayer(TradersOfTheLastCarp.player);
        setNameString("Spike Traps");

        ATrigger btnTrigger = new ButtonTrigger(getAssetManager(), 500, 500);
        ATrap spikeTrap = TrapFactory.createTrap(TrapFactory.SPIKE_TRAP, getAssetManager(), 900, 500);
        btnTrigger.addTrap(spikeTrap);

        addActor(btnTrigger);
        addActor(spikeTrap);

        endInit();
    }
}
