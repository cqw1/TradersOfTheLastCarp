package com.totlc.Actors.players;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Array;
import com.totlc.AssetList;

public class MrFischl extends PlayableCharacter {

    Animation<TextureRegion> notImplemented;

    public MrFischl(AssetManager assetManager) {
        this(assetManager, 0, 0);
    }

    public MrFischl(AssetManager assetManager, float x, float y) {
        super(assetManager, x, y);

        setHpMax(5);
        setHpCurrent(getHpMax());
        setSpeed(300);
        setMaxVel(300);

        setMovingDown(true);
    }

    @Override
    protected void initTextures(AssetManager assetManager) {
        // Head Textures.
        head = assetManager.get(AssetList.FISCHL_HEAD.toString());
        Array<TextureRegion> textureArray = new Array<TextureRegion>(4);
        textureArray.add(head.findRegion("head_left"));
        textureArray.add(head.findRegion("head_forward"));
        textureArray.add(head.findRegion("head_right"));
        notImplemented = new Animation<TextureRegion>(1 / 15f, textureArray, Animation.PlayMode.LOOP_PINGPONG);

        walk_side = assetManager.get(AssetList.JANE_WALK_SIDE.toString());
    }

    // Placeholder draw for character select screen.
    @Override
    public void draw(Batch batch, float delta) {
        batch.draw(notImplemented.getKeyFrame(getAnimationTime(), true), getX(), getY(), getTextureWidthHead(), getTextureHeightHead());
    }
}
