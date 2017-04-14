package com.totlc.Actors.effects;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.ParticleEffect;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.totlc.Actors.players.Player;
import com.totlc.AssetList;
import com.totlc.TradersOfTheLastCarp;

public class Confetti extends AEffect {

    private TextureAtlas partycleAtlas;
    private ParticleEffect confetti;

    public Confetti(AssetManager assetManager) {
        super(assetManager, new Rectangle(0, 0, 1, 1));
        partycleAtlas = assetManager.get(AssetList.PARTYCLES.toString());
        confetti = new ParticleEffect();
        confetti.load(Gdx.files.internal(AssetList.CONFETTI.toString()), partycleAtlas);
        confetti.start();
        confetti.setPosition(TradersOfTheLastCarp.CONFIG_WIDTH / 2, TradersOfTheLastCarp.CONFIG_HEIGHT);
    }

    public Confetti(AssetManager assetManager, Player player){
        this(assetManager);
        confetti.findEmitter("goldfish").setMaxParticleCount(player.getGoldfishCount() * 10);
        confetti.findEmitter("goldfish").getEmission().setHigh(player.getGoldfishCount());
    }

    @Override
    public void act(float deltaTime){
        increaseAnimationTime(deltaTime);
        if(confetti.isComplete()){
            remove();
        }
    }

    @Override
    public void draw(Batch batch, float alpha) {
        confetti.draw(batch, Gdx.graphics.getDeltaTime());
    }

    @Override
    public boolean collidesWith(Actor otherActor) {
        return false;
    }

    @Override
    public void endCollidesWith(Actor otherActor) {

    }
}
