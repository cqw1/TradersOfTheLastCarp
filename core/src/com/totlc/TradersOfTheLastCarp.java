package com.totlc;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.assets.loaders.resolvers.InternalFileHandleResolver;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.maps.tiled.*;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.utils.Array;
import com.totlc.Actors.Player;
import com.totlc.Actors.TotlcObject;
import com.totlc.audio.MusicPlayer;
import com.totlc.levels.*;

import java.util.ArrayList;

public class TradersOfTheLastCarp extends ApplicationAdapter {
	public static int CONFIG_WIDTH = 1600;
	public static int CONFIG_HEIGHT = 900;
	private boolean drawHitboxes = true;

	public AssetManager assetManager = new AssetManager();
	public static MusicPlayer musicPlayer = new MusicPlayer();

	private static OrthographicCamera camera;
	private static ShapeRenderer shapeRenderer;

	public static ALevel level;

	public static BitmapFont systemFont0;
	public static Player player;

	// Holds the actual level objects.
	public static ArrayList<ALevel> LEVEL_OBJ = new ArrayList<ALevel>();
	// Holds the class names of each level object. Must correspond with LEVEL_OBJ.
    // Used so we can find the index of current level by finding indexOf classname.
    public static ArrayList<String> LEVEL_NAME = new ArrayList<String>();

