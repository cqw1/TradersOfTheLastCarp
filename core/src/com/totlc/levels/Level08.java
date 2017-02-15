package com.totlc.levels;

import com.badlogic.gdx.assets.AssetManager;
import com.totlc.AssetList;
import com.totlc.TradersOfTheLastCarp;

public class Level08 extends ALevel {

    public final String tmxFileName = AssetList.LEVEL08_TMX.toString();

    public Level08(AssetManager assetManager) {
        super(assetManager);
        setNextLevel(Level09.class);
    }

    public void initOtherLevelStuff() {
        setPlayer(TradersOfTheLastCarp.player);
        loadFromTMX(tmxFileName);
    }
}
