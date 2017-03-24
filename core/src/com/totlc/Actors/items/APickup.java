package com.totlc.Actors.items;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.totlc.Actors.Player;
import com.totlc.Actors.TotlcObject;
import com.totlc.Actors.Character;
import com.totlc.AssetList;

/**
 * Abstract class for items that can be picked up by the player.
 * Include inventory items as well as health, keys, and powerups.
 */
public abstract class APickup extends TotlcObject {

    public APickup(AssetManager assetManager, Rectangle r) {
        super(assetManager, r);
    }

    public abstract void pickup(Character p);

    public boolean collidesWith(Actor otherActor) {
        if (otherActor instanceof Player){
            pickup((Character) otherActor);
            return true;
        }
        return false;
    }
}
