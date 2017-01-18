package com.totlc.Actors.triggers;

import com.totlc.Actors.MovableObject;
import com.totlc.Actors.traps.ITrap;

import java.util.List;

public abstract class ATrigger extends MovableObject {

    private List<ITrap> listOfTraps;

    public void addTrap(ITrap t) { listOfTraps.add(t); }

    public List<ITrap> getListOfTraps() { return listOfTraps; }

    public void setListOfTraps(List<ITrap> lt) { listOfTraps = lt; }
}
