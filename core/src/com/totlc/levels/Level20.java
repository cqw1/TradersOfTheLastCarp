package com.totlc.levels;

import com.badlogic.gdx.assets.AssetManager;
import com.totlc.AssetList;
import com.totlc.TradersOfTheLastCarp;

public class Level20 extends ALevel {

    public final String tmxFileName = AssetList.LEVEL20_TMX.toString();

    public Level20(AssetManager assetManager) {
        super(assetManager);
        setNextLevel(Level21.class);
    }

    public void initOtherLevelStuff() {
        setPlayer(TradersOfTheLastCarp.player);
        loadFromTMX(tmxFileName);
    }
}
