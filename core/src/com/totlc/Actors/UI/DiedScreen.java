package com.totlc.Actors.UI;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.totlc.Actors.TotlcObject;
import com.totlc.AssetList;
import com.totlc.TradersOfTheLastCarp;

public class DiedScreen extends TotlcObject{

    public DiedScreen(AssetManager assetManager) {
        super(assetManager, new Rectangle(0, 0, TradersOfTheLastCarp.CONFIG_WIDTH, TradersOfTheLastCarp.CONFIG_HEIGHT));
    }

    @Override
    public void draw(Batch batch, float alpha) {
        batch.draw(getAssetManager().get(AssetList.DIED_SCREEN.toString(), Texture.class), 0f, 0f, (float) TradersOfTheLastCarp.CONFIG_WIDTH, (float) TradersOfTheLastCarp.CONFIG_HEIGHT);
    }

    @Override
    public boolean collidesWith(Actor otherActor) {
        return false;
    }

    @Override
    public void endCollidesWith(Actor otherActor) {}
}
