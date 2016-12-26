package com.totlc;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;

import com.totlc.desktop.*;

public class TradersOfTheLastCarp extends ApplicationAdapter {
	SpriteBatch batch;
	Texture characterImg;
	private OrthographicCamera camera;

	private Rectangle character;
	
	@Override
	public void create() {
		camera = new OrthographicCamera();
		camera.setToOrtho(false, 500, 750);

		batch = new SpriteBatch();

		//derpCharizard = new Texture("derpcharizard.png");
		characterImg = new Texture(Gdx.files.internal("derpcharizard.png"));
		character = new Rectangle();

		// Dimension ratio is 1:2; width:height
		character.width = 50;
		character.height = character.width * 2;

		// Config dimensions are 500 x 500 - see DesktopLauncher.java.
		character.x = 500 / 2 - character.width / 2;
		character.y = 500 / 2 - character.height / 2;
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
		if(Gdx.input.isKeyPressed(Input.Keys.LEFT)) character.x -= 200 * Gdx.graphics.getDeltaTime();
		if(Gdx.input.isKeyPressed(Input.Keys.RIGHT)) character.x += 200 * Gdx.graphics.getDeltaTime();
		if(Gdx.input.isKeyPressed(Input.Keys.DOWN)) character.y -= 200 * Gdx.graphics.getDeltaTime();
		if(Gdx.input.isKeyPressed(Input.Keys.UP)) character.y += 200 * Gdx.graphics.getDeltaTime();

		// make sure the bucket stays within the screen bounds
		if(character.x < 0) character.x = 0;
		if(character.x > 500 - character.width) character.x = 500 - character.width;
        if(character.y < 0) character.y = 0;
        if(character.y > 500 - character.width) character.x = 500 - character.width;
	}
	
	@Override
	public void dispose() {
		batch.dispose();
		characterImg.dispose();
	}
}
