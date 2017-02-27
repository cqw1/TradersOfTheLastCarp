package com.totlc.levels;

import com.badlogic.gdx.assets.AssetManager;
import com.totlc.Actors.enemies.GelatinKing;
import com.totlc.Actors.enemies.movement.RandomMovement;
import com.totlc.Actors.items.Goldfish;
import com.totlc.TradersOfTheLastCarp;

import static com.totlc.TradersOfTheLastCarp.SCREEN_HEIGHT;
import static com.totlc.TradersOfTheLastCarp.SCREEN_WIDTH;

public class GoldfishLevel extends ALevel {

    public GoldfishLevel(AssetManager assetManager) {
        super(assetManager, ObjectiveVerifier.Objectives.SURVIVE);
        setNextLevel(TitleScreen.class);
    }

    @Override
    public void initOtherLevelStuff() {
        setPlayer(TradersOfTheLastCarp.player);
        setNameString("goldfish");


        Goldfish goldfish1 = new Goldfish(getAssetManager(), SCREEN_WIDTH / 4, SCREEN_HEIGHT / 2);
        addActor(goldfish1);

        Goldfish goldfish2 = new Goldfish(getAssetManager(), (3 * SCREEN_WIDTH) / 4, SCREEN_HEIGHT / 2);
        addActor(goldfish2);


        endInit();
    }
}
