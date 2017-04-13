package com.totlc.Actors.traps;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.ParticleEffect;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Rectangle;
import com.totlc.Actors.damage.Damage;
import com.totlc.AssetList;
import com.totlc.Directionality;

import java.awt.geom.Point2D;

public class ExitPortal extends ATrap {

    // Asset and animation constants.
    private TextureAtlas trapTextureAtlas, particleAtlas;
    private ParticleEffect wormHole;

    private static float width = 128;
    private static float height = 128;
    private static float portalOffset = 12;
    private float angle = 0;
    private Directionality modifiedDirection;

    public ExitPortal(AssetManager assetManager, float x, float y) {
        this(assetManager, new Rectangle(x, y, width, height), 0);
    }

    public ExitPortal(AssetManager assetManager, Rectangle r, long delay) {
        super(assetManager, r, delay);
        moveHitBox(4, 4);
        trapTextureAtlas = assetManager.get(AssetList.WORMHOLE.toString());
        particleAtlas = assetManager.get(AssetList.PARTICLES.toString());
        moveHitBox(trapTextureAtlas.getRegions().get(0).getRegionWidth() / 2 - getHitBoxWidth() * 0.5f, trapTextureAtlas.getRegions().get(1).getRegionHeight() / 2 + portalOffset);
        wormHole = new ParticleEffect();
        wormHole.load(Gdx.files.internal(AssetList.WORMHOLE_EFFECT.toString()), particleAtlas);
        wormHole.start();
    }

    public ExitPortal(AssetManager assetManager, float x, float y, Directionality d) {
        this(assetManager, x, y);
        this.modifiedDirection = d;
    }

    @Override
    public void act(float deltaTime){
        super.act(deltaTime);
        increaseAnimationTime(deltaTime);
        angle += 8;
        angle = angle % 360;
        wormHole.setPosition(getX() + trapTextureAtlas.getRegions().get(0).getRegionWidth() / 2, getY() + trapTextureAtlas.getRegions().get(0).getRegionHeight() / 2 + portalOffset);
    }

    @Override
    public void activate() {
        Sound sound = Gdx.audio.newSound(Gdx.files.internal(AssetList.BLOP.toString()));
        sound.play(0.7f);

        getTargetActor().moveAbs(getHitBoxX(), getHitBoxY());

        if (modifiedDirection != null &&
                getTargetActor() instanceof Damage &&
                (getTargetActor()).isInMotion()) {

            Point2D vel = getTargetActor().getVel();
            float velAngle = getTargetActor().getVelocityAngle();
            double largestVector = Math.max(Math.abs(vel.getX()), Math.abs(vel.getY()));

            getTargetActor().getHitBox().rotate(360 - velAngle);

            if (modifiedDirection.isFacingDown()) {
                vel.setLocation(0, -1 * largestVector);
                getTargetActor().getHitBox().rotate(270);
            } else if (modifiedDirection.isFacingLeft()) {
                vel.setLocation(-1 * largestVector, 0);
                getTargetActor().getHitBox().rotate(180);
            } else if (modifiedDirection.isFacingRight()) {
                vel.setLocation(largestVector, 0);
            } else if (modifiedDirection.isFacingUp()) {
                vel.setLocation(0, largestVector);
                getTargetActor().getHitBox().rotate(90);
            }
        }
    }

    @Override
    public void draw(Batch batch, float alpha) {
//        setZIndex(3);
        batch.draw(trapTextureAtlas.getRegions().get(1), getX() + trapTextureAtlas.getRegions().get(0).getRegionWidth() / 2 - trapTextureAtlas.getRegions().get(1).getRegionWidth() / 2, getY());
        batch.draw(trapTextureAtlas.getRegions().get(0), getX(), getY() + portalOffset, trapTextureAtlas.getRegions().get(0).getRegionWidth() / 2, trapTextureAtlas.getRegions().get(0).getRegionHeight() / 2,
                trapTextureAtlas.getRegions().get(0).getRegionWidth(), trapTextureAtlas.getRegions().get(0).getRegionHeight(), 0.6f, 0.6f, angle);
//        wormHole.draw(batch, Gdx.graphics.getDeltaTime());
    }

    public void setModifiedDirection(String direction) {
        this.modifiedDirection = Directionality.valueOf(direction);
    }
}
