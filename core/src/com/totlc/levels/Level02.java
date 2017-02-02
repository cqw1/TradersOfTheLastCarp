package com.totlc.levels;

import com.badlogic.gdx.assets.AssetManager;
import com.totlc.Actors.Player;
import com.totlc.Actors.enemies.AEnemy;
import com.totlc.Actors.enemies.DaredevilPangolini;
import com.totlc.Actors.enemies.movement.BasicMovement;
import com.totlc.Actors.items.Health;
import com.totlc.Actors.terrain.NextStage;
import com.totlc.Actors.tileset.BasicTileSet;
import com.totlc.Actors.traps.ATrap;
import com.totlc.Actors.traps.ArrowTrap;
import com.totlc.Actors.triggers.ATrigger;
import com.totlc.Actors.triggers.ButtonTrigger;
import com.totlc.TradersOfTheLastCarp;
import com.totlc.levels.ObjectiveVerifier.*;

public class Level02 extends ALevel {

    public Level02(Player player, AssetManager assetManager) {
        super(player, assetManager, Objectives.DESTROY);
    }

    public void initLevel(Player player) {
        playSong("test0");
        setNameString("Level: 02");

        //REQUIRED
        setPlayer(player);

        Health health = new Health(getAssetManager(), getWallSize() + 300, TradersOfTheLastCarp.CONFIG_HEIGHT / 2);
        addActor(health);

        //Traps
        ATrap at1 = new ArrowTrap(getAssetManager(), 500, 700, 500);
        ATrap at2 = new ArrowTrap(getAssetManager(), 1000, 300, 250);
        ATrap at3 = new ArrowTrap(getAssetManager(), getWallSize() + 300, 200, 500);
        addActor(at1);
        addActor(at2);
        addActor(at3);

        //Triggers
        ATrigger bt1 = new ButtonTrigger(getAssetManager(), getWallSize() + 300, TradersOfTheLastCarp.CONFIG_HEIGHT / 2);
        bt1.addTrap(at1);
        bt1.addTrap(at2);
        bt1.addTrap(at3);

        ATrigger bt2 = new ButtonTrigger(getAssetManager(), 700, 400);
        bt2.addTrap(at1);
        bt2.addTrap(at2);
        bt2.addTrap(at3);

        addActor(bt1);
        addActor(bt2);

        //Enemies
        AEnemy pangolini1 = new DaredevilPangolini(getAssetManager(), TradersOfTheLastCarp.CONFIG_WIDTH * 3 / 4, TradersOfTheLastCarp.CONFIG_HEIGHT / 3,
                new BasicMovement(getPlayer()));
        addActor(pangolini1);
        AEnemy pangolini2 = new DaredevilPangolini(getAssetManager(), TradersOfTheLastCarp.CONFIG_WIDTH * 3 / 4, TradersOfTheLastCarp.CONFIG_HEIGHT / 3 * 2,
                new BasicMovement(getPlayer()));
        addActor(pangolini2);

        //REQUIRED
        endInit();
    }
}
