package com.totlc.Actors.items;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.totlc.Actors.Player;
import com.totlc.AssetList;

/**
 * Health pickup item. Restores missing health when collided with, then disappears.
 */
public class Health extends APickup{

    // Texture information.
    private TextureRegion heart;
    private float scale = 0.5f;

    public Health(AssetManager assetManager, int x, int y) {
        super(assetManager, new Rectangle(x, y, 64, 64));
        heart = assetManager.get(AssetList.ITEM_PACK.toString(), TextureAtlas.class).findRegion("1Heart");
    }

    @Override
    public void draw(Batch batch, float alpha) {
        batch.draw(heart, getX(), getY(), heart.getRegionWidth() / 2, heart.getRegionHeight() / 2, heart.getRegionWidth(), heart.getRegionHeight(), scale, scale, 0);
    }

    @Override
    public boolean collidesWith(Actor otherActor) {
        if (otherActor instanceof Player){
            Player p = (Player) otherActor;
            if (p.getHpCurrent() < p.getHpMax()){
                p.setHpCurrent(Math.min(p.getHpCurrent() + 2, p.getHpMax()));
                pickup();
            }
        }
        return false;
    }

    @Override
    public void endCollidesWith(Actor otherActor) {

    }

    @Override
    public void pickup() {
        remove();
    }
}
