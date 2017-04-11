package com.totlc.levels;

import com.badlogic.gdx.assets.AssetManager;
import com.totlc.AssetList;
import com.totlc.TradersOfTheLastCarp;

public class Level21 extends ALevel {

    public final String tmxFileName = AssetList.LEVEL21_TMX.toString();

    public Level21(AssetManager assetManager) {
        super(assetManager);
        setNextLevel(EndLevel.class);
    }

    public void initOtherLevelStuff() {
        setPlayer(TradersOfTheLastCarp.player);
        loadFromTMX(tmxFileName);
    }
}
