package com.totlc.levels;

import com.badlogic.gdx.assets.AssetManager;
import com.totlc.Actors.enemies.AEnemy;
import com.totlc.Actors.enemies.EnemyFactory;
import com.totlc.TradersOfTheLastCarp;

public class StarLevel extends ALevel {

    public StarLevel(AssetManager assetManager) {
        super(assetManager, ObjectiveVerifier.Objectives.SURVIVE);
        setNextLevel(FlanLevel.class);
    }

    public void initLevel() {
        setPlayer(TradersOfTheLastCarp.player);
        setNameString("Stargazer");

        AEnemy star = EnemyFactory.createDefaultEnemy(EnemyFactory.STAR, getAssetManager(), 700, 500);
        addActor(star);

        endInit();
    }
}
