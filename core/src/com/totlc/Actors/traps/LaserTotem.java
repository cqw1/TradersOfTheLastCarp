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
import com.totlc.Actors.damage.DamageEnum;
import com.totlc.Actors.damage.DamageFactory;
import com.totlc.AssetList;

import java.awt.geom.Point2D;

public class LaserTotem extends ATrap{

    // Asset and animation constants.
    private Texture trapTexture;
    private TextureAtlas blue_gem, red_gem;
    Animation<TextureRegion> blueGemAnimation, redGemAnimation;
    private Sound attackSound;
    private static long duration = 8000; // in millis
    private long startTime;
    private static float width = 230;
    private static float height = 301;

    private float angle;
    private float gem_scale;
    private static float rotationSpeed = 0.012f;

    public LaserTotem(AssetManager assetManager, int x, int y, long delay) {
        super(assetManager, new Rectangle(x, y, width, height), delay);
        trapTexture = assetManager.get(AssetList.LASER_TOTEM.toString());
        blue_gem = assetManager.get(AssetList.BLUE_GEM.toString());
        blueGemAnimation = new Animation<TextureRegion>(1 / 16f, blue_gem.getRegions());
        red_gem = assetManager.get(AssetList.RED_GEM.toString());
        redGemAnimation = new Animation<TextureRegion>(1 / 16f, red_gem.getRegions());
        this.angle = 0;
        this.gem_scale = 0.7f;
        for (int i = 0; i < blue_gem.getRegions().size; i++){
            blue_gem.getRegions().get(i).getTexture().setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
        }
        for (int i = 0; i < red_gem.getRegions().size; i++){
            red_gem.getRegions().get(i).getTexture().setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
        }
        this.startTime = 0;
        attackSound = Gdx.audio.newSound(Gdx.files.internal("sounds/laser1.mp3"));
    }

    @Override
    public void activate() {
        this.startTime = System.currentTimeMillis();
        attackSound.play(1f);
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
                    getX() + 36 + red_gem.getRegions().get(0).getRegionWidth() * 0.3f * this.gem_scale, getY() + trapTexture.getHeight() * 0.75f + red_gem.getRegions().get(0).getRegionHeight() * 0.5f * this.gem_scale, 0));
            if (System.currentTimeMillis() - this.startTime >= this.duration){
                this.angle = 0;
                setActive(false);
                attackSound.stop();
            }
        }
    }

    @Override
    public void draw(Batch batch, float alpha) {
        batch.draw(trapTexture, getX(), getY());
        if (isSetup() || isActive()) {
            batch.draw(redGemAnimation.getKeyFrame(getAnimationTime(), true), getX() + 36, getY() + trapTexture.getHeight() * 0.75f,
                    red_gem.getRegions().get(0).getRegionWidth() * 0.5f, red_gem.getRegions().get(0).getRegionHeight() * 0.5f,
                    red_gem.getRegions().get(0).getRegionWidth(), red_gem.getRegions().get(0).getRegionHeight(), this.gem_scale, this.gem_scale, 0);
        } else{
            batch.draw(blueGemAnimation.getKeyFrame(getAnimationTime(), true), getX() + 36, getY() + trapTexture.getHeight() * 0.75f,
                    blue_gem.getRegions().get(0).getRegionWidth() * 0.5f, blue_gem.getRegions().get(0).getRegionHeight() * 0.5f,
                    blue_gem.getRegions().get(0).getRegionWidth(), blue_gem.getRegions().get(0).getRegionHeight(), this.gem_scale, this.gem_scale, 0);
        }
    }
}
