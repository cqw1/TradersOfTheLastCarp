package com.totlc.levels;

import com.badlogic.gdx.assets.AssetManager;
import com.totlc.Actors.Player;
import com.totlc.AssetList;
import com.totlc.TradersOfTheLastCarp;

import com.totlc.levels.ObjectiveVerifier.Objectives;

public class Level03 extends ALevel {

    public final String tmxFileName = AssetList.LEVEL03_TMX.toString();

    public Level03(AssetManager assetManager) {
        super(assetManager, Objectives.SURVIVE);
        setTimeLimit(20000);
        setNextLevel(EndLevel.class);
    }

    public void initLevel() {
        setPlayer(TradersOfTheLastCarp.player);
        loadFromTMX(tmxFileName);

        TradersOfTheLastCarp.musicPlayer.stop();
        TradersOfTheLastCarp.musicPlayer.setSong("test2");
        TradersOfTheLastCarp.musicPlayer.play();
    }
}
