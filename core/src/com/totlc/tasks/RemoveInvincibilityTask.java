package com.totlc.tasks;

import com.badlogic.gdx.utils.Timer;
import com.totlc.Actors.Player;

public class RemoveInvincibilityTask extends Timer.Task {
    private Player player;

    public RemoveInvincibilityTask(Player player) {
        this.player = player;
    }

    @Override
    public void run() {
        player.setInvincible(false);
    }
}
