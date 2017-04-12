package com.totlc.Actors.UI;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.*;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.totlc.Actors.players.Player;
import com.totlc.AssetList;
import com.totlc.TradersOfTheLastCarp;
import com.totlc.levels.ALevel;

/**
 * UI Actor class for displaying player inventory.
 */
public class Inventory extends Actor{

    // Texture information.
    private Texture uiComponent;

    private TextureRegion itemIcon;
    private TextureRegion scoreIcon;
    private BitmapFont font;
    GlyphLayout layout = new GlyphLayout();
    private int scoreOffset = 60;

    // Reference to actor to draw for.
    private Player player;

    public Inventory(AssetManager assetManager, Player player, int x, int y){
        setX(x);
        setY(y);
        setWidth(128);
        setHeight(128);
        setPlayer(player);
        uiComponent = assetManager.get(AssetList.INVENTORY_BOX.toString());
        itemIcon = assetManager.get(AssetList.ITEM_PACK.toString(), TextureAtlas.class).findRegion("0Key");
        itemIcon.getTexture().setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
        TextureAtlas scoreAtlas = assetManager.get(AssetList.GOLDFISH_UI.toString());
        scoreIcon = scoreAtlas.getRegions().get(0);
        scoreIcon.getTexture().setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
        font = TradersOfTheLastCarp.systemFont0;
        font.getRegion().getTexture().setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
    }

    public void draw(Batch batch, float alpha){
        batch.draw(uiComponent, getX(), getY());
        if (getPlayer().hasKey()){
            batch.draw(itemIcon, getX() + getWidth() * 0.5f - itemIcon.getRegionWidth() * 0.5f, getY(),
                    itemIcon.getRegionWidth() * 0.5f, itemIcon.getRegionHeight() * 0.5f,
                    itemIcon.getRegionWidth(), itemIcon.getRegionHeight(), 0.5f, 0.5f, 0);
        }
        drawScore(batch, alpha);
    }

    public Player getPlayer() {
        return player;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    private void drawScore(Batch batch, float alpha) {
        font.getData().setScale(1.0f);
        layout.setText(font, "SCORE: " + ((ALevel)getStage()).getPlayer().getGoldfishCount());
        font.draw(batch, "SCORE: " + ((ALevel)getStage()).getPlayer().getGoldfishCount(), getX()+ uiComponent.getWidth() + scoreOffset, getY() + uiComponent.getHeight() * 0.53f);
        batch.draw(scoreIcon, getX() + uiComponent.getWidth() + scoreOffset + layout.width + 5, getY() + uiComponent.getHeight() * 0.53f - scoreIcon.getRegionHeight() * 0.5f * 0.36f - 5, 0, 0,
                (float) scoreIcon.getRegionWidth(),
                (float) scoreIcon.getRegionHeight(), 0.36f, 0.36f, 0);
    }
}
