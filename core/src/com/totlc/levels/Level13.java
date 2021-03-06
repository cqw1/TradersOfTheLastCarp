package com.totlc.levels;

import com.badlogic.gdx.assets.AssetManager;
import com.totlc.AssetList;
import com.totlc.TradersOfTheLastCarp;

public class Level13 extends ALevel {

    public final String tmxFileName = AssetList.LEVEL13_TMX.toString();

    public Level13(AssetManager assetManager) {
        super(assetManager);
        setNextLevel(Level14.class);
    }

    public void initOtherLevelStuff() {
        setPlayer(TradersOfTheLastCarp.player);
        loadFromTMX(tmxFileName);
    }
}
