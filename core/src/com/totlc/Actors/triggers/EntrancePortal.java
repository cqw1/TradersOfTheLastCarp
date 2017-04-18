package com.totlc.Actors.triggers;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.ParticleEffect;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.totlc.Actors.Character;
import com.totlc.Actors.TotlcObject;
import com.totlc.Actors.damage.Damage;
import com.totlc.Actors.traps.ATrap;
import com.totlc.AssetList;

public class EntrancePortal extends ATrigger {

    // Asset and animation constants.
    private TextureAtlas trapTextureAtlas, particleAtlas;
    private ParticleEffect wormHole;

    private static float width = 128;
    private static float height = 128;
    private static float portalOffset = 12;
    private float angle = 0;

    public EntrancePortal(AssetManager assetManager, float x, float y) {
        this(assetManager, new Rectangle(x, y, width, height));
    }

    public EntrancePortal(AssetManager assetManager, Rectangle r) {
        super(assetManager, r);
        moveHitBox(4, 4);
        trapTextureAtlas = assetManager.get(AssetList.WORMHOLE.toString());
        particleAtlas = assetManager.get(AssetList.PARTICLES.toString());
        moveHitBox(trapTextureAtlas.getRegions().get(0).getRegionWidth() / 2 - getHitBoxWidth() * 0.5f, trapTextureAtlas.getRegions().get(1).getRegionHeight() / 2 + portalOffset);
        wormHole = new ParticleEffect();
        wormHole.load(Gdx.files.internal(AssetList.WORMHOLE_EFFECT.toString()), particleAtlas);
        wormHole.start();
//        setZIndex(4);
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
    public void draw(Batch batch, float alpha) {
//        setZIndex(3);
        batch.draw(trapTextureAtlas.getRegions().get(1), getX() + trapTextureAtlas.getRegions().get(0).getRegionWidth() / 2 - trapTextureAtlas.getRegions().get(1).getRegionWidth() / 2, getY());
        batch.draw(trapTextureAtlas.getRegions().get(0), getX(), getY() + portalOffset, trapTextureAtlas.getRegions().get(0).getRegionWidth() / 2, trapTextureAtlas.getRegions().get(0).getRegionHeight() / 2,
                trapTextureAtlas.getRegions().get(0).getRegionWidth(), trapTextureAtlas.getRegions().get(0).getRegionHeight(), 0.6f, 0.6f, angle);
        wormHole.draw(batch, Gdx.graphics.getDeltaTime());
    }

    public boolean collidesWith(Actor otherActor) {

        //Only activate if encountering a character or damage in motion
        if (otherActor instanceof Character ||
                (otherActor instanceof Damage &&
                        ((Damage)otherActor).isInMotion())) {
            for (ATrap trap : getListOfTraps()) {
                trap.setup((TotlcObject)otherActor);
            }
            handleTrigger(true, otherActor);
        }

        return false;
    }

    public void endCollidesWith(Actor otherActor) {}
}
