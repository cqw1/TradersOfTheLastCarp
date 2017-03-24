package com.totlc.Actors.items;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.*;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.totlc.Actors.Character;
import com.totlc.Actors.Player;
import com.totlc.Actors.effects.Impact;
import com.totlc.AssetList;

public class Goldfish extends APickup {

    // Texture and effect information.
    private TextureAtlas flopTextureAtlas, particleAtlas;
    private Animation<TextureRegion> flopAnimation;
    private ParticleEffect sparkle;
    private ParticleEffect splash;

    private static float width = 72;
    private static float height = 72;

    private static float scale = 0.5f;

    private float angle;
    private boolean rotate;
    private long rotateLimit = 180;
    private long timestamp;
    private long angleDelta;

    public Goldfish(AssetManager assetManager,float x, float y) {
        super(assetManager, new Rectangle(x, y, width * scale, height * scale));
        this.moveHitBox(width * scale * 0.5f, height * scale * 0.5f);
        this.angle = -10;
        this.angleDelta = 1;
        this.rotate = false;
        this.timestamp = System.currentTimeMillis();

        flopTextureAtlas = assetManager.get(AssetList.GOLDFISH.toString());
        flopAnimation = new Animation<TextureRegion>(1/16f, flopTextureAtlas.getRegions());
        particleAtlas = assetManager.get(AssetList.PARTICLES.toString());
        sparkle = new ParticleEffect();
        sparkle.setPosition((float) getHitBoxCenter().getX(), (float) getHitBoxCenter().getY());
        sparkle.load(Gdx.files.internal(AssetList.SPARKLE.toString()), particleAtlas);
        splash = new ParticleEffect();
        splash.setPosition((float) getHitBoxCenter().getX(), (float) getHitBoxCenter().getY());
        splash.load(Gdx.files.internal(AssetList.SPLASH.toString()), particleAtlas);

        for (int i = 0; i < flopTextureAtlas.getRegions().size; i++){
           flopTextureAtlas.getRegions().get(i).getTexture().setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
        }

        sparkle.start();
        splash.start();
    }

    @Override
    public void pickup(Character p) {
        Sound sound = Gdx.audio.newSound(Gdx.files.internal(AssetList.GOLDFISH_SOUND.toString()));
        sound.play(1.0f);
        ((Player) p).setGoldfishCount(((Player) p).getGoldfishCount() + 1);
    }

    @Override
    public void act(float deltaTime){
        super.act(deltaTime);
        increaseAnimationTime(deltaTime);
        if (rotate) {
            angle += angleDelta;
        } else{
            angle -= angleDelta;
        }
        long timeElapsed = System.currentTimeMillis() - timestamp;

        if (timeElapsed > rotateLimit * 0.3f) {
            if (timeElapsed > rotateLimit) {
                timestamp = System.currentTimeMillis();
                rotate = !rotate;
            }
            angleDelta = 1;
        } else {
            angleDelta = 8;
        }
        sparkle.setPosition((float) getHitBoxCenter().getX(), (float) getHitBoxCenter().getY());
        splash.setPosition((float) getHitBoxCenter().getX(), (float) getHitBoxCenter().getY());
    }

    @Override
    public void draw(Batch batch, float alpha) {
        batch.draw(flopAnimation.getKeyFrame(getAnimationTime(), true), getX(), getY(),
                flopTextureAtlas.getRegions().get(0).getRegionWidth() / 2, flopTextureAtlas.getRegions().get(0).getRegionHeight() / 2,
                flopTextureAtlas.getRegions().get(0).getRegionWidth(), flopTextureAtlas.getRegions().get(0).getRegionHeight(), scale, scale, angle);
        sparkle.draw(batch, Gdx.graphics.getDeltaTime());
        splash.draw(batch, Gdx.graphics.getDeltaTime());
    }

    @Override
    public boolean collidesWith(Actor otherActor) {
        if (otherActor instanceof Player) {
            pickup((Character) otherActor);
            return true;
        }

        return false;
    }

    @Override
    public void endCollidesWith(Actor otherActor) {

    }
}
