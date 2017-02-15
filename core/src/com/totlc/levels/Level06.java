package com.totlc.levels;

import com.badlogic.gdx.assets.AssetManager;
import com.totlc.AssetList;
import com.totlc.TradersOfTheLastCarp;

public class Level06 extends ALevel {

    public final String tmxFileName = AssetList.LEVEL06_TMX.toString();

    public Level06(AssetManager assetManager) {
        super(assetManager);
        setNextLevel(Level07.class);
    }

    public void initOtherLevelStuff() {
        setPlayer(TradersOfTheLastCarp.player);
        loadFromTMX(tmxFileName);
    }
}
