package com.totlc.Actors.projectiles;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.g2d.ParticleEmitter;
import com.totlc.Actors.TotlcObject;

public class FireStreamLeft extends FireStream{
    public FireStreamLeft(AssetManager assetManager, TotlcObject actor, float x, float y, long range, int damageType) {
        super(assetManager, actor, x, y, range / 2, 50, range, damageType);
        moveHitBox(-getHitBoxWidth(), -getHitBoxHeight() * 0.5f);
        setxOffset(-getFollowMe().getHitBoxWidth() * 0.5f);
        setyOffset(0);
    }

    @Override
    public void setDirection() {
        ParticleEmitter f = getFire().findEmitter("firebody");
        f.getLife().setHighMax(getRange());
        f.getLife().setHighMin(getRange());
        f.setMaxParticleCount((int) getRange() * 5);
        f.getEmission().setHighMax((float) getRange() * 0.4f);
        f.getEmission().setHighMin((float) getRange() * 0.4f);

        // Set Direction.
        for (ParticleEmitter p : getFire().getEmitters()){
            p.getAngle().setHighMax(p.getAngle().getHighMax() + 180);
            p.getAngle().setHighMin(p.getAngle().getHighMin() + 180);
            p.getAngle().setLow(p.getAngle().getLowMin() + 180, p.getAngle().getLowMax() + 180);
        }
    }
}
