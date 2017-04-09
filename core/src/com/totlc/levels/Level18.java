package com.totlc.levels;

import com.badlogic.gdx.assets.AssetManager;
import com.totlc.AssetList;
import com.totlc.TradersOfTheLastCarp;

public class Level18 extends ALevel {

    public final String tmxFileName = AssetList.LEVEL18_TMX.toString();

    public Level18(AssetManager assetManager) {
        super(assetManager);
        setNextLevel(Level19.class);
    }

    public void initOtherLevelStuff() {
        setPlayer(TradersOfTheLastCarp.player);
        loadFromTMX(tmxFileName);
    }
}
