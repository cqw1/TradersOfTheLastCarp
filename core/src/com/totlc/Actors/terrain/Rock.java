package com.totlc.Actors.terrain;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Rectangle;
import com.totlc.AssetList;

import java.util.Random;

public class Rock extends AWall {

    final private static float width = 48;
    final private static float height = 64;
    private TextureAtlas textureAtlas;
    private float scale = 0.3f;
    private int textureNum;

    public Rock(AssetManager assetManager, float x, float y) {
        super(assetManager, new Rectangle(x, y, width, height));
        Random rand = new Random();
        textureNum = rand.nextInt(3);
        textureAtlas = getAssetManager().get(AssetList.ROCKS.toString(), TextureAtlas.class);
    }

    @Override
    public void draw(Batch batch, float delta) {
        batch.draw(textureAtlas.getRegions().get(textureNum), getX(), getY(), textureAtlas.getRegions().get(textureNum).getRegionWidth() * 0.5f, getY(),
                textureAtlas.getRegions().get(textureNum).getRegionWidth(), textureAtlas.getRegions().get(textureNum).getRegionHeight(), scale, scale, 0);
    }
}
