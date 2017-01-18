package com.totlc.Actors.triggers;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.totlc.Actors.totlcObject;
import com.totlc.Actors.traps.ATrap;

import java.util.ArrayList;
import java.util.List;

public abstract class ATrigger extends totlcObject {

    private List<ATrap> listOfTraps = new ArrayList<ATrap>();

    public void addTrap(ATrap t) { listOfTraps.add(t); }

    public List<ATrap> getListOfTraps() { return listOfTraps; }

    public void setListOfTraps(List<ATrap> lt) { listOfTraps = lt; }
}
