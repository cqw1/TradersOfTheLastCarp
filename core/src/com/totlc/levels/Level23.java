package com.totlc.levels;

import com.badlogic.gdx.assets.AssetManager;
import com.totlc.Actors.items.Goldfish;
import com.totlc.AssetList;
import com.totlc.TradersOfTheLastCarp;

public class Level23 extends ALevel {

    public final String tmxFileName = AssetList.LEVEL23_TMX.toString();

    public Level23(AssetManager assetManager) {
        super(assetManager);
        setNextLevel(EndLevel.class);
    }

    public void initOtherLevelStuff() {
        setPlayer(TradersOfTheLastCarp.player);
        loadFromTMX(tmxFileName);
    }
}
