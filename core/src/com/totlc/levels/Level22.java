package com.totlc.levels;

import com.badlogic.gdx.assets.AssetManager;
import com.totlc.AssetList;
import com.totlc.TradersOfTheLastCarp;

public class Level22 extends ALevel {

    public final String tmxFileName = AssetList.LEVEL22_TMX.toString();

    public Level22(AssetManager assetManager) {
        super(assetManager);
        setNextLevel(Level23.class);
    }

    public void initOtherLevelStuff() {
        setPlayer(TradersOfTheLastCarp.player);
        loadFromTMX(tmxFileName);
    }
}
