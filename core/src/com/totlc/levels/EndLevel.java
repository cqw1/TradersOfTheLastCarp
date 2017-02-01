package com.totlc.levels;

import com.badlogic.gdx.assets.AssetManager;
import com.totlc.Actors.Player;
import com.totlc.Actors.tileset.EndScreen;

public class EndLevel extends ALevel {

    public EndLevel(Player player, AssetManager assetManager) {
        super(player, assetManager);
    }

    public void initLevel(Player player) {
        addActor(new EndScreen(getAssetManager()));
    }

    public void act(float deltaTime) {}

}
