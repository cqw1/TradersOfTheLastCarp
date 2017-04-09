package com.totlc.levels;

import com.badlogic.gdx.assets.AssetManager;
import com.totlc.AssetList;
import com.totlc.TradersOfTheLastCarp;

/**
 * Created by Bao Vu on 4/9/2017.
 */
public class Level17 extends ALevel {
    public final String tmxFileName = AssetList.LEVEL17_TMX.toString();

    public Level17(AssetManager assetManager) {
        super(assetManager);
        setNextLevel(Level18.class);
    }

    public void initOtherLevelStuff() {
        setPlayer(TradersOfTheLastCarp.player);
        loadFromTMX(tmxFileName);
    }
}
