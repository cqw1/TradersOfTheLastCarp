package com.totlc.Actors.enemies;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.*;
import com.badlogic.gdx.math.Rectangle;
import com.totlc.Actors.damage.Melee;
import com.totlc.Actors.enemies.movement.AMovement;
import com.totlc.AssetList;
import com.totlc.Directionality;

import java.awt.geom.Point2D;

public class FellChaperone extends AEnemy{

    // Stat constants.
    private static int id = 8;
    private static int basehp = 3;
    private static int atk = 1;

    // Asset and animation constants.
    private TextureAtlas walkTextureAtlas, attackTextureAtlas, circleTextureAtlas, circleAtlasLoop, particleAtlas;
    private Animation<TextureRegion> walkAnimation, attackAnimation, circleAnimation, circleAnimationLoop;
    private ParticleEffect shadow_path, shadow_cloak, ring_effect;
    Sound attackSound;

    private float textureWidthBody, textureHeightBody, circleWidth, circleHeight;

    private static float maxVel = 300;
    private static float speed = 200;
    private static float friction = 0f;

    private static float circleScale = 3.0f;

    private static float attackChance = .05f;
    private static long attackTime = 4000;
    private long attackStartTime;
    private boolean windDown, spawnedHitbox;

    private static float width = 80;
    private static float height = 250;

    public FellChaperone(AssetManager assetManager, float x, float y, AMovement movement) {
        super(assetManager, new Rectangle(x, y, width, height), movement, basehp, atk);
        moveHitBox(getWidth() * 0.36f, 0);
        this.windDown = false;
        this.spawnedHitbox = false;

        walkTextureAtlas = assetManager.get(AssetList.CHAPERONE_WALK.toString());
        walkAnimation = new Animation<TextureRegion>(1 / 16f, walkTextureAtlas.getRegions(), Animation.PlayMode.LOOP);
        attackTextureAtlas = assetManager.get(AssetList.CHAPERONE_ATTACK.toString());
        attackAnimation = new Animation<TextureRegion>(1 / 20f, attackTextureAtlas.getRegions(), Animation.PlayMode.LOOP);
        circleTextureAtlas = assetManager.get(AssetList.DANGER_ZONE.toString());
        circleAnimation = new Animation<TextureRegion>(1 / 20f, circleTextureAtlas.getRegions());
        circleAtlasLoop = assetManager.get(AssetList.DANGER_ZONE_LOOP.toString());
        circleAnimationLoop = new Animation<TextureRegion>(1 / 16f, circleAtlasLoop.getRegions(), Animation.PlayMode.LOOP_PINGPONG);

        for (int i = 0; i < circleTextureAtlas.getRegions().size; i++){
            circleTextureAtlas.getRegions().get(i).getTexture().setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
        }
        for (int i = 0; i < circleAtlasLoop.getRegions().size; i++){
            circleAtlasLoop.getRegions().get(i).getTexture().setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
        }

        textureWidthBody = walkTextureAtlas.getRegions().get(0).getRegionWidth();
        textureHeightBody = walkTextureAtlas.getRegions().get(0).getRegionHeight();
        circleWidth = circleTextureAtlas.getRegions().get(0).getRegionWidth();
        circleHeight = circleTextureAtlas.getRegions().get(0).getRegionHeight();

        particleAtlas = assetManager.get(AssetList.PARTICLES.toString());
        shadow_path = new ParticleEffect();
        shadow_cloak = new ParticleEffect();
        ring_effect = new ParticleEffect();
        shadow_path.load(Gdx.files.internal(AssetList.SHADOW_PATH.toString()), particleAtlas);
        shadow_cloak.load(Gdx.files.internal(AssetList.SHADOW_CLOAK.toString()), particleAtlas);
        ring_effect.load(Gdx.files.internal(AssetList.RING_EFFECT.toString()), particleAtlas);

        shadow_path.start();
        shadow_path.setPosition((float)getHitBoxCenter().getX() - 32, getY());
        shadow_cloak.start();
        shadow_cloak.setPosition((float)getHitBoxCenter().getX() - 32, getY());
        ring_effect.start();
        ring_effect.setPosition((float)getHitBoxCenter().getX(), getY() + 50);

        attackSound = Gdx.audio.newSound(Gdx.files.internal("sounds/scary0.mp3"));

        initMovement(friction, maxVel, speed);
    }

