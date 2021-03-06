package com.totlc.levels;

import com.badlogic.gdx.assets.AssetManager;
import com.totlc.AssetList;
import com.totlc.TradersOfTheLastCarp;

public class Level03 extends ALevel {

    public final String tmxFileName = AssetList.LEVEL03_TMX.toString();

    public Level03(AssetManager assetManager) {
        super(assetManager);
        setNextLevel(Level04.class);
    }

    public void initOtherLevelStuff() {
        setPlayer(TradersOfTheLastCarp.player);
        loadFromTMX(tmxFileName);
    }
}
