package com.totlc.Actors.items;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.ParticleEffect;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.totlc.Actors.Character;
import com.totlc.AssetList;

import java.awt.geom.Point2D;

/**
 * Health pickup item. Restores missing health when collided with, then disappears.
 */
public class Health extends APickup {

    // Texture information.
    private TextureRegion heart;
    private TextureAtlas particleAtlas;
    private ParticleEffect health;
    private Point2D textureDimensions;
    private float scale = 0.5f;

    public Health(AssetManager assetManager, float x, float y) {
        super(assetManager, new Rectangle(x, y, 64, 64));
        heart = assetManager.get(AssetList.ITEM_PACK.toString(), TextureAtlas.class).findRegion("1Heart");
        heart.getTexture().setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
        particleAtlas = assetManager.get(AssetList.PARTICLES.toString());
        textureDimensions = new Point2D.Float(heart.getRegionWidth(), heart.getRegionHeight());

        health = new ParticleEffect();
        health.setPosition(getX() + (float)textureDimensions.getX() * scale / 2, getY() + (float)textureDimensions.getY() * scale / 2);
        health.load(Gdx.files.internal(AssetList.REGEN.toString()), particleAtlas);
        health.start();
    }

    @Override
    public void act(float deltaTime){
        health.setPosition(getX() + (float)textureDimensions.getX() * scale / 2, getY() + (float)textureDimensions.getY() * scale / 2);
    }

    @Override
    public void draw(Batch batch, float alpha) {
        batch.draw(heart, getX(), getY(), 0, 0, heart.getRegionWidth(), heart.getRegionHeight(), scale, scale, 0);
        health.draw(batch, Gdx.graphics.getDeltaTime());
    }

    @Override
    public void endCollidesWith(Actor otherActor) {

    }

    @Override
    public void pickup(Character p) {
        Sound sound = Gdx.audio.newSound(Gdx.files.internal(AssetList.HEAL_SOUND.toString()));
        sound.play(0.75f);

        if (p.getHpCurrent() < p.getHpMax()){
            p.setHpCurrent(Math.min(p.getHpCurrent() + 2, p.getHpMax()));
        }
    }

}
