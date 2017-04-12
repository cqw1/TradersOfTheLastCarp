package com.totlc.Actors.weapons;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.totlc.Actors.players.Player;

public class Whip extends AWeapon {
    private Player player;

    private long stunPeriod; // in millis

    public Whip(AssetManager assetManager, Player player, float attackTime, long stunDuration, String upAsset, String downAsset, String leftAsset, String rightAsset) {
        super(assetManager, player, 0, attackTime, upAsset, downAsset, leftAsset, rightAsset);
        setStunPeriod(stunDuration);
        this.player = player;
    }

    public Whip(AssetManager assetManager, Player player, float attackTime, String upAsset, String downAsset, String leftAsset, String rightAsset) {
        this(assetManager, player, attackTime, 1000, upAsset, downAsset, leftAsset, rightAsset);
    }

    @Override
    public boolean collidesWith(Actor otherActor) {
        return false;
    }

    @Override
    public void endCollidesWith(Actor otherActor) {}

    public long getStunPeriod() {
        return stunPeriod;
    }

    public void setStunPeriod(long stunPeriod) {
        this.stunPeriod = stunPeriod;
    }
}
