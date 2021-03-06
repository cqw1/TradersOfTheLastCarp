package com.totlc.Actors.damage;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.ParticleEffect;
import com.badlogic.gdx.graphics.g2d.ParticleEmitter;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.totlc.Actors.effects.BoulderBreak;
import com.totlc.Actors.effects.Impact;
import com.totlc.Actors.enemies.AEnemy;
import com.totlc.Actors.terrain.AWall;
import com.totlc.Actors.terrain.Rock;
import com.totlc.AssetList;
import com.totlc.TradersOfTheLastCarp;

import java.awt.*;

public class Boulder extends Damage{

    private float destinationHeight;
    private boolean rolling = false;
    private boolean removeFlag = false;
    private float angle = 0;
    private Point boulderVel = new Point(-300, 0);

    private TextureAtlas particleAtlas;
    private ParticleEffect debris;
    private Sound rollingSound;
    private Texture shadow;

    private static float shadowSize = 3;

    // Not generated with factory.
    public Boulder(AssetManager assetManager, float x, float y, int attack, int damageType) {
        this(assetManager, new Rectangle(x, y, 300, 300), attack, damageType);
    }

    public Boulder(AssetManager assetManager, Rectangle r, int attack, int damageType) {
        super(assetManager, r, attack, damageType);
        this.destinationHeight = getY();
        moveAbs(getX(), TradersOfTheLastCarp.CONFIG_HEIGHT);
        getHitBox().setScale(0, 0);
        moveHitBox(36, 36);
        setScaleFactor(1);
        setTexture(new Texture(Gdx.files.internal(AssetList.BOULDER.toString())));
        shadow = getAssetManager().get(AssetList.SHADOW.toString());
        shadow.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
        setVel(new Point(0, -800));

        particleAtlas = assetManager.get(AssetList.PARTICLES.toString());
        debris = new ParticleEffect();
        debris.load(Gdx.files.internal(AssetList.DEBRIS_TRAIL.toString()), particleAtlas);
        debris.start();

        rollingSound = Gdx.audio.newSound(Gdx.files.internal("sounds/rumble0.wav"));
    }

    @Override
    public void act(float delta) {
        moveUnit(delta);
        if (getVel().getX() > 0){
            angle -= 3;
        } else if (getVel().getX() < 0){
            angle += 3;
        }
        if(getY() <= destinationHeight && !rolling){
            // Start rolling.
            setVel(boulderVel);
            getHitBox().setScale(0.8f, 0.7f);
            rolling = true;
            Sound impactSound = Gdx.audio.newSound(Gdx.files.internal("sounds/bouldercrash0.mp3"));
            impactSound.play(0.5f);
            rollingSound.play(1f);
        }
        debris.setPosition((float)getCenter().getX(), getY() + 10);
    }

    @Override
    public void draw(Batch batch, float alpha) {
        debris.draw(batch, Gdx.graphics.getDeltaTime());
        if (!removeFlag) {
            batch.draw(shadow, (float) getCenter().getX() - (shadow.getWidth() * shadowSize * getShadowScale()) * 0.5f, this.destinationHeight - (shadow.getHeight() * shadowSize * getShadowScale()) * 0.5f,
                    shadow.getWidth() * shadowSize * getShadowScale(),
                    shadow.getHeight() * shadowSize * getShadowScale());
            batch.draw(getTexture(), getX(), getY(), getWidth() / 2, getHeight() / 2, getWidth(), getHeight(), getScaleFactor(), getScaleFactor(), angle, 0, 0, 300, 300, false, false);
        }
    }

    @Override
    public boolean collidesWith(Actor otherActor) {
       if (otherActor instanceof AEnemy){
            if (((AEnemy) otherActor).isInvincible()){
                breakEffect();
            } else{
                getStage().addActor(new Impact(getAssetManager(), otherActor.getX(), otherActor.getY()));
            }
        } else if (otherActor instanceof AWall || otherActor instanceof Rock){
            if (rolling){
                breakEffect();
            }
        }
        return false;
    }

    private void breakEffect(){
        Sound impactSound = Gdx.audio.newSound(Gdx.files.internal("sounds/bouldercrash1.mp3"));
        impactSound.play(0.5f);
        rollingSound.stop();
        rollingSound.dispose();
        getStage().addActor(new BoulderBreak(getAssetManager(), (float) getHitBoxCenter().getX(), (float) getHitBoxCenter().getY()));
        allowRemoval();
    }

    private void allowRemoval(){
        removeFlag = true;
        getHitBox().setScale(0, 0);
        for (ParticleEmitter p : debris.getEmitters()){
            p.allowCompletion();
        }
    }

    private boolean delayRemove() {
       return debris.isComplete() && super.remove();
    }

    private float getShadowScale(){
        return (TradersOfTheLastCarp.CONFIG_HEIGHT - getY()) / (TradersOfTheLastCarp.CONFIG_HEIGHT - destinationHeight);
    }

    @Override
    public void endCollidesWith(Actor otherActor) {

    }

    public void setBoulderVel(Point p) {
        boulderVel = p;
    }
}
