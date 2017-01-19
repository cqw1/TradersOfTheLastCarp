package com.totlc.Actors.triggers;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.totlc.Actors.totlcObject;
import com.totlc.Actors.traps.ATrap;

import java.util.ArrayList;
import java.util.List;

public abstract class ATrigger extends totlcObject {

    private boolean triggered = false;

    private Actor actorThatTriggered;

    private List<ATrap> listOfTraps = new ArrayList<ATrap>();

    public ATrigger(AssetManager assetManager, float x, float y) {
        super(assetManager, x, y);
    }

    public void addTrap(ATrap t) { listOfTraps.add(t); }

    public List<ATrap> getListOfTraps() { return listOfTraps; }

    public void setListOfTraps(List<ATrap> lt) { listOfTraps = lt; }

    public boolean isTriggered() { return triggered; }

    public void setTriggered(boolean t) { this.triggered = t; }

    public Actor getActorThatTriggered() { return actorThatTriggered; }

    public void setActorThatTriggered(Actor actorThatTriggered) { this.actorThatTriggered = actorThatTriggered; }

    public void handleTrigger(boolean b, Actor a) { setTriggered(b); setActorThatTriggered(a);}

    public void endCollidesWith(Actor otherActor) {
        if (isTriggered() && otherActor == getActorThatTriggered()) {
            setTriggered(false);
            setActorThatTriggered(null);
        }
    }
}
