package com.totlc.Actors.UI;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.maps.MapProperties;
import com.totlc.levels.ALevel;

import java.lang.reflect.InvocationTargetException;

public class TextBoxFactory {
    public static final String TYPE = "TEXT";
    public static final String CARP = "CARP";
    public static final String STORK = "STORK";

    public static TextBox createTextBox(String type, String message, AssetManager assetManager) {
        try {
            if (type.equals(CARP)) {
                return new CarpTextBox(assetManager, message);
            } else if (type.equals(STORK)) {
                return new StorkTextBox(assetManager, message);
            }

            return new TextBox(assetManager, message);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    public static TextBox createTextBoxFromMp(MapProperties mp, AssetManager assetManager) {
        try {
            TextBox text = createTextBox(mp.get("type", String.class), mp.get("message", String.class), assetManager);
            return text;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }
}
