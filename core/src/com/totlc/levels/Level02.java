package com.totlc.levels;

import com.badlogic.gdx.assets.AssetManager;
import com.totlc.Actors.Player;
import com.totlc.Actors.enemies.AEnemy;
import com.totlc.Actors.enemies.DaredevilPangolini;
import com.totlc.Actors.enemies.movement.BasicMovement;
import com.totlc.Actors.items.Health;
import com.totlc.Actors.traps.ATrap;
import com.totlc.Actors.traps.ArrowTrap;
import com.totlc.Actors.triggers.ATrigger;
import com.totlc.Actors.triggers.ButtonTrigger;
import com.totlc.AssetList;
import com.totlc.TradersOfTheLastCarp;
import com.totlc.levels.ObjectiveVerifier.*;

public class Level02 extends ALevel {

    public final String tmxFileName = AssetList.LEVEL02_TMX.toString();

    public Level02(Player player, AssetManager assetManager) {
        super(player, assetManager, Objectives.DESTROY);
    }

    public void initLevel(Player player) {
        super.initLevel(player);
        loadFromTMX(tmxFileName);

        TradersOfTheLastCarp.musicPlayer.stop();
        TradersOfTheLastCarp.musicPlayer.setSong("test2");
        TradersOfTheLastCarp.musicPlayer.play();

        //REQUIRED
        endInit();
    }
}
