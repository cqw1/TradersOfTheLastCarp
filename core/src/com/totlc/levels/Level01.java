package com.totlc.levels;

import com.badlogic.gdx.assets.AssetManager;
import com.totlc.Actors.UI.ToolTip;
import com.totlc.AssetList;
import com.totlc.TradersOfTheLastCarp;
import com.totlc.levels.ObjectiveVerifier.*;

import static com.totlc.TradersOfTheLastCarp.CONFIG_HEIGHT;
import static com.totlc.TradersOfTheLastCarp.CONFIG_WIDTH;

public class Level01 extends ALevel {

    public final String tmxFileName = AssetList.LEVEL01_TMX.toString();

    public Level01(AssetManager assetManager) {
        super(assetManager);
        setNextLevel(Level02.class);
    }

    public static ALevel make(AssetManager assetManager) {
        return new Level01(assetManager);
    }

    public void initOtherLevelStuff() {
        setPlayer(TradersOfTheLastCarp.player);
        restorePlayerHealth();
        loadFromTMX(tmxFileName);


        ToolTip objectiveToolTip = new ToolTip(
                getAssetManager(),
                CONFIG_WIDTH - 500,
                CONFIG_HEIGHT - 250,
                "Check each level's objective \nto complete the level.",
                2f,
                6f,
                12f,
                false);

        addActor(objectiveToolTip);
    }
}
