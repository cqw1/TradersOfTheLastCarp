package com.totlc.Actors.enemies;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.*;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.totlc.Actors.Character;
import com.totlc.Actors.damage.Damage;
import com.totlc.Actors.effects.Stun;
import com.totlc.Actors.enemies.movement.AMovement;
import com.totlc.Actors.weapons.Whip;
import com.totlc.AssetList;
import com.totlc.TradersOfTheLastCarp;
import com.totlc.status.Invulnerable;

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
    private long stunPeriod = 0; // in millis

    // Health indicator texture.
    private TextureRegion heart;

    private int heartSize = 48;
    private BitmapFont font;
    GlyphLayout layout = new GlyphLayout();

    private boolean invincible = false;

    private AMovement movement;

    public AEnemy(AssetManager assetManager, Rectangle r, AMovement movement, int hp, int atk){
        super(assetManager, r);
        TextureAtlas atlas = new TextureAtlas(AssetList.ICON_PACK.toString());
        heart = atlas.findRegion("heart_icon");
        heart.getTexture().setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
        font = TradersOfTheLastCarp.systemFont0;
        this.movement = movement;
        setHpMax(hp);
        setHpCurrent(hp);
        this.attack = atk;
    }

    /**
     * Helper that draws a health indicator above the enemy.
     */
    public void drawHealth(Batch batch, float alpha, int xOffset, int yOffset) {
        if(showHP) {
            batch.draw(heart, (float) getHitBoxCenter().getX() - (heartSize / 2) + xOffset, (float) getHitBoxCenter().getY() + getHitBoxHeight() + yOffset, heartSize, heartSize);
            layout.setText(font, getHpCurrent() + "/" + getHpMax());
            font.draw(batch, getHpCurrent() + "/" + getHpMax(), (float) getHitBoxCenter().getX() + xOffset - (layout.width / 2), (float) getHitBoxCenter().getY() + getHitBoxHeight() + yOffset + (heartSize / 2) + 3);
            if (System.currentTimeMillis() - this.hpTimer > 3000) {
                this.showHP = false;
            }
        }
    }

    public void initMovement(float friction, float maxVel, float speed) {
        setFriction(friction);
        setMaxVel(maxVel);
        setSpeed(speed);
        setBaseSpeed(speed);
    }

    /**
     * Helper that draws a stun indicator above enemy head.
     * @param stunPeriod: Duration to stun for.
     */
    private void drawStunIndicator(long stunPeriod) {
        Stun stun = new Stun(getAssetManager(), this, stunPeriod, true);
        getStage().addActor(stun);
    }

    public boolean collidesWith(Actor otherActor) {
        if (otherActor instanceof Damage && ((Damage)otherActor).getDamageType() != 1) {
            Damage damage = (Damage) otherActor;
            if (!invincible) {
//                if (System.currentTimeMillis() > damage.getTimeToApplyDamage()) {
                    takeDamage(damage.getAttack());
//                }
            }
        } else if (otherActor instanceof Whip) {
            if (!invincible) {
                // Invincible enemies can't be stunned.
                if (!stunned) {
                    stunned = true;
                    stunStart = System.currentTimeMillis();
                    setStunPeriod(((Whip)otherActor).getStunPeriod());
                    drawStunIndicator(stunPeriod);
                    this.hpTimer = System.currentTimeMillis();
                    this.showHP = true;
                }
            }

            // Maybe save this for different weapons
            //takeDamage(((AWeapon)otherActor).getAttack());
        }

        return (getHpCurrent() <= 0);
    }

    public void act(float deltaTime) {
        super.act(deltaTime);
        if (checkStun()) {
            return;
        }
        increaseAnimationTime(deltaTime);
        procStatuses();
        movement.move(this, deltaTime);
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

    public void setStunPeriod(long stun) {
        this.stunPeriod = stun;
    }

    public AMovement getMovement() {
        return movement;
    }

    public void setMovement(AMovement movement) {
        this.movement = movement;
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

    public void setInvincible(boolean invincible) {
        if(invincible){
           getStatuses().add(new Invulnerable(getAssetManager(), this));
        } else{
            getStatuses().remove(new Invulnerable(getAssetManager(), this));
        }
        this.invincible = invincible;
    }

    public  boolean isInvincible(){
        return this.invincible;
    }
}
