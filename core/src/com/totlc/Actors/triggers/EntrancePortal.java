package com.totlc.Actors.triggers;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.totlc.Actors.Character;
import com.totlc.Actors.TotlcObject;
import com.totlc.Actors.damage.Damage;
import com.totlc.Actors.traps.ATrap;

public class EntrancePortal extends ATrigger {

    private static float width = 64;
    private static float height = 128;

    public EntrancePortal(AssetManager assetManager, float x, float y) {
        this(assetManager, new Rectangle(x, y, width, height));
    }

    public EntrancePortal(AssetManager assetManager, Rectangle r) {
        super(assetManager, r);
    }

    @Override
    public void draw(Batch batch, float alpha) {

    }

    public boolean collidesWith(Actor otherActor) {

        //Only activate if encountering a character or damage in motion
        if (otherActor instanceof Character ||
                (otherActor instanceof Damage &&
                        ((Damage)otherActor).isInMotion())) {
            for (ATrap trap : getListOfTraps()) {
                trap.setup((TotlcObject)otherActor);
            }
            handleTrigger(true, otherActor);
        }

        return false;
    }

    public void endCollidesWith(Actor otherActor) {}
}
