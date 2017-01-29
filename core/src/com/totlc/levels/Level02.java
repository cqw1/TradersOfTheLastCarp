package com.totlc.levels;

import com.badlogic.gdx.assets.AssetManager;
import com.totlc.Actors.Player;
import com.totlc.Actors.terrain.NextStage;

public class Level02 extends ALevel {

    public Level02(Player player, AssetManager assetManager) {
        super(player, assetManager,
                new NextStage(assetManager, ALevel.DEFAULT_WALLSIZE, player.getHeight()),
                new EndLevel(player, assetManager),
                objectives.UNLOCK);

    }
}
