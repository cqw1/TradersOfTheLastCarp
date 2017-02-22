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
    private static float width = 32;
    private static float height = 32;

    public ButtonTrigger(AssetManager assetManager, float x, float y){
        super(assetManager, new Rectangle(x, y, width, height));

        buttonTextureAtlas = assetManager.get(AssetList.PLATE_BROWN.toString());
        buttonAnimation = new Animation<TextureRegion>(1 / 24f, buttonTextureAtlas.getRegions());

        moveHitBox(48, 48);
        moveRel(getX() - getHitBoxX(), getY() - getHitBoxY());
        setSpeed(200);
    }

    public ButtonTrigger(AssetManager assetManager, Rectangle r) {
        super(assetManager, r);

        buttonTextureAtlas = assetManager.get(AssetList.PLATE_BROWN.toString());
        buttonAnimation = new Animation<TextureRegion>(1 / 24f, buttonTextureAtlas.getRegions());

        moveHitBox(48, 48);
        setSpeed(200);
    }

    public void draw(Batch batch, float delta) {
        if (isTriggered()) {
            batch.draw(buttonAnimation.getKeyFrame(getAnimationTime(), false), getX(), getY());
        }
    }

    public boolean collidesWith(Actor otherActor) {
        return super.collidesWith(otherActor);
    }
}
