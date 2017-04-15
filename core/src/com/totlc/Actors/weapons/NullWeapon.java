package com.totlc.Actors.weapons;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.totlc.Actors.Character;

public class NullWeapon extends AWeapon {

    public NullWeapon(AssetManager assetManager, Character character, int attack, float attackingAnimationLength, String upAsset, String downAsset, String leftAsset, String rightAsset) {
        super(assetManager, character, attack, attackingAnimationLength, upAsset, downAsset, leftAsset, rightAsset);
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
}
