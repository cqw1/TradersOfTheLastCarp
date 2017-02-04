package com.totlc.Actors.projectiles;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.ParticleEffect;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.totlc.Actors.TotlcObject;
import com.totlc.AssetList;

public abstract class FireStream extends Projectile {

    // Texture information
    private TextureAtlas particleAtlas;

    private ParticleEffect fire;

    // Positioning information.
    private TotlcObject followMe;
    private float xOffset = 0;
    private float yOffset = 0;

    // Particle information.
    private long range;

    public FireStream(AssetManager assetManager, TotlcObject actor, float x, float y, float width, float height, long range, int damageType) {
        super(assetManager, new Rectangle(x, y, width, height));
        setDamageType(damageType);
        setAttack(1);
        setRange(range);
        loadAssets(assetManager);
        setFollowMe(actor);
        fire.setPosition(getX(), getY());
    }

    @Override
    public void act(float deltaTime){
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

    public abstract void setDirection();

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
