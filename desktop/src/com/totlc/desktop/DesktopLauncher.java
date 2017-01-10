package com.totlc.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.totlc.TradersOfTheLastCarp;

public class DesktopLauncher {

	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.title = "Traders of the Last Carp";
        config.width = TradersOfTheLastCarp.CONFIG_WIDTH;
        config.height = TradersOfTheLastCarp.CONFIG_HEIGHT;
		new LwjglApplication(new TradersOfTheLastCarp(), config);
	}
}
