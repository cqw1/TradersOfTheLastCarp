package com.totlc.Actors.projectiles;

import com.badlogic.gdx.assets.AssetManager;

import com.badlogic.gdx.graphics.g2d.ParticleEmitter;
import com.totlc.Actors.TotlcObject;

public class FireStreamRight extends FireStream{

    public FireStreamRight(AssetManager assetManager, TotlcObject actor, float x, float y, long range, int damageType) {
        super(assetManager, actor, x, y, range / 2, 50, range, damageType);
        moveHitBox(0, -getHitBoxHeight() * 0.5f);
        setxOffset(getFollowMe().getHitBoxWidth() * 0.6f);
        setyOffset(0);
    }

    @Override
    public void setDirection() {
        ParticleEmitter e = getFire().findEmitter("firebody");
        e.getLife().setHighMax(getRange());
        e.getLife().setHighMin(getRange());
        e.setMaxParticleCount((int) getRange() * 5);
        e.getEmission().setHighMax((float) getRange() * 0.4f);
        e.getEmission().setHighMin((float) getRange() * 0.4f);

        //Default direction. No additional configuration necessary.
    }
}
