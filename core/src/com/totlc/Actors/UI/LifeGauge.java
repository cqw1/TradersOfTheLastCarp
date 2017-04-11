package com.totlc.Actors.UI;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.NinePatch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.totlc.Actors.players.Player;
import com.totlc.AssetList;

/**
 * Actor that draws the life gauge of a player.
 */
public class LifeGauge extends Actor {

    // lol hardcoded Static dimension constants.
    private static int gaugeHeight = 64;
    private static int gaugeWidth = 256;
    private static int notchHeight = 19;
    private static int notchWidth = 10;
    private static int vGaugeOffset = 22;
    private static int hGaugeOffset = notchWidth + (int)(0.2 * notchWidth);
    // Notch width multiplier
    private static int notchMult = 3;
    // Texture information.
    private NinePatch gauge;
    private NinePatch notch;

    // Reference to actor to draw for.
    private Player player;

    public LifeGauge(AssetManager assetManager, Player player, int x, int y){
        gauge = new NinePatch(assetManager.get(AssetList.LIFE_GAUGE.toString(), Texture.class), 60, 30, gaugeHeight / 4, gaugeHeight / 4);
        notch = new NinePatch(assetManager.get(AssetList.LIFE_BAR.toString(), Texture.class), notchWidth / 2 - 1, notchWidth / 2 - 1, notchHeight / 4, notchHeight / 4);
        setX(x);
        setY(y);
        setWidth(Math.max(gauge.getLeftWidth() + (notch.getTotalWidth() * notchMult) * player.getHpMax() + hGaugeOffset, gauge.getLeftWidth() + gauge.getRightWidth()));
        setHeight(gauge.getTotalHeight());
        setPlayer(player);
    }

    public void draw(Batch batch, float alpha){
        gauge.draw(batch, getX(), getY(), getWidth(), getHeight());
        for(int i = 0; i < player.getHpCurrent(); i++){
            float xAdjust = getX() + gauge.getLeftWidth() + (notch.getTotalWidth() * notchMult) * i + 1;
            float yAdjust = getY() + vGaugeOffset;
            notch.draw(batch, xAdjust, yAdjust, notch.getTotalWidth() * notchMult, notch.getTotalHeight());
        }
    }

    public Player getPlayer() {
        return player;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }
}
