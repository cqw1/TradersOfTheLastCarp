package com.totlc.Actors.terrain;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.totlc.Actors.TotlcObject;
import com.totlc.Directionality;
import com.totlc.TradersOfTheLastCarp;
import com.totlc.levels.ObjectiveVerifier.Objectives;

public class NextStage extends TotlcObject {

    public AWall physicalBlock;
    public static float entranceSize = 96;

    public NextStage(AssetManager assetManager, float wallSize, float playerHeight) {
        this(assetManager, wallSize, playerHeight, Objectives.SURVIVE);
    }

    public NextStage(AssetManager assetManager, float wallSize, float playerHeight, Objectives obj) {
        super(assetManager, new Rectangle(TradersOfTheLastCarp.CONFIG_WIDTH - wallSize + entranceSize / 2,
                (TradersOfTheLastCarp.CONFIG_HEIGHT / 2) - (playerHeight / 2) - entranceSize / 2,
                wallSize - 25,
                playerHeight + entranceSize));

        physicalBlock = new Door(assetManager, TradersOfTheLastCarp.CONFIG_WIDTH - wallSize,
                (TradersOfTheLastCarp.CONFIG_HEIGHT / 2) - (playerHeight / 2) - entranceSize / 2,
                wallSize,
                playerHeight + entranceSize,
                obj,
                Directionality.LEFT);
    }

    public NextStage(AssetManager assetManager, Rectangle r) {
        super(assetManager, r);
    }

    @Override
    public void draw(Batch batch, float alpha) {}

    @Override
    public boolean collidesWith(Actor otherActor) {
        return false;
    }

    @Override
    public void endCollidesWith(Actor otherActor) {

    }

    public AWall getPhysicalBlock() {
        return physicalBlock;
    }

    public void setPhysicalBlock(AWall physicalBlock) {
        this.physicalBlock = physicalBlock;
    }

    public void unlock() {
        if(!physicalBlock.isOpen()) {
            physicalBlock.setOpen(true);
            Sound impactSound = Gdx.audio.newSound(Gdx.files.internal("sounds/door_unlock.mp3"));
            impactSound.play(1f);
        }
    }
}
