package com.totlc.Actors.items;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.totlc.Actors.Character;
import com.totlc.Actors.Player;
import com.totlc.AssetList;

public class Key extends APickup {

    private TextureRegion key;
    private static float width = 40;
    private static float height = 64;
    private float scale = 0.5f;

    public Key(AssetManager assetManager, float x, float y) {
        super(assetManager, new Rectangle(x, y, width, height));
        key = assetManager.get(AssetList.ITEM_PACK.toString(), TextureAtlas.class).findRegion("0Key");
        key.getTexture().setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
    }

    @Override
    public void pickup(Character p) {
        // Add self to player inventory?
        ((Player)p).giveKey();

        Sound sound = Gdx.audio.newSound(Gdx.files.internal(AssetList.KEY_PICKUP_SOUND.toString()));
        sound.play(1.0f);
    }

    @Override
    public void draw(Batch batch, float alpha) {
        batch.draw(key, getX(), getY(), 0, 0, key.getRegionWidth(), key.getRegionHeight(), scale, scale, 0);
    }

    @Override
    public void endCollidesWith(Actor otherActor) {}
}
