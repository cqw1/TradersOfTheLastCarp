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
    private static int spidersGenerated = 0;
    private static Random randomNum = new Random();
    private static float width = 128;
    private static float height = 140;
    private static float delay = 0;

    public SpiderRepository(AssetManager assetManager, float x, float y) {
        super(assetManager, new Rectangle(x, y, width, height), 0);
        trapTextureAtlas = assetManager.get(AssetList.SPIDER_TRAP.toString());
        textureNum = randomNum.nextInt(3);
    }

    public SpiderRepository(AssetManager assetManager, Rectangle r) {
        super(assetManager, r, 0);
        trapTextureAtlas = assetManager.get(AssetList.SPIDER_TRAP.toString());
        textureNum = randomNum.nextInt(3);
    }

    @Override
    public void draw(Batch batch, float alpha) {
        batch.draw(trapTextureAtlas.getRegions().get(textureNum), getX(), getY());
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

        int numSpiders = randomNum.nextInt(3) + 1;
        numSpiders = Math.min(numSpiders, maxNumSpiders - numSpiders);
        spidersGenerated += numSpiders;

        for (int i = 0; i < numSpiders; i++) {
            getStage().addActor(EnemyFactory.createDefaultEnemy(EnemyFactory.SPIDER, getAssetManager(),
                    randomNum.nextFloat() * width + getX(), getY()));
        }
    }
}