    @Override
    public void act(float deltaTime){
        super.act(deltaTime);

        if (this.getIsFacing().isFacingRight()) {
            shadow_path.setPosition((float)getHitBoxCenter().getX() - 32, getY());
            shadow_cloak.setPosition((float)getHitBoxCenter().getX() - 32, getY());
        } else {
            shadow_path.setPosition((float)getHitBoxCenter().getX(), getY());
            shadow_cloak.setPosition((float)getHitBoxCenter().getX(), getY());
        }
        ring_effect.setPosition((float)getHitBoxCenter().getX(), getY() + 50);

        if (checkStun()) {
            return;
        }
        if (getAttacking()){
            if(!spawnedHitbox && System.currentTimeMillis() - attackStartTime > 600) {
                getStage().addActor(new Melee(getAssetManager(), (float) getHitBoxCenter().getX() - this.circleWidth * circleScale * 0.5f,
                        getY() - this.circleHeight, circleWidth * circleScale, circleHeight * circleScale, this.attackTime - 600, 2, 1));
                this.spawnedHitbox = true;
            }
            if(System.currentTimeMillis() - attackStartTime > attackTime){
                setAttacking(false);
                this.windDown = true;
                setAnimationTime(0);
                circleAnimation.setPlayMode(Animation.PlayMode.REVERSED);
            }
        } else{
            // Set facing direction.
            if(getMovement().getTarget().getX() - getX() < 0){
                setIsFacing(Directionality.LEFT);
            }  else{
                setIsFacing(Directionality.RIGHT);
            }
            // Attack chance when too close.
            if (new Point2D.Double(getMovement().getTarget().getX(), getMovement().getTarget().getY()).distance(new Point2D.Double(getX(), getY())) < 250
                    && Math.random() < attackChance){
                setAnimationTime(0);
                circleAnimation.setPlayMode(Animation.PlayMode.NORMAL);
                this.attackStartTime = System.currentTimeMillis();
                summonCircle();
            }
        }
        if (this.windDown) {
            if(circleAnimation.isAnimationFinished(getAnimationTime())) {
                this.windDown = false;
                this.spawnedHitbox = false;
                setAnimationTime(0);
            }
        }
    }

    @Override
    public void draw(Batch batch, float alpha) {
        if (getAttacking() || this.windDown) {
            batch.setColor(batch.getColor().r, batch.getColor().g, batch.getColor().b, 0.5f );
            if (!circleAnimation.isAnimationFinished(getAnimationTime())) {
                batch.draw(circleAnimation.getKeyFrame(getAnimationTime(), false), (float) getHitBoxCenter().getX() - circleWidth * 0.5f, getY(),
                        this.circleWidth * 0.5f, this.circleHeight * 0.5f, this.circleWidth, this.circleHeight, circleScale, circleScale, 0);
            } else {
                batch.draw(circleAnimationLoop.getKeyFrame(getAnimationTime(), false), (float) getHitBoxCenter().getX() - circleWidth * 0.5f, getY(),
                        this.circleWidth * 0.5f, this.circleHeight * 0.5f, this.circleWidth, this.circleHeight, circleScale, circleScale, 0);
            }
            batch.setColor(batch.getColor().r, batch.getColor().g, batch.getColor().b, 1.0f);
        }
        shadow_path.draw(batch, Gdx.graphics.getDeltaTime());
        if (!getAttacking()){
            if (this.getIsFacing().isFacingRight()) {
                batch.draw(walkAnimation.getKeyFrame(getAnimationTime(), false), getX(), getY(), getTextureWidthBody(), getTextureHeightBody());
            } else {
                batch.draw(walkAnimation.getKeyFrame(getAnimationTime(), false), getX() + getTextureWidthBody(), getY(), -getTextureWidthBody(), getTextureHeightBody());
            }
        } else{
            if (this.getIsFacing().isFacingRight()) {
                batch.draw(attackAnimation.getKeyFrame(getAnimationTime(), false), getX(), getY(), getTextureWidthBody(), getTextureHeightBody());
            } else {
                batch.draw(attackAnimation.getKeyFrame(getAnimationTime(), false), getX() + getTextureWidthBody(), getY(), -getTextureWidthBody(), getTextureHeightBody());
            }
            ring_effect.draw(batch, Gdx.graphics.getDeltaTime());
        }
        shadow_cloak.draw(batch, Gdx.graphics.getDeltaTime());

        drawHealth(batch, alpha, -(int)getHitBoxWidth() / 2, -(int)getHitBoxHeight() / 2);
    }

    private void summonCircle(){
        attackSound.play(1.0f);
        setAttacking(true);
    }

    public float getTextureWidthBody() {
        return textureWidthBody;
    }

    public void setTextureWidthBody(float textureWidthBody) {
        this.textureWidthBody = textureWidthBody;
    }

    public float getTextureHeightBody() {
        return textureHeightBody;
    }

    public void setTextureHeightBody(float textureHeightBody) {
        this.textureHeightBody = textureHeightBody;
    }
}
