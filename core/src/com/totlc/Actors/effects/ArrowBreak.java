package com.totlc.Actors.effects;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.ParticleEffect;
import com.badlogic.gdx.graphics.g2d.ParticleEmitter;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.totlc.AssetList;

import java.awt.geom.Point2D;

public class ArrowBreak extends AEffect{

    // Texture information
    private TextureAtlas particleAtlas;
    private ParticleEffect arrowBreak;

    public ArrowBreak(AssetManager assetManager, Rectangle r) {
        super(assetManager, r);
    }

    @Override
    public void draw(Batch batch, float alpha) {

    }

    @Override
    public boolean collidesWith(Actor otherActor) {
        return false;
    }

    @Override
    public void endCollidesWith(Actor otherActor) {

    }

    private void loadAssets(AssetManager assetManager, long d){
        particleAtlas = assetManager.get(AssetList.PARTICLES.toString());
        arrowBreak = new ParticleEffect();
        arrowBreak.setPosition(getX(), getY());
        arrowBreak.load(Gdx.files.internal(AssetList.ARROW_BREAK.toString()), particleAtlas);
        ParticleEmitter point = arrowBreak.findEmitter("arrowpoint");
        ParticleEmitter shaft = arrowBreak.findEmitter("arrowshaft");
        
        // Configure effect duration.

        arrowBreak.start();
    }
}
