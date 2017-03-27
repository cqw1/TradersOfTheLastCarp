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
import com.totlc.Actors.damage.FireStream;
import com.totlc.AssetList;
import com.totlc.Directionality;

public class FireTrap extends ATrap {

    // Asset and animation constants.
    private Texture trapTexture;
    private TextureAtlas eyeTextureAtlas;
    private Animation<TextureRegion> trapAnimation;
    private long duration = 3000; // in millis

    private Sound fireSound;

    // Fire Stream variables.
    private FireStream fire;
    private long range; //TODO: FireRange should be included in fire stream no?

    // Trap defaults
    public static float width = 110;
    public static float height = 100;
    public static long defaultRange = 750;
    public static long delay = 650;

    public FireTrap(AssetManager assetManager, float x, float y) {
        this(assetManager, x, y, defaultRange, delay);
    }

    public FireTrap(AssetManager assetManager, float x, float y, Directionality facing) {
        this(assetManager, x, y, defaultRange, delay, facing);
    }

    public FireTrap(AssetManager assetManager, float x, float y, long range, long delay) {
        this(assetManager, x, y, range, delay, Directionality.RIGHT);
    }

    public FireTrap(AssetManager assetManager, float x, float y, long range, long delay, Directionality facing) {
        super(assetManager, new Rectangle(x, y, width, height), delay);
        initDirection(facing);
        eyeTextureAtlas = assetManager.get(AssetList.EYE_GLOW.toString());
        trapAnimation = new Animation<TextureRegion>(1 / 24f, eyeTextureAtlas.getRegions());
        fireSound = Gdx.audio.newSound(Gdx.files.internal("sounds/fire0.mp3"));
        setDuration(duration);
        setRange(range);
    }

    public void initDirection(Directionality facing) {
        setIsFacing(facing);
        if (getIsFacing().isFacingLeft()){
            // TODO: Reinitialize the moving hit-box.
            trapTexture = getAssetManager().get(AssetList.FIRE_TRAP_LEFT.toString());
            moveHitBox(getHitBoxWidth() * 0.35f, 0);
        } else if (getIsFacing().isFacingDown()){
            trapTexture = getAssetManager().get(AssetList.FIRE_TRAP_DOWN.toString());
        } else {
            trapTexture = getAssetManager().get(AssetList.FIRE_TRAP_RIGHT.toString());
        }
    }

    @Override
    public void activate() {
        fire = new FireStream(getAssetManager(), this, getX(), getY(), getRange(), 0, getIsFacing());
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
            fire.getHitBox().setScale(0, 0);
            fire.endEffect();
            fireSound.stop();
        }
    }

    @Override
    public void draw(Batch batch, float alpha) {
        batch.draw(trapTexture, getX(), getY());
        if (isActive() || isSetup()) {
            if (getIsFacing().isFacingLeft()){
                batch.draw(trapAnimation.getKeyFrame(getAnimationTime(), true), (float)getHitBoxCenter().getX() - 4, (float)getHitBoxCenter().getY());
            } else if (getIsFacing().isFacingDown()){
                batch.draw(trapAnimation.getKeyFrame(getAnimationTime(), true), (float)getHitBoxCenter().getX() - 43, (float)getHitBoxCenter().getY());
                batch.draw(trapAnimation.getKeyFrame(getAnimationTime(), true), (float)getHitBoxCenter().getX() + 25, (float)getHitBoxCenter().getY());
            } else {
                batch.draw(trapAnimation.getKeyFrame(getAnimationTime(), true), (float)getHitBoxCenter().getX() - 10, (float)getHitBoxCenter().getY());
            }
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
