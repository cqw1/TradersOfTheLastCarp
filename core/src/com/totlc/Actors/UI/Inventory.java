package com.totlc.Actors.UI;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.*;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.totlc.Actors.Player;
import com.totlc.AssetList;
import com.totlc.TradersOfTheLastCarp;
import com.totlc.levels.ALevel;

/**
 * UI Actor class for displaying player inventory.
 */
public class Inventory extends Actor{

    // Texture information.
    private Texture uiComponent, itemIcon;
    private TextureRegion scoreIcon;
    private BitmapFont font;

    // Reference to actor to draw for.
    private Player player;

    public Inventory(AssetManager assetManager, Player player, int x, int y){
        setX(x);
        setY(y);
        setWidth(128);
        setHeight(128);
        setPlayer(player);
        uiComponent = assetManager.get(AssetList.INVENTORY_BOX.toString());
        itemIcon = null;
        TextureAtlas scoreAtlas = assetManager.get(AssetList.GOLDFISH_UI.toString());
        scoreIcon = scoreAtlas.getRegions().get(0);
        scoreIcon.getTexture().setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
        font = TradersOfTheLastCarp.systemFont0;
        font.getRegion().getTexture().setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
    }

    public void draw(Batch batch, float alpha){
        batch.draw(uiComponent, getX(), getY());
        if (itemIcon != null){
            batch.draw(itemIcon, getX(), getY());
        }
        drawScore(batch, alpha);
    }

    public Player getPlayer() {
        return player;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    private void drawScore(Batch batch, float alpha){
        font.draw(batch, "SCORE: " + ((ALevel)getStage()).getPlayer().getGoldFishCount(), getX()+ uiComponent.getWidth(), getY() + uiComponent.getHeight() * 0.2f);
        batch.draw(scoreIcon, getX() + uiComponent.getWidth() * 2.0f, getY(), (float) scoreIcon.getRegionWidth() * 0.5f,
                (float) scoreIcon.getRegionHeight() * 0.5f,
                (float) scoreIcon.getRegionWidth(),
                (float) scoreIcon.getRegionHeight(), 0.5f, 0.5f, 0);
    }
}
