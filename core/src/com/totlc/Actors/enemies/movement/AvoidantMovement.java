package com.totlc.Actors.enemies.movement;

import com.totlc.Actors.Character;
import com.totlc.Actors.enemies.AEnemy;

public class AvoidantMovement extends AMovement{

    public AvoidantMovement(Character player) {
        super(player);
    }

    @Override
    public void specializedMove(AEnemy self, float deltaTime) {

    }
}
