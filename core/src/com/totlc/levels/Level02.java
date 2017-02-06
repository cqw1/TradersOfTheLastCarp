package com.totlc.levels;

import com.badlogic.gdx.assets.AssetManager;
import com.totlc.Actors.Player;
import com.totlc.AssetList;
import com.totlc.TradersOfTheLastCarp;
import com.totlc.levels.ObjectiveVerifier.*;

public class Level02 extends ALevel {

    public final String tmxFileName = AssetList.LEVEL02_TMX.toString();

    public Level02(AssetManager assetManager) {
        super(assetManager, Objectives.DESTROY);
        setNextLevel(Level03.class);
    }

    public void initLevel() {
        setPlayer(TradersOfTheLastCarp.player);
        loadFromTMX(tmxFileName);

        TradersOfTheLastCarp.musicPlayer.stop();
        TradersOfTheLastCarp.musicPlayer.setSong("test2");
        TradersOfTheLastCarp.musicPlayer.play();
    }
}
