package com.totlc.Actors.players;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.totlc.AssetList;

public class Louisiana extends PlayableCharacter {

    public Louisiana(AssetManager assetManager, float x, float y) {
        super(assetManager, x, y);
    }

    @Override
    protected void initTextures(AssetManager assetManager){
        // Standing Textures.
        stand = assetManager.get(AssetList.JACK_STAND.toString());

        //Walking Textures and Animations.
        walk_side = assetManager.get(AssetList.JACK_WALK_SIDE.toString());
        walk_animation_side = new Animation<TextureRegion>(1 / 12f, walk_side.getRegions());

        // Cheer Textures and Animation.

        // Attacking Textures and Animation.

        // Head Textures.
        head = assetManager.get(AssetList.JACK_HEAD.toString());

        // Whip initialization.

    }
}
