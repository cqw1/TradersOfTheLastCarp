package com.totlc.levels;

import com.badlogic.gdx.assets.AssetManager;
import com.totlc.AssetList;
import com.totlc.TradersOfTheLastCarp;

public class Level11 extends ALevel {

    public final String tmxFileName = AssetList.LEVEL11_TMX.toString();

    public Level11(AssetManager assetManager) {
        super(assetManager);
        setNextLevel(Level12.class);
    }

    public void initOtherLevelStuff() {
        setPlayer(TradersOfTheLastCarp.player);
        loadFromTMX(tmxFileName);
    }
}
