package com.totlc.levels;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.totlc.Actors.UI.TextBox;
import com.totlc.Actors.UI.TextBoxFactory;
import com.totlc.Actors.UI.TutorialInfo;
import com.totlc.TradersOfTheLastCarp;

public class TutorialLevel extends ALevel {

    private TextBox textBox;

    public TutorialLevel(AssetManager assetManager) {
        super(assetManager, ObjectiveVerifier.Objectives.SURVIVE);
        setTimeLimit(10000);
        setNextLevel(Level01.class);
        setNameString("Tutorial");

//        textBox = new TextBox(assetManager, "Come find me if you want to escape!", 0, 1, 10, false);
//        textBox = new TextBox(assetManager, "Come find me if you want to escape!", 0, 1, 10);
        textBox = TextBoxFactory.createTextBox(assetManager, "Come find me if you want to escape!", 0, 1, 10);
    }

    public void initOtherLevelStuff() {
        setPlayer(TradersOfTheLastCarp.player);
        addActor(new TutorialInfo(getAssetManager()));
        addActor(textBox);

        endInit();
    }

//    @Override
//    public void act(float delta) {
//        // Let the actors update themselves
//        for (Actor a : getActors()) {
//            a.act(delta);
//        }
//    }
}
