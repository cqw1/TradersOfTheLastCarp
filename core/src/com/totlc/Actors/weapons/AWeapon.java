package com.totlc.Actors.weapons;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Mesh;
import com.badlogic.gdx.graphics.VertexAttribute;
import com.badlogic.gdx.graphics.VertexAttributes;
import com.badlogic.gdx.graphics.g2d.*;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.totlc.Actors.TotlcObject;
import com.totlc.Actors.Character;
import com.totlc.AssetList;

public abstract class AWeapon extends TotlcObject {

    private float attackingCounter = 0;
    private float attackingAnimationLength = 0;
    private Character character;
    private int attack = 1;

    private float whip_width = 128;
    private float whip_height = 64;

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

    public AWeapon(AssetManager assetManager, Character character, int attack, float attackingAnimationLength, String upAsset, String downAsset, String leftAsset, String rightAsset) {
        super(assetManager, new Rectangle(character.getX(), character.getY(), 128, 64));

        this.character = character;
        this.attack = attack;
        this.attackingAnimationLength = attackingAnimationLength;

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
        Rectangle characterHB = character.getHitBox().getBoundingRectangle();

        if (character.getIsFacing().isFacingRight()) {
//                setWidth(rightAnimation.getKeyFrame(attackingCounter, false).getRegionWidth());
//                setHeight(rightAnimation.getKeyFrame(ahbttackingCounter, false).getRegionHeight());
            moveAbs(characterHB.getX() + character.getWidth(), characterHB.getY() + Math.abs(characterHB.getHeight() - whip_height) / 2);
            setWidth(whip_width);
            setHeight(whip_height);
            initHitBox();

            batch.draw(rightAnimation.getKeyFrame(attackingCounter, false), character.getX(), character.getY());
        } else if (character.getIsFacing().isFacingLeft()) {
//                setWidth(-1 * leftAnimation.getKeyFrame(attackingCounter, false).getRegionWidth());
//                setHeight(leftAnimation.getKeyFrame(attackingCounter, false).getRegionHeight());
            moveAbs(characterHB.getX(), characterHB.getY() + Math.abs(characterHB.getHeight() - whip_height) / 2);
            setHeight(whip_height);
            setWidth(-1 * whip_width);
            initHitBox();

            batch.draw(leftAnimation.getKeyFrame(attackingCounter, false), character.getX() - 1.35f * character.getWidth(), character.getY());
        } else if (character.getIsFacing().isFacingDown()) {
//                setWidth(downAnimation.getKeyFrame(attackingCounter, false).getRegionWidth());
//                setHeight(-1 * downAnimation.getKeyFrame(attackingCounter, false).getRegionHeight());
            moveAbs(characterHB.getX() + Math.abs(characterHB.getWidth() - whip_height) / 2, characterHB.getY());
            setWidth(whip_height);
            setHeight(-1 * whip_width);
            initHitBox();

            batch.draw(downAnimation.getKeyFrame(attackingCounter, false), character.getX(), character.getY() - 0.9375f * character.getHeight());
        } else if (character.getIsFacing().isFacingUp()) {
//                setWidth(upAnimation.getKeyFrame(attackingCounter, false).getRegionWidth());
//                setHeight(upAnimation.getKeyFrame(attackingCounter, false).getRegionHeight());
            moveAbs(characterHB.getX() + Math.abs(characterHB.getWidth() - whip_height) / 2, characterHB.getY() + characterHB.getHeight());
            setHeight(whip_width);
            setWidth(whip_height);
            initHitBox();

            batch.draw(upAnimation.getKeyFrame(attackingCounter, false), character.getX(), character.getY());
        }

    }

    public void loadAnimation(String asset) {
        getAssetManager().load(asset, TextureAtlas.class);
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
