package com.totlc.levels;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.math.Rectangle;
import com.totlc.Actors.Player;
import com.totlc.Actors.enemies.AEnemy;
import com.totlc.Actors.enemies.DaredevilPangolini;
import com.totlc.Actors.enemies.Spider;
import com.totlc.Actors.enemies.movement.BasicMovement;
import com.totlc.Actors.items.Health;
import com.totlc.Actors.terrain.NextStage;
import com.totlc.Actors.tileset.BasicTileSet;
import com.totlc.Actors.weapons.Whip;
import com.totlc.AssetList;
import com.totlc.TradersOfTheLastCarp;
import com.totlc.levels.ObjectiveVerifier.*;

public class Level02 extends ALevel {

    public Level02(Player player, AssetManager assetManager) {
        super(player, assetManager,
                new NextStage(assetManager, ALevel.DEFAULT_WALLSIZE, player.getHeight()),
                new Level03(player, assetManager),
                objectives.DESTROY);
    }

    public void initLevel(Player player) {
        playSong("test0");
        setNameString("Level: 02");

        //REQUIRED
        setPlayer(player);

        BasicTileSet bts = new BasicTileSet(getAssetManager());
        addActor(bts);

        Health health = new Health(getAssetManager(), getWallSize() + 300, TradersOfTheLastCarp.CONFIG_HEIGHT / 2);
        addActor(health);

        AEnemy pangolini1 = new DaredevilPangolini(getAssetManager(), new Rectangle(TradersOfTheLastCarp.CONFIG_WIDTH * 3 / 4, TradersOfTheLastCarp.CONFIG_HEIGHT / 2,
                72, 108),
                new BasicMovement(getPlayer()));
        addActor(pangolini1);

        //REQUIRED
        endInit();
    }
}
