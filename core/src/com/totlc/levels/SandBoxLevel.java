package com.totlc.levels;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.totlc.Actors.UI.ButtonPrompt;
import com.totlc.Actors.effects.Confetti;
import com.totlc.Actors.enemies.*;
import com.totlc.Actors.enemies.movement.*;
import com.totlc.Actors.items.AItem;
import com.totlc.Actors.items.APickup;
import com.totlc.Actors.items.Goldfish;
import com.totlc.Actors.items.Key;
import com.totlc.Actors.terrain.AWall;
import com.totlc.Actors.terrain.Rock;
import com.totlc.Actors.traps.*;
import com.totlc.Actors.triggers.*;
import com.totlc.AssetList;
import com.totlc.Directionality;
import com.totlc.TradersOfTheLastCarp;
import com.totlc.levels.ObjectiveVerifier.*;

/**
 * Order of demo levels:
 *  Sandbox
 *  Spike trap
 *  Teleporter
 *  Spider repository and spiders
 *  Stargazer
 *  Flan + arrow trap (to exhibit splitting)
 *  Flamethrowers
 *  GelatinLevel
 */

public class SandBoxLevel extends ALevel{

    public SandBoxLevel(AssetManager assetManager) {
        super(assetManager, Objectives.UNLOCK);
        setNextLevel(TitleScreen.class);
    }

    public void initOtherLevelStuff() {
        setPlayer(TradersOfTheLastCarp.player);
        setNameString("SandBox");

        TradersOfTheLastCarp.musicPlayer.stop();
        TradersOfTheLastCarp.musicPlayer.setSong("test0");
        TradersOfTheLastCarp.musicPlayer.getCurrentSong().setLooping(true);
        TradersOfTheLastCarp.musicPlayer.play();

//        getPlayer().setGoldfishCount(25);
//        addActor(new Confetti(getAssetManager(), getPlayer()));

        //Demo Level 1
//        ATrap y = new FireTrap(getAssetManager(), 800, 400, Directionality.LEFT);
//        addActor(y);
//        ATrap y1 = new FireTrap(getAssetManager(), 600, 200, Directionality.LEFT);
//        addActor(y1);
//        ATrap y2 = new FireTrap(getAssetManager(), 700, 600, Directionality.DOWN);
//        addActor(y2);
//        ATrap y3 = new FireTrap(getAssetManager(), 500, 700, Directionality.DOWN);
//        addActor(y3);
//        ATrap y4 = new FireTrap(getAssetManager(), 700, 300, Directionality.LEFT);
//        addActor(y4);
//        ATrap y5 = new FireTrap(getAssetManager(), 300, 600, Directionality.DOWN);
//        addActor(y5);
//
//        AWall r = new Rock(getAssetManager(), 200, 700);
//        addActor(r);


//        APickup a = new Goldfish(getAssetManager(), 200, 200);
//        addActor(a);
//        APickup a1 = new Goldfish(getAssetManager(), 300, 500);
//        addActor(a1);
//        APickup a2 = new Goldfish(getAssetManager(), 500, 700);
//        addActor(a2);
//        APickup a3 = new Goldfish(getAssetManager(), 700, 300);
//        addActor(a3);
//        APickup a4 = new Goldfish(getAssetManager(), 800, 400);
//        addActor(a4);
//        APickup a5 = new Goldfish(getAssetManager(), 600, 500);
//        addActor(a5);

//        IntervalTrigger z = new IntervalTrigger(getAssetManager(), 200, 400);
//        z.setStartTime(1000);
//        z.setInterval(2500);
//        z.addTrap(y1);
//        z.addTrap(y3);
//        z.addTrap(y5);
//        addActor(z);
//
//        IntervalTrigger z1 = new IntervalTrigger(getAssetManager(), 200, 400);
//        z1.setStartTime(3500);
//        z1.setInterval(2500);
//        z1.addTrap(y);
//        z1.addTrap(y2);
//        z1.addTrap(y4);
//        addActor(z1);

//        AEnemy x = new JustDessert(getAssetManager(), 900, 400, new ProximityBasedAggro(getPlayer()));
//        addActor(x);

        ButtonPrompt credits;
        credits = new ButtonPrompt(getAssetManager(), AssetList.CREDITS.toString(), (TradersOfTheLastCarp.CONFIG_WIDTH - 1320) / 2, (TradersOfTheLastCarp.CONFIG_HEIGHT - 602) / 2) {

            @Override
            public void draw(Batch batch, float alpha) {
                batch.draw(getAsset(), getX(), getY(), 1320, 602);
            }

            @Override
            public void update() {
            }
        };

        credits.update();
        addActor(credits);

        ButtonPrompt escPrompt;

        escPrompt = new ButtonPrompt(getAssetManager(), AssetList.BUTTON_PROMPT_ESC.toString(), TradersOfTheLastCarp.CONFIG_WIDTH - 300, 30) {
            private float baseY = getY();

            @Override
            public void draw(Batch batch, float alpha) {
                if (System.currentTimeMillis() % 1000 <= 200) {
                    return;
                }
                batch.draw(getAsset(), getX(), getY(), 150 , 60);
            }

            @Override
            public void update() {
//                setY(baseY - (optionFocusIndex - 1) * 120 * cursorScale);
            }
        };

        escPrompt.update();
        addActor(escPrompt);

        endInit();
    }
}
