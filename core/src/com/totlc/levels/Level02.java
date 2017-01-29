package com.totlc.levels;

import com.badlogic.gdx.assets.AssetManager;
import com.totlc.Actors.Player;
import com.totlc.Actors.enemies.Spider;
import com.totlc.Actors.terrain.NextStage;
import com.totlc.Actors.tileset.BasicTileSet;
import com.totlc.levels.ObjectiveVerifier.*;

public class Level02 extends ALevel {

    public Level02(Player player, AssetManager assetManager) {
        super(player, assetManager,
                new NextStage(assetManager, ALevel.DEFAULT_WALLSIZE, player.getHeight()),
                new EndLevel(player, assetManager),
                objectives.DESTROY);
    }

    public void initLevel(Player player) {
        playSong("test0");
        setNameString("TEST LEVEL");

        //REQUIRED
        setPlayer(player);

        BasicTileSet bts = new BasicTileSet(getAssetManager());
        addActor(bts);

        Spider spider0 = new Spider(getAssetManager(), 0, 0);
        addActor(spider0);

        //REQUIRED
        endInit();
    }
}
