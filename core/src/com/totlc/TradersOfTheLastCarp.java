package com.totlc;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Dialog;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;


public class TradersOfTheLastCarp extends ApplicationAdapter {
	public static int CONFIG_WIDTH = 1024;
	public static int CONFIG_HEIGHT = 512;

	SpriteBatch batch;
	Texture characterImg;
	private OrthographicCamera camera;

	private Rectangle character;
	
	@Override
	public void create() {
		camera = new OrthographicCamera();
		camera.setToOrtho(false, CONFIG_WIDTH, CONFIG_HEIGHT);

		batch = new SpriteBatch();

		//derpCharizard = new Texture("derpcharizard.png");
		characterImg = new Texture(Gdx.files.internal("derpcharizard.png"));
		character = new Rectangle();

		// Dimension ratio is 1:2; width:height
		character.width = 50;
		character.height = character.width * 2;

		// Config dimensions are 1024 x 512 - see DesktopLauncher.java.
		character.x = CONFIG_WIDTH / 2 - character.width / 2;
		character.y = CONFIG_HEIGHT / 2 - character.height / 2;
	}

	@Override
	public void render() {
		// Clear the screen with a red color. The
		// arguments to glClearColor are the red, green
		// blue and alpha component in the range [0,1]
		// of the color to be used to clear the screen.
		Gdx.gl.glClearColor(1, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		// Tell the camera to update its matrices.
		camera.update();

		// Begin a new batch. Draw character image.
		batch.setProjectionMatrix(camera.combined);
		batch.begin();
		batch.draw(characterImg, character.x, character.y);
		batch.end();

		// Process user input.
		if (Gdx.input.isKeyPressed(Input.Keys.LEFT)) {
			character.x -= 200 * Gdx.graphics.getDeltaTime();
		}
		if (Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {
			character.x += 200 * Gdx.graphics.getDeltaTime();
		}
		if (Gdx.input.isKeyPressed(Input.Keys.DOWN)) {
			character.y -= 200 * Gdx.graphics.getDeltaTime();
		}
		if (Gdx.input.isKeyPressed(Input.Keys.UP)) {
			character.y += 200 * Gdx.graphics.getDeltaTime();
		}

		// make sure the bucket stays within the screen bounds
		if (character.x < 0) {
			// Check left out of bounds
			character.x = 0;
		}
		if (character.x > CONFIG_WIDTH - character.width) {
			// Check right out of bounds
			character.x = CONFIG_WIDTH - character.width;
		}
        if (character.y < 0) {
			// Check bottom out of bounds
        	character.y = 0;
		}
        if (character.y > CONFIG_HEIGHT - character.height) {
			// Check top out of bounds
        	character.y = CONFIG_HEIGHT - character.height;
		}
	}
	
	@Override
	public void dispose() {
		batch.dispose();
		characterImg.dispose();
	}
}
