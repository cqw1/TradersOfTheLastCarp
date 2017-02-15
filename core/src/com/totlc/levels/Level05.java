package com.totlc.levels;

import com.badlogic.gdx.assets.AssetManager;
import com.totlc.AssetList;
import com.totlc.TradersOfTheLastCarp;

public class Level05 extends ALevel {

    public final String tmxFileName = AssetList.LEVEL05_TMX.toString();

    public Level05(AssetManager assetManager) {
        super(assetManager);
        setNextLevel(Level06.class);
    }

    public void initOtherLevelStuff() {
        setPlayer(TradersOfTheLastCarp.player);
        loadFromTMX(tmxFileName);
    }
}
