package com.totlc.Actors.UI;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.NinePatch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.totlc.AssetList;

/**
 * Black UI Bar
 */
public class Bar extends Actor {

    // Texture information.
    private NinePatch bar;

    public Bar(int x, int y, int width, int height){
        setX(x);
        setY(y);
        setWidth(width);
        setHeight(height);
        bar = new NinePatch(new Texture(AssetList.UI_BAR.toString()), 16, 16, 8, 8);
    }

    public void draw(Batch batch, float alpha){
        bar.draw(batch, getX(), getY(), getWidth(), getHeight());
    }
}
