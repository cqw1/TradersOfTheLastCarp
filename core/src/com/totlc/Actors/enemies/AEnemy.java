package com.totlc.Actors.enemies;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.g2d.*;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.totlc.Actors.Character;
import com.totlc.Actors.projectiles.Projectile;
import com.totlc.Actors.weapons.AWeapon;
import com.totlc.Actors.weapons.Whip;
import com.totlc.AssetList;

import java.awt.geom.Point2D;

//Empty for now. All enemies will inherit from this one, to differentiate player and enemy?

public abstract class AEnemy extends Character {

    // Enemy attributes.
    private int attack;

    private long hpTimer;

    private boolean showHP = false;

    private boolean isFloating = false;

    private boolean stunned = false;
    private long stunStart;
    private long stunPeriod = 1000; // in millis

    // Health indicator texture.
    private TextureRegion heart;

    private int heartSize = 48;
    private BitmapFont font;
    GlyphLayout layout = new GlyphLayout();

    public AEnemy(AssetManager assetManager, Rectangle r){
        super(assetManager, r);
        TextureAtlas atlas = new TextureAtlas(AssetList.ICON_PACK.toString());
        heart = atlas.findRegion("heart_icon");
        font = new BitmapFont(new FileHandle(AssetList.LOVELO_FONT.toString()),
               new FileHandle(AssetList.LOVELO_IMAGE.toString()), false);
    }

    /**
     * Helper that draws a health indicator above the enemy.
     */
    public void drawHealth(Batch batch, float alpha, int xOffset, int yOffset) {
        if(showHP) {
            batch.draw(heart, (float) getCenter().getX() - (heartSize / 2) + xOffset, (float) getCenter().getY() + getHeight() - heartSize / 2 + yOffset, heartSize, heartSize);
            layout.setText(font, getHpCurrent() + "/" + getHpMax());
            font.draw(batch, getHpCurrent() + "/" + getHpMax(), (float) getCenter().getX() + xOffset - (layout.width / 2), (float) getCenter().getY() + getHeight() + yOffset + 4);
            if (System.currentTimeMillis() - this.hpTimer > 3000) {
                this.showHP = false;
            }
        }
    }

    public boolean collidesWith(Actor otherActor) {
        if (otherActor instanceof Projectile && ((Projectile)otherActor).getDamageType() != 1) {
            System.out.println("collidesWith Projectile");
            takeDamage(((Projectile)otherActor).getAttack());

        } else if (otherActor instanceof Whip) {
            System.out.println("collidesWith Whip");
            stunned = true;
            stunStart = System.currentTimeMillis();

            // Maybe save this for different weapons
            //takeDamage(((AWeapon)otherActor).getAttack());
        }

        return (getHpCurrent() <= 0);
    }

    public void endCollidesWith(Actor otherActor) {}

    public int getAttack() {
        return attack;
    }

    public void setAttack(int attack) {
        this.attack = attack;
    }

    public boolean isFloating() {
        return isFloating;
    }

    public void setFloating(boolean floating) {
        isFloating = floating;
    }

    public boolean isShowHP() {
        return showHP;
    }

    public void setShowHP(boolean showHP) {
        this.showHP = showHP;
    }

    public Point2D getTarget(Actor target) {
        Point2D targetVector = new Point2D.Double(target.getX() - getX(), target.getY() - getY());
        float n = (float) Math.sqrt(Math.pow(targetVector.getX(), 2) + Math.pow(targetVector.getY(), 2));
        if (n != 0) {
            targetVector.setLocation(targetVector.getX() / n, targetVector.getY() / n);
            return targetVector;
        } else{
            return new Point2D.Double(0, 0);
        }
    }

    @Override
    public void takeDamage(int damage){
        super.takeDamage(damage);
        this.hpTimer = System.currentTimeMillis();
        this.showHP = true;
    }

    public boolean isStunned() {
        return stunned;
    }
    public void setStunned(boolean stun) {
        this.stunned = stun;
    }

    public long getStunStart() {
        return stunStart;
    }

    public long getStunPeriod() {
        return stunPeriod;
    }

    // Does all the stun checks for enemies. Returns true if they're stunned so the subclass act method knows to return
    // early.
    public boolean checkStun() {
        if (stunned) {
            if (System.currentTimeMillis() > (stunStart + stunPeriod)) {
                // No longer stunned
                stunned = false;
            }
        }
        return stunned;
    }

}
