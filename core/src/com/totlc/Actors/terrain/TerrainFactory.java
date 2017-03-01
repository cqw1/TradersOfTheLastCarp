package com.totlc.Actors.terrain;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.MapProperties;

public class TerrainFactory {

    public static final String TYPE = "TERRAIN";
    public static final String ROCK = "ROCK";

    public static AWall createTerrain(String type, AssetManager assetManager, float x, float y) {
        if (type.equals(ROCK)) {
            return new Rock(assetManager, x, y);
        }

        return null;
    }

    public static AWall createTerrainFromMP(MapProperties mp, AssetManager assetManager) {
        return createTerrain(mp.get("type", String.class), assetManager, mp.get("x", float.class), mp.get("y", float.class));
    }
}
