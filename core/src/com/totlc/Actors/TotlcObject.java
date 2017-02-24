package com.totlc.Actors;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.totlc.Actors.terrain.AWall;
import com.totlc.Directionality;
import com.totlc.TradersOfTheLastCarp;
import com.badlogic.gdx.math.Polygon;
import com.totlc.status.AStatus;

import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.List;

public abstract class TotlcObject extends Actor {

    /**
     * IMPORTANT: MAKE SURE TO SET THE WIDTH, HEIGHT, AND CALL initHitBox()
     */

    private float speed;
    private float baseSpeed;

    //Image related fields
    private AssetManager assetManager;

    Texture texture;
    Animation<TextureRegion> animation;
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

    private List<AStatus> statuses = new ArrayList<AStatus>();

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

    public Point2D.Double getUpdatedVelocity(Point2D acc) {
        Point2D newVelocity = getVel();
        double newX = newVelocity.getX() * getFriction() + acc.getX();
        double newY = newVelocity.getY() * getFriction() + acc.getY();
        if (Math.abs(newX)  > getMaxVel()){
            newX = getMaxVel() * Math.signum(newX);
        }
        if (Math.abs(newY) > getMaxVel()){
            newY = getMaxVel() * Math.signum(newY);
        }
        return new Point2D.Double(newX, newY);
    }

    public void updateVelocity() {
        setVel(getUpdatedVelocity(getAcc()));
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

    public void setWidth(float w) { WIDTH = w; }

    public float getWidth() { return WIDTH; }

    public void setHeight(float h) { HEIGHT = h; }

    public float getHeight() { return HEIGHT; }

    public Point2D getCenter() { return new Point2D.Float(getX() + getWidth() / 2, getY() + getHeight() / 2);}

    public Point2D getHitBoxCenter() {
        Vector2 c = hitBox.getBoundingRectangle().getCenter(new Vector2());
        return new Point2D.Float(c.x, c.y);
    }

    public void setSpeed(float s) { speed = s; }

    public float getSpeed() { return speed; }

    public float getBaseSpeed() {
        return baseSpeed;
    }

    public void setBaseSpeed(float baseSpeed) {
        this.baseSpeed = baseSpeed;
    }

    public float getVelocityAngle(){
        double angle = Math.atan2(getVel().getY(), getVel().getX());
        return (float)Math.toDegrees(angle);
    }

    public AssetManager getAssetManager() { return assetManager; }

    public void setTexture(Texture t) { texture = t; }

    public Texture getTexture() { return texture; }

    public void setAnimation(Animation<TextureRegion> a) { animation = a; }

    public Animation<TextureRegion> getAnimation() { return getAnimation(); }

    public void setAnimationTime(float a) { animationTime = a; }

    public float getAnimationTime() { return animationTime; }

    public void increaseAnimationTime(float i) { animationTime += i; }

    public void moveUnit(float delta) {
        moveRel((float) vel.getX() * delta, (float) vel.getY() * delta);
    }

    public boolean isInMotion() {
        return vel.getX() == 0 && vel.getY() == 0;
    }

    public void returnIntoBounds(float formerX, float formerY) {
        for (Actor act: getStage().getActors().toArray()) {
            if (act instanceof AWall) {
                AWall wall = (AWall) act;

                Polygon horHitBox = new Polygon(wall.getHitBox().getVertices());
                horHitBox.translate(0, formerY - getY());

                Polygon verHitBox = new Polygon(wall.getHitBox().getVertices());
                verHitBox.translate(formerX - getX(), 0);

                if (Intersector.overlapConvexPolygons(horHitBox, getHitBox())) {
                    moveRel(formerX - getX(), 0);
                }

                if (Intersector.overlapConvexPolygons(verHitBox, getHitBox())) {
                    moveRel(0, formerY - getY());
                }
            }
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

    public List<AStatus> getStatuses() {
        return statuses;
    }

    protected void procStatuses(){
        for (AStatus s : getStatuses()){
            s.proc();
        }
    }

    protected void drawStatuses(Batch batch, float alpha){
        for (AStatus s : getStatuses()){
           s.draw(batch, alpha);
        }
    }

    public boolean isTravelingLeft() {
        return vel.getX() < 0 && Math.abs(vel.getX()) > Math.abs(vel.getY());
    }

    public boolean isTravelingRight() {
        return vel.getX() > 0 && Math.abs(vel.getX()) > Math.abs(vel.getY());
    }

    public boolean isTravelingDown() {
        return vel.getY() < 0 && Math.abs(vel.getX()) < Math.abs(vel.getY());
    }

    public boolean isTravelingUp() {
        return vel.getY() > 0 && Math.abs(vel.getX()) < Math.abs(vel.getY());
    }
}
