package com.totlc.levels;

import com.badlogic.gdx.assets.AssetManager;
import com.totlc.AssetList;
import com.totlc.TradersOfTheLastCarp;

public class Level02 extends ALevel {

    public final String tmxFileName = AssetList.LEVEL02_TMX.toString();

    public Level02(AssetManager assetManager) {
        super(assetManager);
        setNextLevel(Level03.class);
    }

    public void initOtherLevelStuff() {
        setPlayer(TradersOfTheLastCarp.player);
        loadFromTMX(tmxFileName);
    }
}
