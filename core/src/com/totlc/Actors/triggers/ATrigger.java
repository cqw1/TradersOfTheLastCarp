package com.totlc.Actors.triggers;

import com.totlc.Actors.totlcObject;
import com.totlc.Actors.traps.ATrap;

import java.util.ArrayList;
import java.util.List;

public abstract class ATrigger extends totlcObject {

    boolean isTriggered = false;

    private List<ATrap> listOfTraps = new ArrayList<ATrap>();

    public void addTrap(ATrap t) { listOfTraps.add(t); }

    public List<ATrap> getListOfTraps() { return listOfTraps; }

    public void setListOfTraps(List<ATrap> lt) { listOfTraps = lt; }

    public boolean isTriggered() { return isTriggered; }

    public void setTriggered(boolean triggered) { isTriggered = triggered; }

}
