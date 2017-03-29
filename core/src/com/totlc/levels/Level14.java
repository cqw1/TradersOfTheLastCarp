package com.totlc.levels;

import com.badlogic.gdx.assets.AssetManager;
import com.totlc.AssetList;
import com.totlc.TradersOfTheLastCarp;

public class Level14 extends ALevel {

    public final String tmxFileName = AssetList.LEVEL14_TMX.toString();

    public Level14(AssetManager assetManager) {
        super(assetManager);
        setNextLevel(Level15.class);
    }

    public void initOtherLevelStuff() {
        setPlayer(TradersOfTheLastCarp.player);
        loadFromTMX(tmxFileName);
    }
}
