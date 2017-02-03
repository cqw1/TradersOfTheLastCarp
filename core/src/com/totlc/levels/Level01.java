package com.totlc.levels;

import com.badlogic.gdx.assets.AssetManager;
import com.totlc.AssetList;
import com.totlc.TradersOfTheLastCarp;
import com.totlc.Actors.Player;
import com.totlc.levels.ObjectiveVerifier.*;

public class Level01 extends ALevel{

    public final String tmxFileName = AssetList.LEVEL01_TMX.toString();

    public Level01(Player player, AssetManager assetManager) {
        super(player, assetManager, Objectives.DESTROY);
    }

    public void initLevel(Player player) {
        super.initLevel(player);
        super.loadFromTMX(tmxFileName);

        TradersOfTheLastCarp.musicPlayer.setSong("test0");
        TradersOfTheLastCarp.musicPlayer.play();

        //REQUIRED
        endInit();
    }
}
