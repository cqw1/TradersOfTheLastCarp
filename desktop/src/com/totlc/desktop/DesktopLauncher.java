package com.totlc.desktop;

import com.badlogic.gdx.Files;
import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.totlc.TradersOfTheLastCarp;

public class DesktopLauncher {

	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.title = "Louisiana Jack and the Kingdom of the Crystal Carp";
        config.width = TradersOfTheLastCarp.CONFIG_WIDTH;
        config.height = TradersOfTheLastCarp.CONFIG_HEIGHT;
		config.addIcon("carp_icon128.png", Files.FileType.Internal);
		config.addIcon("carp_icon32.png", Files.FileType.Internal);
		config.addIcon("carp_icon16.png", Files.FileType.Internal);
		new LwjglApplication(new TradersOfTheLastCarp(), config);
	}
}
