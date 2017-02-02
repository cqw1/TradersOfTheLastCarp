package com.totlc.levels;

import com.badlogic.gdx.assets.AssetManager;
import com.totlc.Actors.Player;
import com.totlc.Actors.tileset.EndScreen;

public class EndLevel extends ALevel {

    public EndLevel(Player player, AssetManager assetManager) {
        super(player, assetManager);
    }

    public void initLevel(Player player) {
        playSong("test5");
        addActor(new EndScreen(getAssetManager()));
    }

    public void act(float deltaTime) {}

}
