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

        Goldfish goldfish = new Goldfish(getAssetManager(), SCREEN_WIDTH / 2, SCREEN_HEIGHT / 2);
        addActor(goldfish);


        endInit();
    }
}
