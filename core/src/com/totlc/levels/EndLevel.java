package com.totlc.levels;

import com.badlogic.gdx.assets.AssetManager;
import com.totlc.Actors.Player;
import com.totlc.Actors.tileset.EndScreen;
import com.totlc.TradersOfTheLastCarp;

public class EndLevel extends ALevel {

    public EndLevel(AssetManager assetManager) {
        super(assetManager);
    }

    public void initLevel() {
        TradersOfTheLastCarp.musicPlayer.stop();
        TradersOfTheLastCarp.musicPlayer.setSong("test5");
        TradersOfTheLastCarp.musicPlayer.play();

        addActor(new EndScreen(getAssetManager()));



//        float unitScale = 1 / 64f;
//        OrthogonalTiledMapRenderer renderer = new OrthogonalTiledMapRenderer(map, unitScale);
//        renderer.render();
    }

    public void act(float deltaTime) {}

}
