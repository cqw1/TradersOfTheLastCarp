package com.totlc.Actors.weapons;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.totlc.Actors.Player;

public class Whip extends AWeapon {
    private Player player;

    public Whip(AssetManager assetManager, Player player, String upAsset, String downAsset, String leftAsset, String rightAsset) {
        super(assetManager, player, 0, 0.5f, upAsset, downAsset, leftAsset, rightAsset);
        this.player = player;
    }


    @Override
    public boolean collidesWith(Actor otherActor) {
        return false;
    }

    @Override
    public void endCollidesWith(Actor otherActor) {}
}
