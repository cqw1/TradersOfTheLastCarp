package com.totlc.Actors.players;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.totlc.Actors.weapons.Whip;
import com.totlc.AssetList;

public class Louisiana extends PlayableCharacter {

    public Louisiana(AssetManager assetManager) {
        this(assetManager, 0, 0);
    }

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
        whip_animation_side = new Animation<TextureRegion>(1.0f /whip_side.getRegions().size * 0.3f, whip_side.getRegions());
        whip_front = assetManager.get(AssetList.JACK_WHIP_FRONT.toString());
        whip_animation_front = new Animation<TextureRegion>(1.0f /whip_front.getRegions().size * 0.3f, whip_front.getRegions());
        whip_back = assetManager.get(AssetList.JACK_WHIP_BACK.toString());
        whip_animation_back = new Animation<TextureRegion>(1.0f /whip_back.getRegions().size * 0.3f, whip_back.getRegions());

        // Head Textures.
        head = assetManager.get(AssetList.JACK_HEAD.toString());

        // Whip initialization.
        setWeapon(new Whip(assetManager, this, 0.3f, 2000, AssetList.ORANGE_WHIP_BACK.toString(), AssetList.ORANGE_WHIP_FRONT.toString(), AssetList.ORANGE_WHIP_LEFT.toString(), AssetList.ORANGE_WHIP_RIGHT.toString()));
    }

    @Override
    public void createWeapon(){
        super.createWeapon();
        if (getAttacking()){
            Sound sound = Gdx.audio.newSound(Gdx.files.internal("sounds/whip0.mp3"));
            sound.play(1.0f);
        } else{
            Sound sound = Gdx.audio.newSound(Gdx.files.internal("sounds/negative0.wav"));
            sound.play(1.0f);
        }
    }
}
