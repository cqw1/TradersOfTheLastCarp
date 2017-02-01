package com.totlc.levels;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.math.Rectangle;
import com.totlc.Actors.Player;
import com.totlc.Actors.enemies.AEnemy;
import com.totlc.Actors.enemies.DaredevilPangolini;
import com.totlc.Actors.enemies.Spider;
import com.totlc.Actors.enemies.Stargazer;
import com.totlc.Actors.enemies.movement.BasicMovement;
import com.totlc.Actors.enemies.movement.IntervalMovement;
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

        // Invincible spiders.
        Spider spider0 = new Spider(getAssetManager(), (int)(TradersOfTheLastCarp.CONFIG_WIDTH * 0.75), (int)(TradersOfTheLastCarp.CONFIG_HEIGHT * 0.5), new BasicMovement(getPlayer()));
        spider0.setMaxVel(spider0.getMaxVel() - 40);
        spider0.setInvincible(true);

        Spider spider1 = new Spider(getAssetManager(), (int)(TradersOfTheLastCarp.CONFIG_WIDTH * 0.5), (int)(TradersOfTheLastCarp.CONFIG_HEIGHT * 0.5), new BasicMovement(getPlayer()));
        spider1.setMaxVel(spider1.getMaxVel() - 40);
        spider1.setInvincible(true);

        addActor(spider0);
        addActor(spider1);


        for (int i = 0; i < 3; i++) {
            ButtonTrigger triggerTop = new ButtonTrigger(getAssetManager(), new Rectangle((int)(TradersOfTheLastCarp.CONFIG_WIDTH * (0.1 + i * 0.3)), (int)(TradersOfTheLastCarp.CONFIG_HEIGHT * 0.4), 32, 32));
            triggerTop.moveRel(-triggerTop.getWidth() / 2, -triggerTop.getHeight() / 2);

            ButtonTrigger triggerBot = new ButtonTrigger(getAssetManager(), new Rectangle((int)(TradersOfTheLastCarp.CONFIG_WIDTH * (0.25 + i * 0.3)), (int)(TradersOfTheLastCarp.CONFIG_HEIGHT * 0.4), 32, 32));
            triggerBot.moveRel(-triggerBot.getWidth() / 2, -triggerBot.getHeight() / 2);

            ArrowTrap arrowTrapTop = new ArrowTrap(getAssetManager(), (int)(TradersOfTheLastCarp.CONFIG_WIDTH * ((i + 1) * 0.3)), (int)(TradersOfTheLastCarp.CONFIG_HEIGHT * 0.8), 500);
            arrowTrapTop.moveRel(-arrowTrapTop.getWidth() / 2, -arrowTrapTop.getHeight() / 2);

            ArrowTrap arrowTrapBot = new ArrowTrap(getAssetManager(), (int)(TradersOfTheLastCarp.CONFIG_WIDTH * (0.15 + (i + 1) * 0.3)), (int)(TradersOfTheLastCarp.CONFIG_HEIGHT * 0.2), 500);
            arrowTrapBot.moveRel(-arrowTrapBot.getWidth() / 2, -arrowTrapBot.getHeight() / 2);

            triggerTop.addTrap(arrowTrapTop);
            triggerBot.addTrap(arrowTrapBot);

            addActor(triggerTop);
            addActor(arrowTrapTop);
            addActor(triggerBot);
            addActor(arrowTrapBot);
        }

        //REQUIRED
        endInit();
    }
}
