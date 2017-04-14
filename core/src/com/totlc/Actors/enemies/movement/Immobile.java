package com.totlc.Actors.enemies.movement;

import com.totlc.Actors.Character;
import com.totlc.Actors.enemies.AEnemy;

public class Immobile extends AMovement {

    public Immobile(Character player) {
        super(player);
    }

    public void specializedMove(AEnemy self, float delta) {}

    public boolean isMoving() {
        return false;
    }
}
