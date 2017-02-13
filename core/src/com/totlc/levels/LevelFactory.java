package com.totlc.levels;

import com.badlogic.gdx.assets.AssetManager;

import java.lang.reflect.InvocationTargetException;

public class LevelFactory {

    public static ALevel createLevel(Class type, AssetManager assetManager) {
        try {
            return (ALevel) type.getConstructor(AssetManager.class).newInstance(assetManager);
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
