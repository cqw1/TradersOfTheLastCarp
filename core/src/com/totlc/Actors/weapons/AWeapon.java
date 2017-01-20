package com.totlc.Actors.weapons;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.totlc.Actors.TotlcObject;
import com.totlc.Actors.Character;
import com.totlc.AssetList;

public abstract class AWeapon extends TotlcObject {

    private AssetManager assetManager;
    private float attackingCounter = 0;
    private float attackingAnimationLength = 0;
    private Character character;

    private String upAsset;
    private String downAsset;
    private String leftAsset;
    private String rightAsset;

    private TextureAtlas upTextureAtlas;
    private TextureAtlas downTextureAtlas;
    private TextureAtlas leftTextureAtlas;
    private TextureAtlas rightTextureAtlas;

    private Animation<TextureRegion> upAnimation;
    private Animation<TextureRegion> downAnimation;
    private Animation<TextureRegion> leftAnimation;
    private Animation<TextureRegion> rightAnimation;

    public AWeapon(AssetManager assetManager, Character character, float attackingAnimationLength) {
        super(assetManager, character.getX(), character.getY());

        setWidth(24);
        setHeight(48);

        this.assetManager = assetManager;
        this.character = character;
        this.attackingAnimationLength = attackingAnimationLength;
    }

    @Override
    public void act(float deltaTime) {
        attackingCounter += deltaTime;

        if (attackingCounter > attackingAnimationLength) {
            attackingCounter = 0;
            character.setAttacking(false);
            this.remove();
        }
    }

    @Override
    public void draw(Batch batch, float alpha) {
        if (assetManager.update() && !assetsLoaded()) {
            // Done loading. Instantiate all assets
            setAssetsLoaded(true);


            // Whip
            downTextureAtlas = assetManager.get(downAsset);
            downAnimation = new Animation<TextureRegion>((float) (1.0/downTextureAtlas.getRegions().size * attackingAnimationLength), downTextureAtlas.getRegions());

            upTextureAtlas = assetManager.get(upAsset);
            upAnimation = new Animation<TextureRegion>((float) (1.0/upTextureAtlas.getRegions().size * attackingAnimationLength), upTextureAtlas.getRegions());

            leftTextureAtlas = assetManager.get(leftAsset);
            leftAnimation = new Animation<TextureRegion>((float) (1.0/leftTextureAtlas.getRegions().size * attackingAnimationLength), leftTextureAtlas.getRegions());

            rightTextureAtlas = assetManager.get(rightAsset);
            rightAnimation = new Animation<TextureRegion>((float) (1.0/rightTextureAtlas.getRegions().size * attackingAnimationLength), rightTextureAtlas.getRegions());
        }

        if (assetsLoaded()) {
            // TODO: Generalize the offsets.
            if (character.getIsFacing().isFacingRight()) {
                batch.draw(rightAnimation.getKeyFrame(attackingCounter, false), character.getX(), character.getY());
            } else if (character.getIsFacing().isFacingLeft()) {
                batch.draw(leftAnimation.getKeyFrame(attackingCounter, false), character.getX() - 1.35f * character.getWidth(), character.getY());
            } else if (character.getIsFacing().isFacingDown()) {
                batch.draw(downAnimation.getKeyFrame(attackingCounter, false), character.getX(), character.getY() - 0.9375f * character.getHeight());
            } else if (character.getIsFacing().isFacingUp()) {
                batch.draw(upAnimation.getKeyFrame(attackingCounter, false), character.getX(), character.getY());
            }
        }

    }

    public void loadAnimation(String asset) {
        assetManager.load(asset, TextureAtlas.class);
    }

    public void setUpAsset(String asset) {
        upAsset = asset;
        loadAnimation(asset);
    }

    public void setDownAsset(String asset) {
        downAsset = asset;
        loadAnimation(asset);
    }

    public void setLeftAsset(String asset) {
        leftAsset = asset;
        loadAnimation(asset);
    }

    public void setRightAsset(String asset) {
        rightAsset = asset;
        loadAnimation(asset);
    }

    public float getAttackingCounter() {
        return attackingCounter;
    }
}
