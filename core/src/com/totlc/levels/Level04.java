package com.totlc.levels;

import com.badlogic.gdx.assets.AssetManager;
import com.totlc.AssetList;
import com.totlc.TradersOfTheLastCarp;

public class Level04 extends ALevel {
    public final String tmxFileName = AssetList.LEVEL04_TMX.toString();

    public Level04(AssetManager assetManager) {
        super(assetManager);
        setNextLevel(Level05.class);
    }

    public void initOtherLevelStuff() {
        setPlayer(TradersOfTheLastCarp.player);
        loadFromTMX(tmxFileName);
    }
}
