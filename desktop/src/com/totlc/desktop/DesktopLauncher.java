package com.totlc.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.totlc.TradersOfTheLastCarp;

public class DesktopLauncher {

	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.title = "Traders of the Last Carp";
        config.width = 500;
        config.height = 500;
		new LwjglApplication(new TradersOfTheLastCarp(), config);
	}
}
