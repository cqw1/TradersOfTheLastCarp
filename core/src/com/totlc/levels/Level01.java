package com.totlc.levels;

import com.badlogic.gdx.assets.AssetManager;
import com.totlc.Actors.effects.Smoke;
import com.totlc.Actors.projectiles.FireStreamRight;
import com.totlc.AssetList;
import com.totlc.TradersOfTheLastCarp;
import com.totlc.Actors.Player;
import com.totlc.levels.ObjectiveVerifier.*;

public class Level01 extends ALevel{

    public final String tmxFileName = AssetList.LEVEL01_TMX.toString();

    public Level01(Player player, AssetManager assetManager) {
        super(player, assetManager, Objectives.DESTROY);
    }

    public void initLevel() {
        loadFromTMX(tmxFileName);
        addActor(new FireStreamRight(getAssetManager(), getPlayer(), 0, 0));

        TradersOfTheLastCarp.musicPlayer.setSong("test0");
        TradersOfTheLastCarp.musicPlayer.play();
    }
}
