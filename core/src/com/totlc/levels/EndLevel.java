package com.totlc.levels;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.maps.MapLayer;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.totlc.Actors.Player;
import com.totlc.Actors.tileset.EndScreen;
import com.totlc.AssetList;
import com.totlc.TradersOfTheLastCarp;

public class EndLevel extends ALevel {

    public EndLevel(Player player, AssetManager assetManager) {
        super(player, assetManager);
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
