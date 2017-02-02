package com.totlc.levels;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.math.Rectangle;
import com.totlc.Actors.enemies.AEnemy;
import com.totlc.Actors.enemies.JustDessert;
import com.totlc.Actors.enemies.movement.Immobile;
import com.totlc.Actors.traps.ArrowTrap;
import com.totlc.Actors.triggers.ButtonTrigger;
import com.totlc.TradersOfTheLastCarp;
import com.totlc.Actors.Player;
import com.totlc.levels.ObjectiveVerifier.*;

public class Level01 extends ALevel{

    public Level01(Player player, AssetManager assetManager) {
        super(player, assetManager, Objectives.DESTROY);
    }

    public void initLevel(Player player) {
        super.initLevel(player);
        TradersOfTheLastCarp.musicPlayer.setSong("test0");
        TradersOfTheLastCarp.musicPlayer.play();
        setNameString("Level: 01");

        //REQUIRED
        setPlayer(player);

        ArrowTrap arrowTrap = new ArrowTrap(getAssetManager(), TradersOfTheLastCarp.CONFIG_WIDTH / 2, TradersOfTheLastCarp.CONFIG_HEIGHT / 2, 650);
        arrowTrap.moveRel(-arrowTrap.getWidth() / 2, -arrowTrap.getHeight() / 2);

        for (int i = 0; i < TradersOfTheLastCarp.CONFIG_HEIGHT / 128; i++) {
            // Create a column of triggers so you always step on one.
            ButtonTrigger trigger = new ButtonTrigger(getAssetManager(), new Rectangle(TradersOfTheLastCarp.CONFIG_WIDTH / 4, i * 128, 32, 32));
            trigger.moveRel(-trigger.getWidth() / 2, -trigger.getHeight() / 2);
            trigger.addTrap(arrowTrap);
            addActor(trigger);
        }

        addActor(arrowTrap);
        
        AEnemy dummyFlan = new JustDessert(getAssetManager(),
                3 * TradersOfTheLastCarp.CONFIG_WIDTH / 4, TradersOfTheLastCarp.CONFIG_HEIGHT / 2, new Immobile(getPlayer()));
        dummyFlan.setHpMax(2);
        dummyFlan.setHpCurrent(2);
        addActor(dummyFlan);


        //REQUIRED
        endInit();
    }
}
