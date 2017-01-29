package com.totlc;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.utils.Array;
import com.totlc.Actors.Player;
import com.totlc.Actors.TotlcObject;
import com.totlc.levels.ALevel;
import com.totlc.levels.Level01;


public class TradersOfTheLastCarp extends ApplicationAdapter {
	public static int CONFIG_WIDTH = 1600;
	public static int CONFIG_HEIGHT = 900;
	private boolean drawHitboxes = true;

	public AssetManager assetManager = new AssetManager();

	private static OrthographicCamera camera;

	public static ALevel level;

	@Override
	public void create() {
		camera = new OrthographicCamera();
		camera.setToOrtho(false, CONFIG_WIDTH, CONFIG_HEIGHT);

		assetManager.load("sounds/trap_activation.mp3", Sound.class);

		assetManager.finishLoading();

		// Player player = new Player((TextureAtlas) assetManager.get("dummy/dummy.atlas"), 0, 0);
		Player player = new Player(assetManager, 0, CONFIG_HEIGHT / 2);

		// Initialize input processor.
		level = new Level01(player, assetManager);
		Gdx.input.setInputProcessor(level);
		level.initLevel(player);
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


		if (drawHitboxes) {
			ShapeRenderer shapeRenderer = new ShapeRenderer();
            shapeRenderer.setProjectionMatrix(camera.combined);
			shapeRenderer.setColor(Color.RED);
			shapeRenderer.begin(ShapeRenderer.ShapeType.Line);
			Array<Actor> actors = level.getActors();
			for (Actor a : actors) {
				if (a instanceof TotlcObject) {
//					System.out.println("instanceof TotlcObject");
//					System.out.println("class: " + a.getClass());
//					System.out.println("hitbox: " + ((TotlcObject)a).getHitBox());
//					System.out.println("transformed vertices: " + Arrays.toString(((TotlcObject)a).getHitBox().getTransformedVertices()));
//				System.out.println("vertices: " + Arrays.toString(((TotlcObject)a).getHitBox().getVertices()));
					shapeRenderer.polygon(((TotlcObject)a).getHitBox().getTransformedVertices());
//				shapeRenderer.polygon(((TotlcObject)a).getHitBox().getVertices());
				}
			}
			shapeRenderer.end();

		}
	}
	
	@Override
	public void dispose() {
		level.dispose();
	}
}
