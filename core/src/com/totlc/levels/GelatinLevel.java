package com.totlc.levels;

import com.badlogic.gdx.assets.AssetManager;
import com.totlc.Actors.traps.ATrap;
import com.totlc.Actors.traps.FireTrap;
import com.totlc.Actors.triggers.ATrigger;
import com.totlc.Actors.triggers.TriggerFactory;
import com.totlc.TradersOfTheLastCarp;

/**
 * Created by cwang on 2/12/17.
 */
public class GelatinLevel extends ALevel {

    public GelatinLevel(AssetManager assetManager) {
        super(assetManager, ObjectiveVerifier.Objectives.SURVIVE);
        setNextLevel(TitleScreen.class);
    }

    @Override
    public void initLevel() {
        setPlayer(TradersOfTheLastCarp.player);
        setNameString("gelatin");

        endInit();
    }
}
