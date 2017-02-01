package com.totlc.levels;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.math.Rectangle;
import com.totlc.Actors.Player;
import com.totlc.Actors.enemies.AEnemy;
import com.totlc.Actors.enemies.DaredevilPangolini;
import com.totlc.Actors.enemies.Stargazer;
import com.totlc.Actors.enemies.movement.BasicMovement;
import com.totlc.Actors.items.Health;
import com.totlc.Actors.terrain.NextStage;
import com.totlc.Actors.tileset.BasicTileSet;
import com.totlc.Actors.traps.ArrowTrap;
import com.totlc.Actors.triggers.ButtonTrigger;
import com.totlc.TradersOfTheLastCarp;
import com.totlc.levels.ObjectiveVerifier.objectives;

public class Level03 extends ALevel {

    public Level03(Player player, AssetManager assetManager) {
        super(player, assetManager,
                new NextStage(assetManager, ALevel.DEFAULT_WALLSIZE, player.getHeight()),
                new EndLevel(player, assetManager),
                objectives.SURVIVE);
    }

    public void initLevel(Player player) {
        playSong("test0");
        setNameString("Level: 03");

        //REQUIRED
        setPlayer(player);

        BasicTileSet bts = new BasicTileSet(getAssetManager());
        addActor(bts);

        Health health = new Health(getAssetManager(), getWallSize() + 300, TradersOfTheLastCarp.CONFIG_HEIGHT / 2);
        addActor(health);

        // Invincible stargazer.
        Stargazer stargazer = new Stargazer(getAssetManager(), (int)(TradersOfTheLastCarp.CONFIG_WIDTH * 0.5), (int)(TradersOfTheLastCarp.CONFIG_HEIGHT * 0.5), new BasicMovement(getPlayer()));
        stargazer.setInvincible(true);
        addActor(stargazer);

        // Arrow traps and triggers along the top wall at intervals of 0.2
        ButtonTrigger buttonTop1 = new ButtonTrigger(getAssetManager(), new Rectangle((int)(TradersOfTheLastCarp.CONFIG_WIDTH * 0.1), (int)(TradersOfTheLastCarp.CONFIG_HEIGHT * 0.3), 32, 32));
        buttonTop1.moveHitBox(0,-1 * TradersOfTheLastCarp.CONFIG_HEIGHT / 2);

        ArrowTrap arrowTrapTop1 = new ArrowTrap(getAssetManager(), (int)(TradersOfTheLastCarp.CONFIG_WIDTH * 0.2), (int)(TradersOfTheLastCarp.CONFIG_HEIGHT *0.1), 500);
        arrowTrapTop1.moveRel(-arrowTrapTop1.getWidth() / 2, -arrowTrapTop1.getHeight() / 2);

        buttonTop1.addTrap(arrowTrapTop1);
        addActor(buttonTop1);
        addActor(arrowTrapTop1);

        //REQUIRED
        endInit();
    }
}
