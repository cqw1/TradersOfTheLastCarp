package com.totlc.levels;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.totlc.Actors.UI.TextBox;
import com.totlc.Actors.UI.TutorialInfo;
import com.totlc.TradersOfTheLastCarp;

public class TutorialLevel extends ALevel {

    private TextBox textBox;

    public TutorialLevel(AssetManager assetManager) {
        super(assetManager);
        setNextLevel(Level01.class);

        textBox = new TextBox(assetManager, "testing text box", 0, 1, 10, false);
    }

    public static ALevel make(AssetManager assetManager) {
        return new TutorialLevel(assetManager);
    }

    public void initOtherLevelStuff() {
        setPlayer(TradersOfTheLastCarp.player);
        addActor(new TutorialInfo(getAssetManager()));
        addActor(textBox);
    }

    @Override
    public void act(float delta) {
        // Let the actors update themselves
        for (Actor a : getActors()) {
            a.act(delta);
        }
    }
}
