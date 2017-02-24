package com.totlc.Actors.damage;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.ParticleEffect;
import com.badlogic.gdx.graphics.g2d.ParticleEmitter;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.totlc.Actors.TotlcObject;
import com.totlc.AssetList;
import com.totlc.Directionality;

public class FireStream extends Damage {

    // Texture information
    private TextureAtlas particleAtlas;
    private ParticleEffect fire;

    // Positioning information.
    private TotlcObject followMe;
    private float xOffset = 0;
    private float yOffset = 0;
    private static int damage = 1;
    private static int streamWidth = 50;

    // Particle information.
    private long range;

    public FireStream(AssetManager assetManager, TotlcObject actor, float x, float y, long range, int damageType) {
        super(assetManager, new Rectangle(x, y, range / 2, streamWidth), 1, damageType);
        setIsFacing(Directionality.RIGHT);
        setDamageType(damageType);
        setRange(range);
        setFollowMe(actor);
        loadAssets(assetManager);
        fire.setPosition(getX(), getY());
    }

    public FireStream(AssetManager assetManager, TotlcObject actor, float x, float y, long range, int damageType, Directionality facing) {
        super(assetManager, new Rectangle(x, y, range / 2, streamWidth), 1, damageType);
        setIsFacing(facing);
        setDamageType(damageType);
        setRange(range);
        setFollowMe(actor);
        loadAssets(assetManager);
        fire.setPosition(getX(), getY());
    }

    @Override
    public void act(float deltaTime){
        if (System.currentTimeMillis() > getTimeToApplyDamage() + getDamageInterval()) {
            setTimeToApplyDamage(System.currentTimeMillis());
        }
        increaseAnimationTime(deltaTime);
        moveRelative();
        fire.setPosition(getX(), getY());
        if (fire.isComplete()){
            remove();
        }
    }

    public void moveRelative() {
        moveAbs((float)getFollowMe().getHitBoxCenter().getX() + getxOffset(), (float)getFollowMe().getHitBoxCenter().getY() + getyOffset());
    }

    @Override
    public void draw(Batch batch, float alpha) {
        fire.draw(batch, Gdx.graphics.getDeltaTime());
    }

    @Override
    public boolean collidesWith(Actor otherActor) {
        return false;
    }

    @Override
    public void endCollidesWith(Actor otherActor) {

    }

    /**
     * Extend this method with orientation setup for directional fire streams. (Default direction RIGHT)
     * @param assetManager: Assetmanager to load assets from.
     */
    private void loadAssets(AssetManager assetManager){
        particleAtlas = assetManager.get(AssetList.PARTICLES.toString());
        fire = new ParticleEffect();
        fire.setPosition(getX(), getY());
        fire.load(Gdx.files.internal(AssetList.FLAMETHROWER.toString()), particleAtlas);
        // Start effect in concrete implementations.
        setDirection();
    }

    public void setDirection(){
        ParticleEmitter f = getFire().findEmitter("firebody");
        f.getLife().setHighMax(getRange());
        f.getLife().setHighMin(getRange());
        f.setMaxParticleCount((int) getRange() * 5);
        f.getEmission().setHighMax((float) getRange() * 0.4f);
        f.getEmission().setHighMin((float) getRange() * 0.4f);

        // Set Direction.
        if(getIsFacing().isFacingDown()){
            for (ParticleEmitter p : getFire().getEmitters()){
                p.getAngle().setHighMax(p.getAngle().getHighMax() + 270);
                p.getAngle().setHighMin(p.getAngle().getHighMin() + 270);
                p.getAngle().setLow(p.getAngle().getLowMin() + 270, p.getAngle().getLowMax() + 270);
            }
            setWidth(streamWidth);
            setHeight(range / 2);
            initHitBox();
            moveHitBox(-getHitBoxWidth() * 0.5f, -getHitBoxHeight());
            setxOffset(0);
            setyOffset(-getFollowMe().getHitBoxHeight() * 0.15f);
        } else if(getIsFacing().isFacingLeft()){
            for (ParticleEmitter p : getFire().getEmitters()){
                p.getAngle().setHighMax(p.getAngle().getHighMax() + 180);
                p.getAngle().setHighMin(p.getAngle().getHighMin() + 180);
                p.getAngle().setLow(p.getAngle().getLowMin() + 180, p.getAngle().getLowMax() + 180);
            }
            moveHitBox(-getHitBoxWidth(), -getHitBoxHeight() * 0.5f);
            setxOffset(-getFollowMe().getHitBoxWidth() * 0.6f);
            setyOffset(3);
        } else if(getIsFacing().isFacingUp()){
            for (ParticleEmitter p : getFire().getEmitters()){
                p.getAngle().setHighMax(p.getAngle().getHighMax() + 90);
                p.getAngle().setHighMin(p.getAngle().getHighMin() + 90);
                p.getAngle().setLow(p.getAngle().getLowMin() + 90, p.getAngle().getLowMax() + 90);
            }
        } else{
            // Default direction (RIGHT).
            // Unsupported direction (UP).
            moveHitBox(0, -getHitBoxHeight() * 0.5f);
            setxOffset(getFollowMe().getHitBoxWidth() * 0.6f);
            setyOffset(2);
        }
    }

    public void endEffect(){
        fire.allowCompletion();
    }

    public ParticleEffect getFire() {
        return fire;
    }

    public TotlcObject getFollowMe() {
        return followMe;
    }

    public void setFollowMe(TotlcObject followMe) {
        this.followMe = followMe;
    }

    public float getxOffset() {
        return xOffset;
    }

    public void setxOffset(float xOffset) {
        this.xOffset = xOffset;
    }

    public float getyOffset() {
        return yOffset;
    }

    public void setyOffset(float yOffset) {
        this.yOffset = yOffset;
    }

    public long getRange() {
        return range;
    }

    public void setRange(long range) {
        this.range = range;
    }
}
