package com.totlc.levels;

import com.badlogic.gdx.assets.AssetManager;

public class LevelFactory {

    public static ALevel createLevel(Class type, AssetManager assetManager) {
        if (type.equals(TitleScreen.class)) {
            return new TitleScreen(assetManager);
        } else if (type.equals(LevelSelect.class)) {
            return new LevelSelect(assetManager);
        } else if (type.equals(Level01.class)) {
            return new Level01(assetManager);
        } else if (type.equals(Level02.class)) {
            return new Level02(assetManager);
        } else if (type.equals(Level03.class)) {
            return new Level03(assetManager);
        } else if (type.equals(EndLevel.class)) {
            return new EndLevel(assetManager);
        } else if (type.equals(SandBoxLevel.class)) {
            return new SandBoxLevel(assetManager);
        }

        return null;
    }
}
