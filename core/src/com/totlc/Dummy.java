package com.totlc;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;

public class Dummy extends Actor {
    TextureAtlas textureAtlas = new TextureAtlas(Gdx.files.internal("dummy_spritesheet/dummy_spritesheet.atlas"));
    Animation<TextureRegion> animation = new Animation(1/12f, textureAtlas.getRegions());

    Texture texture = new Texture(Gdx.files.internal("dummy_spritesheet/0.png"));

    public void draw(Batch batch, float alpha) {
        batch.draw(texture, 0, 0);
    }
}
