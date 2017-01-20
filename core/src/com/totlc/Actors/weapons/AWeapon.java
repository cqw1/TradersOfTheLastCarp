package com.totlc.Actors.weapons;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Mesh;
import com.badlogic.gdx.graphics.VertexAttribute;
import com.badlogic.gdx.graphics.VertexAttributes;
import com.badlogic.gdx.graphics.g2d.*;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.totlc.Actors.TotlcObject;
import com.totlc.Actors.Character;
import com.totlc.AssetList;

public abstract class AWeapon extends TotlcObject {

    private AssetManager assetManager;
    private float attackingCounter = 0;
    private float attackingAnimationLength = 0;
    private Character character;
    private int attack = 1;

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

    public AWeapon(AssetManager assetManager, Character character, int attack, float attackingAnimationLength) {
        super(assetManager, character.getX(), character.getY());

        setWidth(256);
        setHeight(128);

        initHitBox();
//        getHitBox().setOrigin(character.getX() + character.getWidth() / 2, character.getY() + character.getHeight() / 2);

        this.assetManager = assetManager;
        this.character = character;
        this.attack = attack;
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

        System.out.println("character.getX(): " + character.getX());
        System.out.println("character.getY(): " + character.getY());
        moveAbs(character.getX(), character.getY());
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
            getHitBox().rotate(360 - getHitBox().getRotation());

            if (character.getIsFacing().isFacingRight()) {
                setWidth(rightAnimation.getKeyFrame(attackingCounter, false).getRegionWidth());
                setHeight(rightAnimation.getKeyFrame(attackingCounter, false).getRegionHeight());;
                initHitBox();
//                getHitBox().rotate(0);

                batch.draw(rightAnimation.getKeyFrame(attackingCounter, false), character.getX(), character.getY());
            } else if (character.getIsFacing().isFacingLeft()) {
                setWidth(-1 * leftAnimation.getKeyFrame(attackingCounter, false).getRegionWidth());
                setHeight(leftAnimation.getKeyFrame(attackingCounter, false).getRegionHeight());;
                initHitBox();
//                getHitBox().rotate(180);

                batch.draw(leftAnimation.getKeyFrame(attackingCounter, false), character.getX() - 1.35f * character.getWidth(), character.getY());
            } else if (character.getIsFacing().isFacingDown()) {
                setWidth(downAnimation.getKeyFrame(attackingCounter, false).getRegionWidth());
                setHeight(-1 * downAnimation.getKeyFrame(attackingCounter, false).getRegionHeight());;
                initHitBox();
//                getHitBox().rotate(270);

                batch.draw(downAnimation.getKeyFrame(attackingCounter, false), character.getX(), character.getY() - 0.9375f * character.getHeight());
            } else if (character.getIsFacing().isFacingUp()) {
                setWidth(upAnimation.getKeyFrame(attackingCounter, false).getRegionWidth());
                setHeight(upAnimation.getKeyFrame(attackingCounter, false).getRegionHeight());;
                initHitBox();
//                getHitBox().rotate(90);

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

    public int getAttack() {
        return attack;
    }
}
