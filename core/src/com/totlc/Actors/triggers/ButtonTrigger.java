package com.totlc.Actors.triggers;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.totlc.AssetList;
import com.totlc.Actors.Player;
import com.totlc.Actors.enemies.AEnemy;
import com.totlc.Actors.traps.ATrap;

public class ButtonTrigger extends ATrigger {

    // Asset and animation constants.
    private TextureAtlas buttonTextureAtlas;
    private Animation<TextureRegion> buttonAnimation;

    public ButtonTrigger(AssetManager assetManager, String asset, int x, int y){
        super(assetManager, x, y);
        setHeight(32);
        setWidth(32);
        initHitBox();

        setSpeed(200);

        setMovingLeft(false);
        setMovingRight(false);
        setMovingUp(false);
        setMovingDown(false);

        assetManager.load(AssetList.PLATE_BROWN.toString(), TextureAtlas.class);
    }

    public void act(float deltaTime){
        increaseAnimationTime(deltaTime);
        if(!isTriggered()) {
            setAnimationTime(0);
        }
    }

    public void draw(Batch batch, float delta) {
        AssetManager assetManager = getAssetManager();

        if (assetManager.update() && !assetsLoaded()) {
            // Done loading. Instantiate all assets
            setAssetsLoaded(true);

            buttonTextureAtlas = assetManager.get(AssetList.PLATE_BROWN.toString());
            buttonAnimation = new Animation<TextureRegion>(1 / 24f, buttonTextureAtlas.getRegions());
        }

        if (assetsLoaded()) {
            if(isTriggered()){
                batch.draw(buttonAnimation.getKeyFrame(getAnimationTime(), false), getX(), getY());
            }
        }
    }

    public boolean collidesWith(Actor otherActor) {
        if (otherActor instanceof Player ||
                otherActor instanceof AEnemy) {

            if (!isTriggered()) {
                Sound sound = Gdx.audio.newSound(Gdx.files.internal("sounds/trap_activation.mp3"));
                sound.play(1.0f);

                for (ATrap trap : getListOfTraps()) {
                    trap.activate();
                }
            }

            handleTrigger(true, otherActor);
        }

        return false;
    }
}
