package com.totlc.Actors.players;

import com.badlogic.gdx.assets.AssetManager;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.totlc.Actors.weapons.Whip;
import com.totlc.AssetList;

public class Colorado extends PlayableCharacter {

    public Colorado(AssetManager assetManager) {
        this(assetManager, 0, 0);
    }

    public Colorado(AssetManager assetManager, float x, float y) {
        super(assetManager, x, y);
        setHeadXOffset(10);
        setHeadYOffset(33);
        setSway(2);
        setBob(3);

        setHpMax(6);
        setHpCurrent(getHpMax());
        setSpeed(240);
        setMaxVel(250);
    }

    @Override
    protected void initTextures(AssetManager assetManager){
        // Standing Textures.
        stand = assetManager.get(AssetList.JANE_STAND.toString());

        //Walking Textures and Animations.
        walk_side = assetManager.get(AssetList.JANE_WALK_SIDE.toString());
        walk_animation_side = new Animation<TextureRegion>(1 / 10f, walk_side.getRegions(), Animation.PlayMode.LOOP);
        walk_front = assetManager.get(AssetList.JANE_WALK_FRONT.toString());
        walk_animation_front = new Animation<TextureRegion>(1 / 10f, walk_front.getRegions(), Animation.PlayMode.LOOP);
        walk_back = assetManager.get(AssetList.JANE_WALK_BACK.toString());
        walk_animation_back = new Animation<TextureRegion>(1 / 10f, walk_back.getRegions(), Animation.PlayMode.LOOP);

        // Attacking Textures and Animation.
        whip_side = assetManager.get(AssetList.JANE_WHIP_SIDE.toString());
        whip_animation_side = new Animation<TextureRegion>(1.0f / whip_side.getRegions().size * 0.3f, whip_side.getRegions());
        whip_front = assetManager.get(AssetList.JANE_WHIP_FRONT.toString());
        whip_animation_front = new Animation<TextureRegion>(1.0f / whip_front.getRegions().size * 0.3f, whip_front.getRegions());
        whip_back = assetManager.get(AssetList.JANE_WHIP_BACK.toString());
        whip_animation_back = new Animation<TextureRegion>(1.0f / whip_back.getRegions().size * 0.3f, whip_back.getRegions());

        // Head Textures.
        head = assetManager.get(AssetList.JANE_HEAD.toString());

        // Whip initialization.
        setWeapon(new Whip(assetManager, this, 0.3f, 3000, AssetList.BLUE_WHIP_BACK.toString(), AssetList.BLUE_WHIP_FRONT.toString(), AssetList.BLUE_WHIP_LEFT.toString(), AssetList.BLUE_WHIP_RIGHT.toString()));
    }
}
