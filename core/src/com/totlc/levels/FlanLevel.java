package com.totlc.levels;

import com.badlogic.gdx.assets.AssetManager;
import com.totlc.Actors.enemies.AEnemy;
import com.totlc.Actors.enemies.EnemyFactory;
import com.totlc.Actors.traps.ATrap;
import com.totlc.Actors.traps.TrapFactory;
import com.totlc.Actors.triggers.ATrigger;
import com.totlc.Actors.triggers.TriggerFactory;
import com.totlc.TradersOfTheLastCarp;

public class FlanLevel extends ALevel {

    public FlanLevel(AssetManager assetManager) {
        super(assetManager, ObjectiveVerifier.Objectives.SURVIVE);
        setNextLevel(FlameLevel.class);
    }

    public void initOtherLevelStuff() {
        setPlayer(TradersOfTheLastCarp.player);
        setNameString("Flan");

        AEnemy flan = EnemyFactory.createDefaultEnemy(EnemyFactory.FLAN, getAssetManager(), 700, 500);
        addActor(flan);

        ATrap trap = TrapFactory.createTrap(TrapFactory.ARROW_TRAP, getAssetManager(), 400, 500);
        addActor(trap);

        ATrigger trigger = TriggerFactory.createTrigger(TriggerFactory.BUTTON, getAssetManager(), 300, 500);
        trigger.addTrap(trap);
        addActor(trigger);

        endInit();
    }
}
