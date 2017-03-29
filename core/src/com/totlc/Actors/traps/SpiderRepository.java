package com.totlc.Actors.traps;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Rectangle;
import com.totlc.Actors.enemies.EnemyFactory;
import com.totlc.AssetList;

import java.util.Random;

/**
 * Bursts into spiders upon collision with another actor.
 */
public class SpiderRepository extends ATrap  {

    private int textureNum;
    private TextureAtlas trapTextureAtlas;
    private static int maxNumSpiders = 10;
    private int spidersGenerated = 0;
    private static Random randomNum = new Random();
    private static float width = 128;
    private static float height = 140;
    private static long delay = 0;

    // Scaling factor for animations.
    private float scale = 1;
    private static float minScale = 0.9f;
    private boolean animate, shrink;

    public SpiderRepository(AssetManager assetManager, float x, float y) {
        this(assetManager, new Rectangle(x, y, width, height));
    }

    public SpiderRepository(AssetManager assetManager, Rectangle r) {
        super(assetManager, r, delay);
        trapTextureAtlas = assetManager.get(AssetList.SPIDER_TRAP.toString());
        textureNum = randomNum.nextInt(3);
        this.animate = false;
        this.shrink = false;
    }

    @Override
    public void draw(Batch batch, float alpha) {
        batch.draw(trapTextureAtlas.getRegions().get(textureNum), getX(), getY(), trapTextureAtlas.getRegions().get(textureNum).getRegionWidth() * 0.5f, getY(),
                trapTextureAtlas.getRegions().get(textureNum).getRegionWidth(), trapTextureAtlas.getRegions().get(textureNum).getRegionHeight(), 1, scale, 0);
    }

    @Override
    public void act(float delta){
        super.act(delta);
        if (this.animate){
            if (this.shrink){
                this.scale = this.scale - 0.02f;
                if (this.scale <= minScale){
                    this.shrink = false;
                }
            } else{
                this.scale = this.scale + 0.02f;
                if (this.scale >= 1){
                    this.animate = false;
                }
            }
        }
    }

    @Override
    public void setup() {
        if (spidersGenerated < maxNumSpiders) {
            super.setup();
        }
    }

    @Override
    public void activate() {
        if (spidersGenerated >= maxNumSpiders) {
            return;
        }
        this.animate = true;
        this.shrink = true;
        int numSpiders = randomNum.nextInt(3) + 1;
        numSpiders = Math.min(numSpiders, maxNumSpiders - numSpiders);
        spidersGenerated += numSpiders;

        for (int i = 0; i < numSpiders; i++) {
            getStage().addActor(EnemyFactory.createDefaultEnemy(EnemyFactory.SPIDER, getAssetManager(),
                    randomNum.nextFloat() * width + getX(), getY()));
        }
    }
}
