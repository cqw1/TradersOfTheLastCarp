package com.totlc.Actors.traps;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.totlc.Actors.effects.Exclamation;
import com.totlc.Actors.projectiles.FireStream;
import com.totlc.Actors.projectiles.FireStreamRight;
import com.totlc.AssetList;

public class FireTrapRight extends ATrap {

    // Asset and animation constants.
    private Texture trapTexture;
    private TextureAtlas eyeTextureAtlas;
    private Animation<TextureRegion> trapAnimation;
    private long duration = 10000; // in millis

    private Sound fireSound;

    // Fire Stream variables.
    private FireStream fire;
    private long range;

    public FireTrapRight(AssetManager assetManager, float x, float y, long range, long delay) {
        super(assetManager, new Rectangle(x, y, 110, 100), delay);
        trapTexture = assetManager.get(AssetList.FIRE_TRAP_RIGHT.toString());
        eyeTextureAtlas = assetManager.get(AssetList.EYE_GLOW.toString());
        trapAnimation = new Animation<TextureRegion>(1 / 24f, eyeTextureAtlas.getRegions());
        fireSound = Gdx.audio.newSound(Gdx.files.internal("sounds/fire0.mp3"));
        setDuration(duration);
        setRange(range);
    }

    @Override
    public void activate() {
        fire = new FireStreamRight(getAssetManager(), this, getX(), getY(), getRange(), 0);
        fireSound.play();
        getStage().addActor(fire);
    }

    @Override
    public void setup() {
        if (!isSetup()) {
            // If someone's already triggered this trap
            getStage().addActor(new Exclamation(getAssetManager(), (float) getHitBoxCenter().getX() + getHitBoxWidth() / 2, (float) getHitBoxCenter().getY() + getHitBoxHeight() / 3));
            setStartTime(System.currentTimeMillis());
            setSetup(true);
        }
    }

    @Override
    public void act(float deltaTime){
        delayActivation();
        increaseAnimationTime(deltaTime);
        if (isActive() && System.currentTimeMillis() > (getStartTime() + getDuration())) {
            // If the trap was active and we've already passed our delay and the allotted time for displaying eyebrows.
            setActive(false);
            setSetup(false);
            fire.endEffect();
            fireSound.stop();
        }
    }

    @Override
    public void draw(Batch batch, float alpha) {
        batch.draw(trapTexture, getX(), getY());
        if (isActive() || isSetup()) {
            batch.draw(trapAnimation.getKeyFrame(getAnimationTime(), true), (float)getHitBoxCenter().getX() - 10, (float)getHitBoxCenter().getY());
        }
    }

    public long getDuration() {
        return duration;
    }

    public void setDuration(long duration) {
        this.duration = duration;
    }

    public long getRange() {
        return range;
    }

    public void setRange(long range) {
        this.range = range;
    }
}
