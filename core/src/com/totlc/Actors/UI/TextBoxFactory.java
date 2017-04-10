package com.totlc.Actors.UI;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.maps.MapProperties;
import com.totlc.levels.ALevel;

import java.lang.reflect.InvocationTargetException;

public class TextBoxFactory {
    public static final String TYPE = "TEXT";

    public static TextBox createTextBox(String message, AssetManager assetManager) {
        try {
            return new TextBox(assetManager, message);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    public static TextBox createTextBoxFromMp(MapProperties mp, AssetManager assetManager) {
        try {
            TextBox text = createTextBox(mp.get("message", String.class), assetManager);
            return text;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }
}
