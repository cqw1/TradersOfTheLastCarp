package com.totlc.levels;

import com.badlogic.gdx.assets.AssetManager;
import com.totlc.AssetList;
import com.totlc.TradersOfTheLastCarp;

public class Level07 extends ALevel {

    public final String tmxFileName = AssetList.LEVEL07_TMX.toString();

    public Level07(AssetManager assetManager) {
        super(assetManager);
        setNextLevel(Level08.class);
    }

    public void initOtherLevelStuff() {
        setPlayer(TradersOfTheLastCarp.player);
        loadFromTMX(tmxFileName);
    }
}
