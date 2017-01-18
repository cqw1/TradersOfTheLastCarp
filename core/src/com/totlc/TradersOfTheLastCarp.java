package com.totlc;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.totlc.Actors.Player;
import com.totlc.Actors.UI.LifeGauge;
import com.totlc.Actors.enemies.Spider;
import com.totlc.Actors.enemies.Stargazer;
import com.totlc.audio.MusicPlayer;
import com.totlc.levels.ALevel;
import com.totlc.levels.Level01;


public class TradersOfTheLastCarp extends ApplicationAdapter {
	public static int CONFIG_WIDTH = 1024;
	public static int CONFIG_HEIGHT = 512;

	public AssetManager assetManager = new AssetManager();

	private static OrthographicCamera camera;

	private ALevel level;

	@Override
	public void create() {
		camera = new OrthographicCamera();
		camera.setToOrtho(false, CONFIG_WIDTH, CONFIG_HEIGHT);

		//assetManager.load("dummy_new/dummy_new.atlas", TextureAtlas.class);

        //assetManager.load("dummy/0.png", Texture.class);
		//assetManager.load("dummy/dummy.atlas", TextureAtlas.class);
		assetManager.load(AssetList.SPIDER_IDLE.toString(), TextureAtlas.class);
		assetManager.load(AssetList.SPIDER_WALK.toString(), TextureAtlas.class);
		assetManager.load(AssetList.STARGAZER_BODY.toString(), TextureAtlas.class);
		assetManager.load(AssetList.STARGAZER_EYE.toString(), TextureAtlas.class);
		assetManager.load(AssetList.STARGAZER_SPIN.toString(), TextureAtlas.class);
		assetManager.load(AssetList.STARGAZER_GAZE.toString(), TextureAtlas.class);

		assetManager.load("sounds/trap_activation.mp3", Sound.class);

		assetManager.finishLoading();

		// Player player = new Player((TextureAtlas) assetManager.get("dummy/dummy.atlas"), 0, 0);
		Player player = new Player(assetManager, 0, CONFIG_HEIGHT / 2);
		Spider spider = new Spider(assetManager, 0, 0);
		Stargazer stargazer = new Stargazer(assetManager, CONFIG_WIDTH / 3, CONFIG_HEIGHT / 3);

		// Initialize input processor.
		level = new Level01(player, assetManager);
		level.addActor(spider);
		level.addActor(stargazer);
		Gdx.input.setInputProcessor(level);
	}

	@Override
	public void resize(int width, int height) {

	}

	@Override
	public void render() {
		if (assetManager.update()) {
			// Done loading. Move to next screen.
			// TODO: Move to next screen.
		} else {
			// Returns a number from 0 to 1
			float progress = assetManager.getProgress();
			System.out.println("Progress: " + progress);
			// TODO: Update progress bar.
		}

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
	}
}
