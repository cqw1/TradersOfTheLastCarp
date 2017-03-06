package com.totlc.Actors.players;

import com.badlogic.gdx.assets.AssetManager;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.totlc.AssetList;

public class Colorado extends PlayableCharacter {

    public Colorado(AssetManager assetManager, float x, float y) {
        super(assetManager, x, y);
    }

    @Override
    protected void initTextures(AssetManager assetManager){
        // Standing Textures.
        stand = assetManager.get(AssetList.JANE_STAND.toString());

        //Walking Textures and Animations.
        walk_side = assetManager.get(AssetList.JANE_WALK_SIDE.toString());
        walk_animation_side = new Animation<TextureRegion>(1 / 12f, walk_side.getRegions());

        // Cheer Textures and Animation.

        // Attacking Textures and Animation.

        // Head Textures.
        head = assetManager.get(AssetList.JANE_HEAD.toString());

        // Whip initialization.

    }
}