	@Override
	public void create() {
		camera = new OrthographicCamera();
		camera.setToOrtho(false, CONFIG_WIDTH, CONFIG_HEIGHT);

		loadAssets();
        player = new Player(assetManager, 0, CONFIG_HEIGHT / 2);

		// For drawing hitboxes.
		shapeRenderer = new ShapeRenderer();
//		shapeRenderer.setProjectionMatrix(camera.combined);
		shapeRenderer.setColor(Color.RED);

        level = LevelFactory.createLevel(TitleScreen.class, assetManager);
        Gdx.input.setInputProcessor(level);
        level.initLevel();
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
        assetManager.load("sounds/trap_activation.mp3", Sound.class);

        systemFont0 =  new BitmapFont(new FileHandle(AssetList.LOVELO_FONT.toString()),
                new FileHandle(AssetList.LOVELO_IMAGE.toString()), false);

        // Spider
        assetManager.load(AssetList.SPIDER_IDLE.toString(), TextureAtlas.class);
        assetManager.load(AssetList.SPIDER_WALK.toString(), TextureAtlas.class);

        // Stargazer
        assetManager.load(AssetList.STARGAZER_BODY.toString(), TextureAtlas.class);
        assetManager.load(AssetList.STARGAZER_EYE.toString(), TextureAtlas.class);
        assetManager.load(AssetList.STARGAZER_SPIN.toString(), TextureAtlas.class);
        assetManager.load(AssetList.STARGAZER_GAZE.toString(), TextureAtlas.class);
        assetManager.load(AssetList.SHADOW.toString(), Texture.class);

        //Flan
        assetManager.load(AssetList.FLAN.toString(), TextureAtlas.class);
        assetManager.load(AssetList.FLAN_PRIME.toString(), TextureAtlas.class);

        //Pangolini
        assetManager.load(AssetList.PANGOLINI.toString(), Texture.class);

        // Goldfish
        assetManager.load(AssetList.GOLDFISH.toString(), TextureAtlas.class);
        assetManager.load(AssetList.GOLDFISH_GLOW.toString(), TextureAtlas.class);

        // Effects
        assetManager.load(AssetList.PARTICLES.toString(), TextureAtlas.class);
        assetManager.load(AssetList.IMPACT.toString(), TextureAtlas.class);
        assetManager.load(AssetList.EXCLAMATION.toString(), TextureAtlas.class);

        // BasicTileSet and terrain
        assetManager.load(AssetList.DEFAULT_TILESET.toString(), Texture.class);
        assetManager.load(AssetList.WALL_LEFT.toString(), Texture.class);
        assetManager.load(AssetList.WALL_RIGHT.toString(), Texture.class);
        assetManager.load(AssetList.WALL_TOP.toString(), Texture.class);
        assetManager.load(AssetList.WALL_BOTTOM.toString(), Texture.class);
        assetManager.load(AssetList.END_CREDITS.toString(), Texture.class);

        //TMX files
        assetManager.setLoader(TiledMap.class, new TmxMapLoader(new InternalFileHandleResolver()));
        assetManager.load(AssetList.LEVEL01_TMX.toString(), TiledMap.class);
        assetManager.load(AssetList.LEVEL02_TMX.toString(), TiledMap.class);
        assetManager.load(AssetList.LEVEL03_TMX.toString(), TiledMap.class);

        // Arrow
        assetManager.load(AssetList.PROJECTILE_ARROW.toString(), Texture.class);

        // Starshot
        assetManager.load(AssetList.PROJECTILE_STAR_SHOT.toString(), TextureAtlas.class);
        assetManager.load(AssetList.STAR_PARTICLES.toString(), TextureAtlas.class);

        // Arrow Trap
        assetManager.load(AssetList.ARROW_TRAP.toString(), TextureAtlas.class);

        // Spider Trap
        assetManager.load(AssetList.SPIDER_TRAP.toString(), TextureAtlas.class);

        // Fire Trap
        assetManager.load(AssetList.FIRE_TRAP_LEFT.toString(), Texture.class);
        assetManager.load(AssetList.FIRE_TRAP_RIGHT.toString(), Texture.class);
        assetManager.load(AssetList.FIRE_TRAP_LEFT.toString(), Texture.class);
        assetManager.load(AssetList.FIRE_TRAP_DOWN.toString(), Texture.class);
        assetManager.load(AssetList.EYE_GLOW.toString(), TextureAtlas.class);

        // Button Trigger
        assetManager.load(AssetList.PLATE_BROWN.toString(), TextureAtlas.class);

        // Items
        assetManager.load(AssetList.ITEM_PACK.toString(), TextureAtlas.class);

        // UI
        assetManager.load(AssetList.ICON_PACK.toString(), TextureAtlas.class);
        assetManager.load(AssetList.UI_BAR.toString(), Texture.class);
        assetManager.load(AssetList.INVENTORY_BOX.toString(), Texture.class);
        assetManager.load(AssetList.LIFE_GAUGE.toString(), Texture.class);
        assetManager.load(AssetList.LIFE_BAR.toString(), Texture.class);
        assetManager.load(AssetList.DIED_SCREEN.toString(), Texture.class);
        assetManager.load(AssetList.TITLE_SCREEN.toString(), Texture.class);
        assetManager.load(AssetList.OPTION_QUICKPLAY.toString(), Texture.class);
        assetManager.load(AssetList.OPTION_LVLSELECT.toString(), Texture.class);

        // Player
        // Standing assets.
        assetManager.load(AssetList.PLAYER_STAND_UP.toString(), Texture.class);
        assetManager.load(AssetList.PLAYER_STAND_DOWN.toString(), Texture.class);
        assetManager.load(AssetList.PLAYER_STAND_LEFT.toString(), Texture.class);
        assetManager.load(AssetList.PLAYER_STAND_RIGHT.toString(), Texture.class);

        // Walking animations
        assetManager.load(AssetList.PLAYER_WALK_UP.toString(), TextureAtlas.class);
        assetManager.load(AssetList.PLAYER_WALK_DOWN.toString(), TextureAtlas.class);
        assetManager.load(AssetList.PLAYER_WALK_LEFT.toString(), TextureAtlas.class);
        assetManager.load(AssetList.PLAYER_WALK_RIGHT.toString(), TextureAtlas.class);

        // Whipping animations
        assetManager.load(AssetList.PLAYER_WHIP_UP.toString(), TextureAtlas.class);
        assetManager.load(AssetList.PLAYER_WHIP_DOWN.toString(), TextureAtlas.class);
        assetManager.load(AssetList.PLAYER_WHIP_LEFT.toString(), TextureAtlas.class);
        assetManager.load(AssetList.PLAYER_WHIP_RIGHT.toString(), TextureAtlas.class);

        // Whip
        assetManager.load(AssetList.WHIP_UP.toString(), TextureAtlas.class);
        assetManager.load(AssetList.WHIP_DOWN.toString(), TextureAtlas.class);
        assetManager.load(AssetList.WHIP_LEFT.toString(), TextureAtlas.class);
        assetManager.load(AssetList.WHIP_RIGHT.toString(), TextureAtlas.class);

        assetManager.finishLoading();

    }
}
