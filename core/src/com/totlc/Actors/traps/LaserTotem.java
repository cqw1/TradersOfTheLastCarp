package com.totlc.Actors.traps;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.*;
import com.badlogic.gdx.math.Rectangle;
import com.totlc.Actors.damage.DamageEnum;
import com.totlc.Actors.damage.DamageFactory;
import com.totlc.Actors.damage.Laser;
import com.totlc.AssetList;

import java.awt.geom.Point2D;

public class LaserTotem extends ATrap{

    // Asset and animation constants.
    private Texture trapTexture;
    private TextureAtlas blue_gem, red_gem, particleAtlas;
    Animation<TextureRegion> blueGemAnimation, redGemAnimation;
    private ParticleEffect laserSource;
    private Laser l;

    private Sound attackSound;
    private static long duration = 8000; // in millis
    private long startTime;
    private static float width = 230;
    private static float height = 301;

    private float angle, initialAngle;
    private float gem_scale;
    private static float rotationSpeed = 0.016f;
    private static long delay = 2000;

    public LaserTotem(AssetManager assetManager, float x, float y) {
        this(assetManager, x, y, delay);
    }

    public LaserTotem(AssetManager assetManager, float x, float y, long delay) {
        super(assetManager, new Rectangle(x, y, width, height), delay);
        trapTexture = assetManager.get(AssetList.LASER_TOTEM.toString());
        blue_gem = assetManager.get(AssetList.BLUE_GEM.toString());
        blueGemAnimation = new Animation<TextureRegion>(1 / 8f, blue_gem.getRegions());
        red_gem = assetManager.get(AssetList.RED_GEM.toString());
        redGemAnimation = new Animation<TextureRegion>(1 / 16f, red_gem.getRegions());
        this.gem_scale = 0.7f;
        for (int i = 0; i < blue_gem.getRegions().size; i++){
            blue_gem.getRegions().get(i).getTexture().setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
        }
        for (int i = 0; i < red_gem.getRegions().size; i++){
            red_gem.getRegions().get(i).getTexture().setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
        }
        this.startTime = 0;
        attackSound = Gdx.audio.newSound(Gdx.files.internal("sounds/laser1.mp3"));
        this.initialAngle = 0;
        this.angle = 0;

        particleAtlas = assetManager.get(AssetList.PARTICLES.toString());
        laserSource = new ParticleEffect();
        laserSource.load(Gdx.files.internal(AssetList.LASERPULSE.toString()), particleAtlas);
    }

    public LaserTotem(AssetManager assetManager, int x, int y, long delay, float initialAngle) {
        this(assetManager, x, y, delay);
        this.initialAngle = initialAngle;
        this.angle = initialAngle;
    }

    @Override
    public void activate() {
        this.startTime = System.currentTimeMillis();
        attackSound.play(1f);
        laserSource.start();
        laserSource.setPosition(getX() + 75, getY() + trapTexture.getHeight() * 0.88f);
    }

    @Override
    public void act(float deltaTime){
        delayActivation();
        if (isActive() && System.currentTimeMillis() > (getStartTime() + getDelay())) {
            // End setup phase. Active phase is ended manually.
            setSetup(false);
        }
        increaseAnimationTime(deltaTime);
        if(isActive()){
            this.angle += rotationSpeed;
            getStage().addActor(DamageFactory.createDamage(DamageEnum.LASER, new Point2D.Double(2000 * Math.sin(angle), 2000 * Math.cos(angle)), getAssetManager(),
                    getX() + 20, getY() + trapTexture.getHeight() * 0.74f + red_gem.getRegions().get(0).getRegionHeight() * 0.5f * this.gem_scale, 0));
            if (System.currentTimeMillis() - this.startTime >= this.duration){
                this.angle = this.initialAngle;
                setActive(false);
                attackSound.stop();
                laserSource.allowCompletion();
            }
        }
    }

    @Override
    public void draw(Batch batch, float alpha) {
        batch.draw(trapTexture, getX(), getY());
        if (isSetup() || isActive()) {
            batch.draw(redGemAnimation.getKeyFrame(getAnimationTime(), true), getX() + 38, getY() + trapTexture.getHeight() * 0.74f,
                    red_gem.getRegions().get(0).getRegionWidth() * 0.5f, red_gem.getRegions().get(0).getRegionHeight() * 0.5f,
                    red_gem.getRegions().get(0).getRegionWidth(), red_gem.getRegions().get(0).getRegionHeight(), this.gem_scale, this.gem_scale, 0);
        } else{
            batch.draw(blueGemAnimation.getKeyFrame(getAnimationTime(), true), getX() + 38, getY() + trapTexture.getHeight() * 0.74f,
                    blue_gem.getRegions().get(0).getRegionWidth() * 0.5f, blue_gem.getRegions().get(0).getRegionHeight() * 0.5f,
                    blue_gem.getRegions().get(0).getRegionWidth(), blue_gem.getRegions().get(0).getRegionHeight(), this.gem_scale, this.gem_scale, 0);
        }
        laserSource.draw(batch, Gdx.graphics.getDeltaTime());
    }
}
