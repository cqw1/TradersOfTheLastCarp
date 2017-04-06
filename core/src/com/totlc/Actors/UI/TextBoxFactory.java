package com.totlc.Actors.UI;

import com.badlogic.gdx.assets.AssetManager;
import com.totlc.levels.ALevel;

import java.lang.reflect.InvocationTargetException;

public class TextBoxFactory {
    public static TextBox createTextBox(AssetManager assetManager, String message, float delay, float talkTime, float duration) {
        try {
            return TextBox.class.getConstructor(AssetManager.class, String.class, Float.TYPE, Float.TYPE, Float.TYPE)
                    .newInstance(assetManager, message, delay, talkTime, duration);
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }
        return null;
    }
}
