package com.totlc.Actors.triggers;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.totlc.Actors.Player;
import com.totlc.Actors.TotlcObject;
import com.totlc.Actors.enemies.AEnemy;
import com.totlc.Actors.traps.ATrap;
import com.totlc.AssetList;

import java.util.ArrayList;
import java.util.List;

public abstract class ATrigger extends TotlcObject {

    private boolean triggered = false;

    private Actor actorThatTriggered;

    private List<ATrap> listOfTraps = new ArrayList<ATrap>();

    public ATrigger(AssetManager assetManager, Rectangle r) {
        super(assetManager, r);
    }

    public void act(float deltaTime){
        increaseAnimationTime(deltaTime);
        if (!isTriggered()) {
            setAnimationTime(0);
        }
    }

    public void addTrap(ATrap t) { listOfTraps.add(t); }

    public List<ATrap> getListOfTraps() { return listOfTraps; }

    public void setListOfTraps(List<ATrap> lt) { listOfTraps = lt; }

    public boolean isTriggered() { return triggered; }

    public void setTriggered(boolean t) { this.triggered = t; }

    public Actor getActorThatTriggered() { return actorThatTriggered; }

    public void setActorThatTriggered(Actor actorThatTriggered) { this.actorThatTriggered = actorThatTriggered; }

    public void handleTrigger(boolean b, Actor a) { setTriggered(b); setActorThatTriggered(a);}

    @Override
    public boolean collidesWith(Actor otherActor) {
        if (otherActor instanceof Player ||
                (otherActor instanceof AEnemy &&
                        !((AEnemy) otherActor).isFloating())) {

            if (!isTriggered()) {
                Sound sound = Gdx.audio.newSound(Gdx.files.internal(AssetList.TRAP_ACTIVATION.toString()));
                sound.play(0.5f);

                for (ATrap trap : getListOfTraps()) {
                    trap.setup();
                }
            }

            handleTrigger(true, otherActor);
        }

        return false;
    }

    @Override
    public void endCollidesWith(Actor otherActor) {
        if (isTriggered() && otherActor == getActorThatTriggered()) {
            setTriggered(false);
            setActorThatTriggered(null);
        }
    }
}
