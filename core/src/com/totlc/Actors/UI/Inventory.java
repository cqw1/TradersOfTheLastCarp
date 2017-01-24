package com.totlc.Actors.UI;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.NinePatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.totlc.Actors.Player;
import com.totlc.AssetList;

/**
 * UI Actor class for displaying player inventory.
 */
public class Inventory extends Actor{

    // Texture information.
    private Texture uiComponent, itemIcon;


    // Reference to actor to draw for.
    private Player player;

    public Inventory(Player player, int x, int y){
        setX(x);
        setY(y);
        setWidth(128);
        setHeight(128);
        setPlayer(player);
        uiComponent = new Texture(AssetList.INVENTORY_BOX.toString());
    }

    public void draw(Batch batch, float alpha){
        batch.draw(uiComponent, getX(), getY());
        if (itemIcon != null){
            batch.draw(itemIcon, getX(), getY());
        }
    }

    public Player getPlayer() {
        return player;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }
}
