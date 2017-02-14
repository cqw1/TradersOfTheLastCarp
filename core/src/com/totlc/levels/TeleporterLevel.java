package com.totlc.levels;

import com.badlogic.gdx.assets.AssetManager;
import com.totlc.Actors.traps.ATrap;
import com.totlc.Actors.traps.TrapFactory;
import com.totlc.Actors.triggers.ATrigger;
import com.totlc.Actors.triggers.ButtonTrigger;
import com.totlc.TradersOfTheLastCarp;

public class TeleporterLevel extends ALevel {

    public TeleporterLevel(AssetManager assetManager) {
        super(assetManager, ObjectiveVerifier.Objectives.SURVIVE);
        setNextLevel(SpiderLevel.class);
    }

    @Override
    public void initOtherLevelStuff() {
        setPlayer(TradersOfTheLastCarp.player);
        setNameString("Teleporter level");

        ATrigger btnTrigger = new ButtonTrigger(getAssetManager(), 500, 500);
        ATrap teleTrap = TrapFactory.createTrap(TrapFactory.TELEPORTER, getAssetManager(), 300, 200);
        btnTrigger.addTrap(teleTrap);

        addActor(btnTrigger);
        addActor(teleTrap);

        endInit();
    }
}
