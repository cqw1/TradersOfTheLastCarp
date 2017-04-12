package com.totlc;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.assets.loaders.resolvers.InternalFileHandleResolver;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.FPSLogger;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.*;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.maps.tiled.*;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.totlc.Actors.players.Player;
import com.totlc.Actors.TotlcObject;
import com.totlc.Actors.players.Louisiana;
import com.totlc.audio.MusicPlayer;
import com.totlc.levels.*;

public class TradersOfTheLastCarp extends ApplicationAdapter {
	public static int CONFIG_WIDTH = 1600;
	public static int CONFIG_HEIGHT = 900;
	private boolean drawHitboxes = false;

	public AssetManager assetManager = new AssetManager();
	public static MusicPlayer musicPlayer = new MusicPlayer();

	private static OrthographicCamera camera;
	private static ShapeRenderer shapeRenderer;
	private static FPSLogger fpsLogger = new FPSLogger();

	public static ALevel level;

	public static BitmapFont systemFont0, systemFont0L;
	public static Player player;
	public static Class playerClass;

    public static FitViewport viewport;

    public static int SCREEN_WIDTH = CONFIG_WIDTH;
    public static int SCREEN_HEIGHT = CONFIG_HEIGHT;

	public static ParticleEffectPool starTrailPool;

	public static boolean textBoxShowing = false;

	@Override
	public void create() {
		loadAssets();
		player = new Louisiana(assetManager, 0, CONFIG_HEIGHT / 2);
		playerClass = Louisiana.class;

		// For drawing hitboxes.
		shapeRenderer = new ShapeRenderer();
		shapeRenderer.setColor(Color.RED);

        camera = new OrthographicCamera(CONFIG_WIDTH, CONFIG_HEIGHT);
        camera.setToOrtho(false, CONFIG_WIDTH, CONFIG_HEIGHT);
        camera.position.set(camera.viewportWidth/2, camera.viewportHeight/2, 0);

		shapeRenderer.setProjectionMatrix(camera.combined);

        viewport = new FitViewport(CONFIG_WIDTH, CONFIG_HEIGHT, camera);
        viewport.apply();

        initParticleEffectPools();

        level = LevelFactory.createLevel(TitleScreen.class, assetManager);
		Gdx.input.setInputProcessor(level);
        level.initLevel();
	}

	@Override
	public void resize(int width, int height) {
//        System.out.println("width: " + width);
//        System.out.println("height: " + height);
        SCREEN_WIDTH = width;
        SCREEN_HEIGHT = height;

	    viewport.update(width, height, true);
        camera.position.set(camera.viewportWidth/2, camera.viewportHeight/2, 0);
		level.getViewport().update(width, height);
	}

	@Override
	public void render() {
		if(drawHitboxes){
			fpsLogger.log();
		}
		if (assetManager.update()) {
			// Done loading. Move to next screen.
			// TODO: Move to next screen.
		} else {
			// Returns a number from 0 to 1
			float progress = assetManager.getProgress();
			System.out.println("Progress: " + progress);
			// TODO: Update progress bar.

		}
        assetManager.finishLoading();

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
			shapeRenderer.begin(ShapeRenderer.ShapeType.Line);
			Array<Actor> actors = level.getActors();
			for (Actor a : actors) {
				if (a instanceof TotlcObject) {
					shapeRenderer.polygon(((TotlcObject) a).getHitBox().getTransformedVertices());
				}
			}
			shapeRenderer.end();
		}
	}
	
	@Override
	public void dispose() {
		level.dispose();
	}

	public void loadAssets() {
	    // Special loading here
        systemFont0 =  new BitmapFont(new FileHandle(AssetList.LOVELO_FONT.toString()),
                new FileHandle(AssetList.LOVELO_IMAGE.toString()), false);
		systemFont0L =  new BitmapFont(new FileHandle(AssetList.LOVELO_LARGE.toString()));


        assetManager.setLoader(TiledMap.class, new TmxMapLoader(new InternalFileHandleResolver()));

        for (AssetList al: AssetList.values()) {
            if (al.getType() != null) {
                assetManager.load(al.toString(), al.getType());
            }
        }

        assetManager.finishLoading();

    }

    public void initParticleEffectPools() {
		TextureAtlas particleAtlas = assetManager.get(AssetList.STAR_PARTICLES.toString());
		ParticleEffect starTrail = new ParticleEffect();
		starTrail.load(Gdx.files.internal(AssetList.STAR_TRAIL.toString()), particleAtlas);
		starTrail.start();
		starTrailPool = new ParticleEffectPool(starTrail, 20, 20);

	}
}
