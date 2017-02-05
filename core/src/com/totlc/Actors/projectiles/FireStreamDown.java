package com.totlc.Actors.projectiles;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.g2d.ParticleEmitter;
import com.totlc.Actors.TotlcObject;

public class FireStreamDown extends FireStream {
    public FireStreamDown(AssetManager assetManager, TotlcObject actor, float x, float y, long range, int damageType) {
        super(assetManager, actor, x, y, 59, range / 2, range, damageType);
        moveHitBox(-getHitBoxWidth() * 0.5f, -getHitBoxHeight());
        setxOffset(0);
        setyOffset(-getFollowMe().getHitBoxHeight() * 0.15f);
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
            p.getAngle().setHighMax(p.getAngle().getHighMax() + 270);
            p.getAngle().setHighMin(p.getAngle().getHighMin() + 270);
            p.getAngle().setLow(p.getAngle().getLowMin() + 270, p.getAngle().getLowMax() + 270);
        }
    }
}
