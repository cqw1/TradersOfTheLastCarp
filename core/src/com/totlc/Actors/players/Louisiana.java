package com.totlc.Actors.players;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.totlc.Actors.weapons.Whip;
import com.totlc.AssetList;

public class Louisiana extends PlayableCharacter {

    public Louisiana(AssetManager assetManager, float x, float y) {
        super(assetManager, x, y);
        setHeadXOffset(16);
        setHeadYOffset(30);
        setSway(1);
        setBob(3);

        setHpMax(5);
        setHpCurrent(getHpMax());
        setSpeed(300);
        setMaxVel(300);
    }

    @Override
    protected void initTextures(AssetManager assetManager){
        // Standing Textures.
        stand = assetManager.get(AssetList.JACK_STAND.toString());

        //Walking Textures and Animations.
        walk_side = assetManager.get(AssetList.JACK_WALK_SIDE.toString());
        walk_animation_side = new Animation<TextureRegion>(1 / 12f, walk_side.getRegions(), Animation.PlayMode.LOOP);
        walk_front = assetManager.get(AssetList.JACK_WALK_FRONT.toString());
        walk_animation_front = new Animation<TextureRegion>(1 / 12f, walk_front.getRegions(), Animation.PlayMode.LOOP);
        walk_back = assetManager.get(AssetList.JACK_WALK_BACK.toString());
        walk_animation_back = new Animation<TextureRegion>(1 / 12f, walk_back.getRegions(), Animation.PlayMode.LOOP);

        // Attacking Textures and Animation.
        whip_side = assetManager.get(AssetList.JACK_WHIP_SIDE.toString());
        whip_animation_side = new Animation<TextureRegion>(1 / 12f, whip_side.getRegions());

        // Head Textures.
        head = assetManager.get(AssetList.JACK_HEAD.toString());

        // Whip initialization.
        setWeapon(new Whip(assetManager, this, AssetList.WHIP_UP.toString(), AssetList.WHIP_DOWN.toString(), AssetList.ORANGE_WHIP_LEFT.toString(), AssetList.ORANGE_WHIP_RIGHT.toString()));
    }
}
