package com.totlc.Actors;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.totlc.Directionality;
import com.totlc.TradersOfTheLastCarp;
import com.badlogic.gdx.math.Polygon;

import java.awt.geom.Point2D;

public abstract class TotlcObject extends Actor {

    /**
     * IMPORTANT: MAKE SURE TO SET THE WIDTH, HEIGHT, AND CALL initHitBox()
     */

    private float speed;

    //Image related fields
    private TextureAtlas textureAtlas;
    private AssetManager assetManager;

    Texture texture;
    Animation<TextureRegion> animation;
    String asset;
    float animationTime = 0;

    private boolean assetsLoaded = false;

    //Hit box related fields
    private Polygon hitBox;
    private float HEIGHT;
    private float WIDTH;

    private float friction = 0.0f;
    private Point2D acc = new Point2D.Double(0, 0);
    private Point2D vel = new Point2D.Double(0, 0);
    private float maxVel;

    private float knockback = 25;


    // Orientation and movement flags.
    private Directionality isFacing = Directionality.DOWN;
    private boolean isMovingLeft = false;
    private boolean isMovingRight = false;
    private boolean isMovingUp = false;
    private boolean isMovingDown = false;

    public TotlcObject(AssetManager assetManager, Rectangle r) {
        setX(r.getX());
        setY(r.getY());
        setWidth(r.getWidth());
        setHeight(r.getHeight());
        initHitBox();
        setAssetManager(assetManager);
    }

    public abstract void draw(Batch batch, float alpha);

    // Must return true if removing oneself, false otherwise.
    public abstract boolean collidesWith(Actor otherActor);

    public abstract void endCollidesWith(Actor otherActor);

    public boolean isMovingLeft() {
        return isMovingLeft;
    }

    public void setMovingLeft(boolean movingLeft) {
        isMovingLeft = movingLeft;
    }

    public boolean isMovingRight() {
        return isMovingRight;
    }

    public void setMovingRight(boolean movingRight) {
        isMovingRight = movingRight;
    }

    public boolean isMovingUp() {
        return isMovingUp;
    }

    public void setMovingUp(boolean movingUp) {
        this.isMovingUp = movingUp;
    }

    public boolean isMovingDown() {
        return isMovingDown;
    }

    public void setMovingDown(boolean movingDown) {
        isMovingDown = movingDown;
    }

    public Directionality getIsFacing() {
        return isFacing;
    }

    public void setIsFacing(Directionality isFacing) {
        this.isFacing = isFacing;
    }

    public void moveRel(float x, float y) {
        setX(getX() + x);
        setY(getY() + y);
        hitBox.translate(x, y);
    }

    public void moveAbs(float x, float y) {
        float translateX = x - getX();
        float translateY = y - getY();


        setX(x);
        setY(y);
        hitBox.translate(translateX, translateY);
    }

    public void moveHitBox(float x, float y) {
        hitBox.translate(x, y);
    }

    public void updateVelocity(){
        Point2D newVelocity = getVel();
        double newX = newVelocity.getX() * getFriction() + getAcc().getX();
        double newY = newVelocity.getY() * getFriction() + getAcc().getY();
        if (Math.abs(newX)  > getMaxVel()){
            newX = getMaxVel() * Math.signum(newX);
        }
        if (Math.abs(newY) > getMaxVel()){
            newY = getMaxVel() * Math.signum(newY);
        }
        setVel(new Point2D.Double(newX, newY));
    }

    public Polygon getHitBox() { return hitBox; }

    public float getHitBoxWidth() { return hitBox.getBoundingRectangle().width; }

    public float getHitBoxHeight() { return hitBox.getBoundingRectangle().height; }

    public float getHitBoxX() { return hitBox.getBoundingRectangle().x; }

    public float getHitBoxY() { return hitBox.getBoundingRectangle().y; }

    public Rectangle getRectangleHitBox() {
        return hitBox.getBoundingRectangle();
    }

    public void setHitBox(Polygon r) { hitBox = r; }

    public void initHitBox() {
        hitBox = new Polygon(new float[] {
                getX(), getY(),
                getX() + getWidth(), getY(),
                getX() + getWidth(), getY() + getHeight(),
                getX(), getY() + getHeight()
                });
        hitBox.setOrigin(getX() + getWidth() / 2, getY() + getHeight() / 2);
    }

    public float getFriction() {
        return friction;
    }

    public void setFriction(float friction) {
        this.friction = friction;
    }

    public Point2D getAcc() {
        return this.acc;
    }

    public void setAcc(Point2D acc) {
        this.acc = acc;
    }

    public Point2D getVel() {
        return vel;
    }

    public void setVel(Point2D vel) {
        this.vel = vel;
    }

    public float getMaxVel() {
        return maxVel;
    }

    public void setMaxVel(float maxVel) {
        this.maxVel = maxVel;
    }

    public float getKnockback() {
        return knockback;
    }

    public void setKnockback(float knockback) {
        this.knockback = knockback;
    }

    public void setAssetManager(AssetManager assetManager) {
        this.assetManager = assetManager;
    }

    public void setTextureAtlas(TextureAtlas textureAtlas) {
        this.textureAtlas = textureAtlas;
    }

    public void setWidth(float w) { WIDTH = w; }

    public float getWidth() { return WIDTH; }

    public void setHeight(float h) { HEIGHT = h; }

    public float getHeight() { return HEIGHT; }

    public Point2D getCenter() { return new Point2D.Float(getX() + getWidth() / 2, getY() + getHeight() / 2);}

    public Vector2 getHitBoxCenter() { return hitBox.getBoundingRectangle().getCenter(new Vector2()); }

    public void setSpeed(float s) { speed = s; }

    public float getSpeed() { return speed; }

    public float getVelocityAngle(){
        double angle = Math.atan2(getVel().getY(), getVel().getX());
        return (float)Math.toDegrees(angle);
    }

    public AssetManager getAssetManager() { return assetManager; }

    public void setTexture(Texture t) { texture = t; }

    public Texture getTexture() { return texture; }

    public void setAnimation(Animation<TextureRegion> a) { animation = a; }

    public Animation<TextureRegion> getAnimation() { return getAnimation(); }

    public String getAsset() { return asset; }

    public void setAsset(String asset) { this.asset = asset; }

    public void setAnimationTime(float a) { animationTime = a; }

    public float getAnimationTime() { return animationTime; }

    public void increaseAnimationTime(float i) { animationTime += i; }

    public void moveUnit(float delta) {
        moveRel((float) vel.getX() * delta, (float) vel.getY() * delta);
    }

    public void returnIntoBounds() {
        if (getX() < 0) {
            moveAbs(0, getY());
        }

        if (getX() + WIDTH > TradersOfTheLastCarp.CONFIG_WIDTH) {
            moveAbs(TradersOfTheLastCarp.CONFIG_WIDTH - WIDTH, getY());
        }

        if (getY() < 0) {
            moveAbs(getX(), 0);
        }

        if (getY() + HEIGHT > TradersOfTheLastCarp.CONFIG_HEIGHT) {
            moveAbs(getX(), TradersOfTheLastCarp.CONFIG_HEIGHT - HEIGHT);
        }
    }

    public boolean isOutOfBounds() {
        return getX() < 0 || getX() + WIDTH > TradersOfTheLastCarp.CONFIG_WIDTH ||
                getY() < 0 || getY() + HEIGHT > TradersOfTheLastCarp.CONFIG_HEIGHT;
    }

    public boolean assetsLoaded() {
        return assetsLoaded;
    }

    public void setAssetsLoaded(boolean assetsLoaded) {
        this.assetsLoaded = assetsLoaded;
    }
}
