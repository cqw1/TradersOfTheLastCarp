package com.totlc.levels;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.math.Rectangle;
import com.totlc.Actors.Player;
import com.totlc.Actors.enemies.Spider;
import com.totlc.Actors.enemies.movement.IntervalMovement;
import com.totlc.Actors.items.Health;
import com.totlc.Actors.traps.ArrowTrap;
import com.totlc.Actors.triggers.ButtonTrigger;
import com.totlc.AssetList;
import com.totlc.TradersOfTheLastCarp;

import com.totlc.levels.ObjectiveVerifier.Objectives;

public class Level03 extends ALevel {

    public final String tmxFileName = AssetList.LEVEL03_TMX.toString();

    public Level03(Player player, AssetManager assetManager) {
        super(player, assetManager, Objectives.SURVIVE);
        setTimeLimit(20000);
    }

    public void initLevel() {
        loadFromTMX(tmxFileName);

        TradersOfTheLastCarp.musicPlayer.stop();
        TradersOfTheLastCarp.musicPlayer.setSong("test2");
        TradersOfTheLastCarp.musicPlayer.play();
    }
}
