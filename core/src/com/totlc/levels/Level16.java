package com.totlc.levels;

import com.badlogic.gdx.assets.AssetManager;
import com.totlc.AssetList;
import com.totlc.TradersOfTheLastCarp;

public class Level16 extends ALevel {

    public final String tmxFileName = AssetList.LEVEL16_TMX.toString();

    public Level16(AssetManager assetManager) {
        super(assetManager);
        setNextLevel(EndLevel.class);
    }

    public void initOtherLevelStuff() {
        setPlayer(TradersOfTheLastCarp.player);
        loadFromTMX(tmxFileName);
    }
}
