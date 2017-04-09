package com.totlc.levels;

import com.badlogic.gdx.assets.AssetManager;
import com.totlc.AssetList;
import com.totlc.TradersOfTheLastCarp;

public class Level19 extends ALevel {

    public final String tmxFileName = AssetList.LEVEL19_TMX.toString();

    public Level19(AssetManager assetManager) {
        super(assetManager);
        setNextLevel(Level20.class);
    }

    public void initOtherLevelStuff() {
        setPlayer(TradersOfTheLastCarp.player);
        loadFromTMX(tmxFileName);
    }
}
