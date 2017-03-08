package com.totlc.Actors.players;

import com.badlogic.gdx.assets.AssetManager;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.totlc.Actors.weapons.Whip;
import com.totlc.AssetList;

public class Colorado extends PlayableCharacter {

    public Colorado(AssetManager assetManager, float x, float y) {
        super(assetManager, x, y);
        setHeadXOffset(10);
        setHeadYOffset(33);
        setSway(2);
        setBob(3);
    }

    @Override
    protected void initTextures(AssetManager assetManager){
        // Standing Textures.
        stand = assetManager.get(AssetList.JANE_STAND.toString());

        //Walking Textures and Animations.
        walk_side = assetManager.get(AssetList.JANE_WALK_SIDE.toString());
        walk_animation_side = new Animation<TextureRegion>(1 / 12f, walk_side.getRegions(), Animation.PlayMode.LOOP);

        // Cheer Textures and Animation.

        // Attacking Textures and Animation.
        whip_side = assetManager.get(AssetList.JANE_WHIP_SIDE.toString());
        whip_animation_side = new Animation<TextureRegion>(1 / 12f, whip_side.getRegions());

        // Head Textures.
        head = assetManager.get(AssetList.JANE_HEAD.toString());

        // Whip initialization.
        setWeapon(new Whip(assetManager, this, AssetList.WHIP_UP.toString(), AssetList.WHIP_DOWN.toString(), AssetList.BLUE_WHIP_LEFT.toString(), AssetList.BLUE_WHIP_RIGHT.toString()));
    }
}
