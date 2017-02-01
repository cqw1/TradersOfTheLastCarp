package com.totlc.levels;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.math.Rectangle;
import com.totlc.Actors.enemies.AEnemy;
import com.totlc.Actors.enemies.JustDessert;
import com.totlc.Actors.enemies.movement.Immobile;
import com.totlc.Actors.terrain.NextStage;
import com.totlc.Actors.tileset.BasicTileSet;
import com.totlc.Actors.traps.ArrowTrap;
import com.totlc.Actors.triggers.ButtonTrigger;
import com.totlc.TradersOfTheLastCarp;
import com.totlc.Actors.Player;
import com.totlc.levels.ObjectiveVerifier.*;

public class Level01 extends ALevel{

    public Level01(Player player, AssetManager assetManager) {
        super(player, assetManager,
                new NextStage(assetManager, ALevel.DEFAULT_WALLSIZE, player.getHeight()),
                new Level02(player, assetManager),
                objectives.DESTROY);
    }

    public void initLevel(Player player) {
        playSong("test0");
        setNameString("Level: 01");

        BasicTileSet bts = new BasicTileSet(getAssetManager());
        addActor(bts);

        //REQUIRED
        setPlayer(player);

        ButtonTrigger tutorialButton = new ButtonTrigger(getAssetManager(), new Rectangle(TradersOfTheLastCarp.CONFIG_WIDTH / 4, TradersOfTheLastCarp.CONFIG_HEIGHT / 2, 32, TradersOfTheLastCarp.CONFIG_HEIGHT));
        tutorialButton.moveHitBox(0,-1 * TradersOfTheLastCarp.CONFIG_HEIGHT / 2);

        ArrowTrap arrowTrap = new ArrowTrap(getAssetManager(), TradersOfTheLastCarp.CONFIG_WIDTH / 2, TradersOfTheLastCarp.CONFIG_HEIGHT / 2, 650);
        arrowTrap.moveRel(-arrowTrap.getWidth() / 2, -arrowTrap.getHeight() / 2);

        tutorialButton.addTrap(arrowTrap);
        addActor(tutorialButton);
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
