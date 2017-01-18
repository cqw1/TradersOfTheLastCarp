package com.totlc.levels;

import com.totlc.TradersOfTheLastCarp;
import com.totlc.Actors.Player;
import com.totlc.Actors.UI.LifeGauge;
import com.totlc.audio.MusicPlayer;

public class Level01 extends ALevel{

    public Level01(Player player) {
        setPlayer(player);
        addActor(player);

        setMusicPlayer(new MusicPlayer());
        playSong("test0");

        LifeGauge hpBar = new LifeGauge(player, 0, TradersOfTheLastCarp.CONFIG_HEIGHT);
        hpBar.moveBy(0, -hpBar.getHeight());
        addActor(hpBar);
    }
}
