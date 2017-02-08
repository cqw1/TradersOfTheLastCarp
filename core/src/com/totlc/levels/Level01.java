package com.totlc.levels;

import com.badlogic.gdx.assets.AssetManager;
import com.totlc.Actors.enemies.JustDessert;
import com.totlc.Actors.enemies.movement.BasicMovement;
import com.totlc.Actors.enemies.movement.IntervalMovement;
import com.totlc.Actors.enemies.movement.ProximityBasedAggro;
import com.totlc.Actors.items.Goldfish;
import com.totlc.Actors.traps.FireTrapDown;
import com.totlc.Actors.traps.FireTrapLeft;
import com.totlc.Actors.traps.FireTrapRight;
import com.totlc.AssetList;
import com.totlc.TradersOfTheLastCarp;
import com.totlc.levels.ObjectiveVerifier.*;

public class Level01 extends ALevel{

    public final String tmxFileName = AssetList.LEVEL01_TMX.toString();

    public Level01(AssetManager assetManager) {
        super(assetManager, Objectives.DESTROY);
        setNextLevel(Level02.class);
    }

    public void initLevel() {
        setPlayer(TradersOfTheLastCarp.player);
        loadFromTMX(tmxFileName);

        TradersOfTheLastCarp.musicPlayer.setSong("test0");
        TradersOfTheLastCarp.musicPlayer.play();
    }
}
