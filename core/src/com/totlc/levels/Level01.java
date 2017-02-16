package com.totlc.levels;

import com.badlogic.gdx.assets.AssetManager;
import com.totlc.AssetList;
import com.totlc.TradersOfTheLastCarp;
import com.totlc.levels.ObjectiveVerifier.*;

public class Level01 extends ALevel {

    public final String tmxFileName = AssetList.LEVEL01_TMX.toString();

    public Level01(AssetManager assetManager) {
        super(assetManager);
        setNextLevel(Level02.class);
    }

    public static ALevel make(AssetManager assetManager) {
        return new Level01(assetManager);
    }

    public void initOtherLevelStuff() {
        setPlayer(TradersOfTheLastCarp.player);
        restorePlayerHealth();
        loadFromTMX(tmxFileName);
    }
}
