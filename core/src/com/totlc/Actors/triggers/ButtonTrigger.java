package com.totlc.Actors.triggers;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.totlc.Actors.Player;
import com.totlc.Actors.enemies.Enemy;
import com.totlc.Actors.totlcObject;
import com.totlc.Actors.traps.ATrap;

public class ButtonTrigger extends ATrigger {

    public ButtonTrigger(AssetManager assetManager, String asset, int x, int y){
        setX(x);
        setY(y);
        setHeight(32);
        setWidth(32);
        setHitBox(new Rectangle(x, y, getWidth(), getHeight()));

        setSpeed(200);

        setMovingLeft(false);
        setMovingRight(false);
        setMovingUp(false);
        setMovingDown(false);

        setTexture(new Texture(Gdx.files.internal("dummy/0.png")));
        setAsset(asset);

        setAssetManager(assetManager);
    }

    public void draw(Batch batch, float delta) {
        AssetManager assetManager = getAssetManager();

        if (assetManager.update()) {
            batch.draw(getTexture(), getX(), getY());
        }
    }

    public boolean collidesWith(Actor otherActor) {
        if (otherActor instanceof Player ||
                otherActor instanceof Enemy) {

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
