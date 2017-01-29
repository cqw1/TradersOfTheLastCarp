package com.totlc.Actors.terrain;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.totlc.Actors.TotlcObject;
import com.totlc.TradersOfTheLastCarp;

public class NextStage extends TotlcObject {

    public AWall physicalBlock;

    public NextStage(AssetManager assetManager, float wallSize, float playerHeight) {
        super(assetManager, new Rectangle(TradersOfTheLastCarp.CONFIG_WIDTH - wallSize + 25,
                (TradersOfTheLastCarp.CONFIG_HEIGHT / 2) - (playerHeight / 2) - 25,
                wallSize - 25,
                playerHeight + 50));

        physicalBlock = new RightWall(assetManager, new Rectangle(TradersOfTheLastCarp.CONFIG_WIDTH - wallSize,
                (TradersOfTheLastCarp.CONFIG_HEIGHT / 2) - (playerHeight / 2) - 25,
                wallSize,
                playerHeight + 50));
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
        physicalBlock.remove();
    }
}