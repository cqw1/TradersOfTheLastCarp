package com.totlc.Actors.terrain;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Rectangle;
import com.totlc.AssetList;

import java.util.Random;

public class Rock extends AWall {

    final private static float width = 136;
    final private static float height = 100;
    private TextureAtlas textureAtlas;
    private float scale;
    private int textureNum;

    public Rock(AssetManager assetManager, float x, float y, float scale) {
        super(assetManager, new Rectangle(x, y, width, height));
        this.scale = scale;
        getHitBox().setScale(scale, scale);
        moveHitBox(getWidth() * 0.5f * scale, 0);
        Random rand = new Random();
        textureNum = rand.nextInt(3);
        textureAtlas = getAssetManager().get(AssetList.ROCKS.toString(), TextureAtlas.class);
        for (int i = 0; i < textureAtlas.getRegions().size; i++){
            textureAtlas.getRegions().get(i).getTexture().setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
        }
    }

    public Rock(AssetManager assetManager, float x, float y) {
        this(assetManager, x, y, 0.6f);
    }

    @Override
    public void draw(Batch batch, float delta) {
        batch.draw(textureAtlas.getRegions().get(textureNum), getX(), getY(), 0, 0,
                textureAtlas.getRegions().get(textureNum).getRegionWidth(), textureAtlas.getRegions().get(textureNum).getRegionHeight(), scale, scale, 0);
    }
}
