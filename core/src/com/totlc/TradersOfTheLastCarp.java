package com.totlc;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.totlc.Actors.UI.LifeGauge;
import com.totlc.audio.MusicPlayer;
import com.totlc.input.Level;


public class TradersOfTheLastCarp extends ApplicationAdapter {
	public static int CONFIG_WIDTH = 1024;
	public static int CONFIG_HEIGHT = 512;

	private static OrthographicCamera camera;
	private static MusicPlayer musicPlayer;

	private Level level;


	@Override
	public void create() {
		camera = new OrthographicCamera();
		camera.setToOrtho(false, CONFIG_WIDTH, CONFIG_HEIGHT);

		musicPlayer = new MusicPlayer();
		musicPlayer.setSong("test0");
		musicPlayer.play();

		Player player = new Player(0, 0);
		LifeGauge hpBar = new LifeGauge(player, 0, CONFIG_HEIGHT);
		hpBar.moveBy(0, -hpBar.getHeight());
		// Initialize input processor.
		level = new Level(player);
		level.addActor(hpBar);
		Gdx.input.setInputProcessor(level);
	}

	@Override
	public void resize(int width, int height) {

	}

	@Override
	public void render() {
		// Clear the screen with a white color. The
		// arguments to glClearColor are the red, green
		// blue and alpha component in the range [0,1]
		// of the color to be used to clear the screen.
		Gdx.gl.glClearColor(1, 1, 1, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		// Tell the camera to update its matrices.
		camera.update();
		level.act(Gdx.graphics.getDeltaTime());
		level.draw();
	}
	
	@Override
	public void dispose() {
		level.dispose();
		musicPlayer.dispose();
	}
}
