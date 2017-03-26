package com.totlc.levels;

import com.badlogic.gdx.assets.AssetManager;
import com.totlc.AssetList;
import com.totlc.TradersOfTheLastCarp;

/**
 * Created by Bao Vu on 3/26/2017.
 */
public class Level12 extends ALevel {

    public final String tmxFileName = AssetList.LEVEL12_TMX.toString();

    public Level12(AssetManager assetManager) {
        super(assetManager);
        setNextLevel(Level13.class);
    }

    public void initOtherLevelStuff() {
        setPlayer(TradersOfTheLastCarp.player);
        loadFromTMX(tmxFileName);
    }
}
