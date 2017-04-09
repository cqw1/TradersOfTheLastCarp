package com.totlc.levels;

import com.badlogic.gdx.assets.AssetManager;
import com.totlc.AssetList;
import com.totlc.TradersOfTheLastCarp;

public class Level15 extends ALevel {

    public final String tmxFileName = AssetList.LEVEL15_TMX.toString();

    public Level15(AssetManager assetManager) {
        super(assetManager);
        setNextLevel(Level16.class);
    }

    public void initOtherLevelStuff() {
        setPlayer(TradersOfTheLastCarp.player);
        loadFromTMX(tmxFileName);
    }

}
