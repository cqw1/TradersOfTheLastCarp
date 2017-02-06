package com.totlc.levels;

import com.badlogic.gdx.assets.AssetManager;
import com.totlc.Actors.enemies.JustDessert;
import com.totlc.Actors.enemies.movement.BasicMovement;
import com.totlc.Actors.enemies.movement.IntervalMovement;
import com.totlc.Actors.items.Goldfish;
import com.totlc.Actors.traps.FireTrapDown;
import com.totlc.Actors.traps.FireTrapLeft;
import com.totlc.Actors.traps.FireTrapRight;
import com.totlc.AssetList;
import com.totlc.TradersOfTheLastCarp;
import com.totlc.levels.ObjectiveVerifier.*;

public class Level01 extends ALevel{

    public final String tmxFileName = AssetList.LEVEL01_TMX.toString();

    public Level01(AssetManager assetManager) {
        super(assetManager, Objectives.DESTROY);
        setNextLevel(Level02.class);
    }

    public void initLevel() {
        setPlayer(TradersOfTheLastCarp.player);
        loadFromTMX(tmxFileName);

        FireTrapLeft f = new FireTrapLeft(getAssetManager(), 500, 500, 800, 500);
        addActor(f);
        f.setup();

        FireTrapRight f1 = new FireTrapRight(getAssetManager(), 500, 300, 2000, 1000);
        addActor(f1);
        f1.setup();

        FireTrapDown f2 = new FireTrapDown(getAssetManager(), 800, 800, 1500, 1500);
        addActor(f2);
        f2.setup();

        JustDessert j = new JustDessert(getAssetManager(), 150, 700, 3, new IntervalMovement(getPlayer()));
        addActor(j);

        JustDessert j2 = new JustDessert(getAssetManager(), 100, 700, 1, new IntervalMovement(getPlayer()));
        addActor(j2);

        JustDessert j3 = new JustDessert(getAssetManager(), 200, 450, 7, new IntervalMovement(getPlayer()));
        addActor(j3);

        Goldfish g = new Goldfish(getAssetManager(), 500, 200);
        addActor(g);

        TradersOfTheLastCarp.musicPlayer.setSong("test0");
        TradersOfTheLastCarp.musicPlayer.play();
    }
}
