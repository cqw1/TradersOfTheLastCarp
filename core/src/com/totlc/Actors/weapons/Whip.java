package com.totlc.Actors.weapons;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.totlc.Actors.Player;
import com.totlc.Actors.TotlcObject;
import com.totlc.AssetList;

public class Whip extends AWeapon {
    private AssetManager assetManager;
    private Player player;

    public Whip(AssetManager assetManager, Player player) {
        super(assetManager, player, 0.5f);

        this.assetManager = assetManager;
        this.player = player;

        setX(player.getX());
        setY(player.getY());
        initHitBox();

        setUpAsset(AssetList.WHIP_UP.toString());
        setDownAsset(AssetList.WHIP_DOWN.toString());
        setRightAsset(AssetList.WHIP_RIGHT.toString());
        setLeftAsset(AssetList.WHIP_LEFT.toString());
    }


    @Override
    public boolean collidesWith(Actor otherActor) {
        return false;
    }

    @Override
    public void endCollidesWith(Actor otherActor) {

    }
}
