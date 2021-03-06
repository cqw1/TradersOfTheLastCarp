package com.totlc.levels;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.MapProperties;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.totlc.Actors.TotlcObject;
import com.totlc.Actors.players.Player;
import com.totlc.Actors.UI.*;
import com.totlc.Actors.carps.CrystalCarp;
import com.totlc.Actors.enemies.AEnemy;
import com.totlc.Actors.enemies.EnemyFactory;
import com.totlc.Actors.items.APickup;
import com.totlc.Actors.items.PickupFactory;
import com.totlc.Actors.terrain.*;
import com.totlc.Actors.tileset.BasicTileSet;
import com.totlc.Actors.traps.ATrap;
import com.totlc.Actors.traps.TrapFactory;
import com.totlc.Actors.triggers.ATrigger;
import com.totlc.Actors.triggers.TriggerFactory;
import com.totlc.AssetList;
import com.totlc.Directionality;
import com.totlc.TradersOfTheLastCarp;
import com.totlc.Util;
import com.totlc.levels.ObjectiveVerifier.Objectives;

import java.util.HashMap;
import java.util.HashSet;

import static com.totlc.TradersOfTheLastCarp.CONFIG_HEIGHT;
import static com.totlc.TradersOfTheLastCarp.CONFIG_WIDTH;

public abstract class ALevel extends Stage {

    private Player player = TradersOfTheLastCarp.player;
    private TotlcObject dedScreen;

    private FitViewport viewport = new FitViewport(CONFIG_WIDTH, CONFIG_HEIGHT);

    private AssetManager assetManager;

    private TextureRegion objIcon;

    private NextStage nextStage;
    private Class nextLevel;
    private Vector2 playerStartPosition = new Vector2(DEFAULT_WALLSIZE + 10, CONFIG_HEIGHT / 2 - 50);

    // Level information strings.
    private String nameString, infoString;

    private Objectives objective;

    private long startTime;

    private long timeLimit = 0;

    private float wallSize = DEFAULT_WALLSIZE;
    public static float DEFAULT_WALLSIZE = 64;

    // LEvel entity z-indices.
    private static int terrainIndex = 1;
    private static int uiIndex = 2000;

    HashSet<Actor> toBeRemoved = new HashSet<Actor>();

    public ALevel(AssetManager assetManager) {
        this.assetManager = assetManager;
        this.dedScreen = new DiedScreen(assetManager);
        this.nextStage = new NextStage(assetManager, ALevel.DEFAULT_WALLSIZE, player.getHeight());
        setStartTime(System.currentTimeMillis());
        BasicTileSet bts = new BasicTileSet(getAssetManager());
        addActor(bts);
    }


    public ALevel(AssetManager assetManager, Objectives objective) {
        this(assetManager);
        this.objective = objective;
        drawObjectives();
    }

    public void drawObjectives() {
        TextureAtlas atlas = assetManager.get(AssetList.ICON_PACK.toString());
        switch (getObjective().getID()){
            case 0:
                // Survive.
                objIcon = atlas.findRegion("time_icon");
                setInfoString("SURVIVE FOR");
                break;
            case 1:
                // Unlock.
                objIcon = atlas.findRegion("lock_icon");
                setInfoString("UNLOCK DOOR");
                break;
            case 2:
                // Destroy.
                objIcon = atlas.findRegion("skull_icon");
                setInfoString("DEFEAT ENEMIES");
                break;
        }
        objIcon.getTexture().setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
    }

    public void initLevel() {
        this.setViewport(TradersOfTheLastCarp.viewport);
        this.getViewport().update(TradersOfTheLastCarp.SCREEN_WIDTH, TradersOfTheLastCarp.SCREEN_HEIGHT, true);
//        camera.position.set(camera.viewportWidth/2, camera.viewportHeight/2, 0);

        // Prevents the case where the user holds down a key and exits the level, then we don't ever set the moving
        // direction back to false. The player automatically starts walking once the next level loads then.
        player.setMovingLeft(false);
        player.setMovingRight(false);
        player.setMovingUp(false);
        player.setMovingDown(false);

        initOtherLevelStuff();
    }

    public abstract void initOtherLevelStuff();

    public void endInit() {
        initWalls();

        player.moveAbs(playerStartPosition.x, playerStartPosition.y);
        addActor(player);
//        addActor(player.getWeapon());
        getPlayer().setZIndex(100);
        initUI();
    }

    @Override
    public void act(float delta) {

        //First let actors update themselves
        for (Actor a: getActors()) {
            a.act(delta);
        }

        //Check if the player has died
        if (player.getHpCurrent() <= 0) {
            return;
        }

        //Check whether we should unlock the stage
        if (ObjectiveVerifier.verifyDone(this) <= 0.0f) {
            nextStage.unlock();
        }

        //Check whether the player needs to move to the next level
        if (Intersector.overlapConvexPolygons(player.getHitBox(), nextStage.getHitBox())) {
            initNextLevel();
            return;
        }

        //Now check for collisions
        Array<Actor> allActors = getActors();
        toBeRemoved.clear();

        boolean removeA = false;
        boolean removeB = false;
        for (int aCounter = 0; aCounter < allActors.size; aCounter++) {
            // Ignore if not an interactable object or being removed
            Actor a = allActors.get(aCounter);
            if (!(a instanceof TotlcObject) || toBeRemoved.contains(a)) {
                continue;
            }

            TotlcObject objA = (TotlcObject) a;
            for (int bCounter = aCounter; bCounter < allActors.size; bCounter++) {
                // Ignore again, except add that we ignore references to ourselves, or objects being removed
                Actor b = allActors.get(bCounter);
                if (!(b instanceof TotlcObject) || a == b || toBeRemoved.contains(b)) { // || toBeRemoved.contains(b)
                    continue;
                }

                TotlcObject objB = (TotlcObject) b;
                if (Intersector.overlapConvexPolygons(objA.getHitBox(), objB.getHitBox())) {
                    removeA = objA.collidesWith(objB);
                    removeB = objB.collidesWith(objA);

                    if ((objA instanceof Player && removeA) ||
                            (objB instanceof Player && removeB)) {
                        handlePlayerDeath();
                    }

                    if (removeA) {
                        toBeRemoved.add(objA);
                    }
                    if (removeB) {
                        toBeRemoved.add(objB);
                    }
                } else {
                    objA.endCollidesWith(objB);
                    objB.endCollidesWith(objA);
                }
            }
        }

        //Remove any actors that need to disappear
        for (Actor beingRemoved: toBeRemoved) {
            for (Actor actor: allActors) {
                // If actor is being removed, then it should endCollidesWith all other objects (e.g. an enemy got killed
                // on a trigger, the trigger should know that it is no longer colliding with the enemy being removed.)
                if (actor instanceof TotlcObject) {
                    ((TotlcObject) actor).endCollidesWith(beingRemoved);
                }
            }

            beingRemoved.remove();
        }
    }

    @Override
    public boolean keyDown(int keycode) {
        if (keycode == Input.Keys.LEFT) {
            player.setMovingLeft(true);
            player.setIsFacing(Directionality.LEFT);
            return true;
        }
        if (keycode == Input.Keys.RIGHT) {
            player.setMovingRight(true);
            player.setIsFacing(Directionality.RIGHT);
            return true;
        }
        if (keycode == Input.Keys.UP) {
            player.setMovingUp(true);
            player.setIsFacing(Directionality.UP);
            return true;
        }
        if (keycode == Input.Keys.DOWN) {
            player.setMovingDown(true);
            player.setIsFacing(Directionality.DOWN);
            return true;
        }

        if (keycode == Input.Keys.SPACE) {
            if (TradersOfTheLastCarp.textBoxShowing) {
                for (Actor actor: getActors()) {
                    if (actor instanceof TextBox) {
                        ((TextBox) actor).removeTextBox();
                    }
                }
            } else {
                if (player.getHpCurrent() > 0){
                    player.createWeapon();
                } else {
                    try {
                        int numGoldfish = TradersOfTheLastCarp.player.getGoldfishCount() / 2;
                        TradersOfTheLastCarp.player = (Player) TradersOfTheLastCarp.playerClass.getConstructor(AssetManager.class).newInstance(assetManager);
                        TradersOfTheLastCarp.player.setGoldfishCount(numGoldfish);
                        ALevel nextLevelObject = LevelFactory.createLevel(this.getClass(), assetManager);
                        loadLevel(nextLevelObject);
                        Sound sound = Gdx.audio.newSound(Gdx.files.internal("sounds/negative0.wav"));
                        sound.play(1.0f);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
            return true;
        }

        if (keycode == Input.Keys.ESCAPE) {
            TradersOfTheLastCarp.textBoxShowing = false;
            ALevel nextLevelObject = LevelFactory.createLevel(TitleScreen.class, assetManager);
            loadLevel(nextLevelObject);
            Sound sound = Gdx.audio.newSound(Gdx.files.internal("sounds/negative0.wav"));
            sound.play(1.0f);
            return true;
        }


        return false;
    }

    @Override
    public boolean keyUp(int keycode) {
        if (keycode == Input.Keys.LEFT){
            player.setMovingLeft(false);
            return true;
        }
        if (keycode == Input.Keys.RIGHT){
            player.setMovingRight(false);
            return true;
        }
        if (keycode == Input.Keys.UP){
            player.setMovingUp(false);
            return true;
        }
        if (keycode == Input.Keys.DOWN){
            player.setMovingDown(false);
            return true;
        }
        return false;
    }

    public void initUI(){
        int xOffset = 32;
        int yOffset = 32;
        // Add health bar, inventory bar, and level information bar.
        LifeGauge hpBar = new LifeGauge(getAssetManager(), player, 0, CONFIG_HEIGHT);
        Inventory inventory = new Inventory(getAssetManager(), player, 0, CONFIG_HEIGHT);
        Bar bar0 = new Bar(getAssetManager(), 0, CONFIG_HEIGHT, (int)(hpBar.getWidth() + inventory.getWidth() + 32), 64);
        LevelInfo info = new LevelInfo(getAssetManager(), this, CONFIG_WIDTH, CONFIG_HEIGHT);

        //Move them to their correct position
        bar0.moveBy(-xOffset, -(bar0.getHeight() + yOffset));
        hpBar.moveBy(inventory.getWidth() - xOffset * 0.3f, -(hpBar.getHeight() + yOffset * 0.85f));
        inventory.moveBy(0, -(inventory.getHeight() + yOffset * 0.6f));
        info.moveBy(-(info.getWidth() - xOffset), -(info.getHeight() + yOffset));

        addActor(bar0);
        addActor(hpBar);
        addActor(inventory);
        addActor(info);
        bar0.setZIndex(uiIndex);
        hpBar.setZIndex(uiIndex + 1);
        inventory.setZIndex(uiIndex + 2);
        info.setZIndex(uiIndex);
    }

    public void loadFromTMX(String tmxFileName) {
        TiledMap map = getAssetManager().get(tmxFileName);
        setNameString(Util.parseLevelString(tmxFileName));
        TradersOfTheLastCarp.textBoxShowing = false;

        MapProperties mapProperties = map.getProperties();
        switch(mapProperties.get("objective", Integer.class)) {
            case 0:
                this.objective = Objectives.SURVIVE;
                setTimeLimit(mapProperties.get("time", Integer.class));
                break;
            case 1:
                this.objective = Objectives.UNLOCK;
                break;
            case 2:
                this.objective = Objectives.DESTROY;
                break;
        }
        this.nextStage = new NextStage(assetManager, ALevel.DEFAULT_WALLSIZE, player.getHeight(), getObjective());
        drawObjectives();

        try {
            //Traps have least priority, load first
            HashMap<Integer, ATrap> id2Trap = new HashMap<Integer, ATrap>(10);
            for (MapObject mo : map.getLayers().get(TrapFactory.TYPE).getObjects()) {
                MapProperties currentObjProp = mo.getProperties();
                ATrap currentTrap = TrapFactory.createTrapFromMP(currentObjProp, getAssetManager());
                addActor(currentTrap);
                currentTrap.setZIndex(terrainIndex);

                // Update mapping so triggers can use it
                id2Trap.put(currentObjProp.get("id", Integer.class), currentTrap);
            }

            //Triggers after
            for (MapObject mo : map.getLayers().get(TriggerFactory.TYPE).getObjects()) {
                MapProperties currentObjProp = mo.getProperties();
                ATrigger currentTrigger = TriggerFactory.createTriggerFromMP(currentObjProp, getAssetManager());

                // Map triggers tp traps
                for (String i : currentObjProp.get("trap_id", String.class).split(":")) {
                    currentTrigger.addTrap(id2Trap.get(Integer.parseInt(i)));
                }

                addActor(currentTrigger);
                currentTrigger.setZIndex(terrainIndex + 1);
            }
        } catch (NullPointerException n) {
            System.err.print("No trap or trigger layer detected.\n");
        }

        try {
            //Lay out items
            for (MapObject mo : map.getLayers().get(PickupFactory.TYPE).getObjects()) {
                MapProperties currentObjProp = mo.getProperties();
                APickup currentItem = PickupFactory.createPickupFromMP(currentObjProp, getAssetManager());
                addActor(currentItem);
                currentItem.setZIndex(terrainIndex + 2);
            }
        } catch (NullPointerException n) {
            System.err.print("No item layer detected.\n");
        }

        try {
            //Lay down terrain
            for (MapObject mo: map.getLayers().get(TerrainFactory.TYPE).getObjects()) {
                MapProperties currentObjProp = mo.getProperties();
                AWall currentTerrain = TerrainFactory.createTerrainFromMP(currentObjProp, getAssetManager());
                addActor(currentTerrain);
                currentTerrain.setZIndex(terrainIndex);
            }
        } catch ( NullPointerException n) {
            System.err.print("No terrain layer detected.\n");
        }

        try {
            //Enemies
            for (MapObject mo: map.getLayers().get(EnemyFactory.TYPE).getObjects()) {
                MapProperties currentObjProp = mo.getProperties();
                AEnemy currentEnemy = EnemyFactory.createEnemyFromMP(currentObjProp, getAssetManager());
                addActor(currentEnemy);
            }
        } catch (NullPointerException n) {
            System.err.print("No enemy layer detected.\n");
        }

        endInit();

        try {
            //UI elements go on top
            for (MapObject mo: map.getLayers().get(TextBoxFactory.TYPE).getObjects()) {
                MapProperties currentObjProp = mo.getProperties();
                TextBox tb = TextBoxFactory.createTextBoxFromMp(currentObjProp, getAssetManager());
                addActor(tb);
                tb.setZIndex(uiIndex);
            }
        } catch (NullPointerException n) {
            System.err.print("No UI layer detected\n");
        }
    }

    public void handlePlayerDeath() {
//        Sound sound = Gdx.audio.newSound(Gdx.files.internal(AssetList.GAME_OVER_SOUND.toString()));
//        sound.play(1.0f);
        addActor(dedScreen);
        //Button Prompt
        addActor(new ButtonPrompt(assetManager, AssetList.BUTTON_PROMPT_ESC.toString(), TradersOfTheLastCarp.CONFIG_WIDTH - 300 * 0.5f - 50, 100) {
            private float baseY = getY();

            @Override
            public void draw(Batch batch, float alpha) {
                if (System.currentTimeMillis() % 1000 <= 200) {
                    return;
                }
                batch.draw(getAsset(), getX(), getY(), 300 * 0.5f, 120 * 0.5f);
            }

            @Override
            public void update() {
//                setY(baseY - (optionFocusIndex - 1) * 120 * cursorScale);
            }
        });

        addActor(new ButtonPrompt(assetManager, AssetList.BUTTON_PROMPT_SPACE_REPLAY.toString(), TradersOfTheLastCarp.CONFIG_WIDTH - 300 * 0.5f - 50, 50) {
            private float baseY = getY();

            @Override
            public void draw(Batch batch, float alpha) {
                if (System.currentTimeMillis() % 1000 <= 200) {
                    return;
                }
                batch.draw(getAsset(), getX(), getY(), 300 * 0.5f, 120 * 0.5f);
            }

            @Override
            public void update() {
//                setY(baseY - (optionFocusIndex - 1) * 120 * cursorScale);
            }
        });

        CrystalCarp carp = new CrystalCarp(
                assetManager,
                CONFIG_WIDTH / 2 - CrystalCarp.WIDTH / 2,
                CONFIG_HEIGHT / 4 - CrystalCarp.HEIGHT / 2,
                1,
                1,
                1f
        );
        carp.setVoice(AssetList.YOU_LOSE.toString());
        addActor(carp);
    }

    @Override
    public boolean keyTyped(char key) {
        return false;
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        return false;
    }

    @Override
    public boolean mouseMoved(int screenX, int screenY) {
        return false;
    }

    @Override
    public boolean scrolled(int amount) {
        return false;
    }

    //Getters and setters
    public void setPlayer(Player p) { player = p; }
    public Player getPlayer() {
        return player;
    }



    public Vector2 getPlayerStartPosition() {
        return playerStartPosition;
    }

    public void setPlayerStartPosition(Vector2 playerStartPosition) {
        this.playerStartPosition = playerStartPosition;
    }

    public AssetManager getAssetManager() {
        return assetManager;
    }

    public void setAssetManager(AssetManager assetManager) {
        this.assetManager = assetManager;
    }

    public String getNameString() {
        return nameString;
    }

    public void setNameString(String nameString) {
        this.nameString = nameString;
    }

    public String getInfoString() {
        return infoString;
    }

    public void setInfoString(String infoString) {
        this.infoString = infoString;
    }

    public String getObjectiveInfo(){
        switch (getObjective().getID()){
            case 0:
                // Survive.
                return " " + Math.max(Math.ceil(ObjectiveVerifier.verifyDone(this) / 100) / 10, 0);
            case 1:
                // Unlock.
                return "";
            case 2:
                // Destroy.
                return "x " + (int)ObjectiveVerifier.verifyDone(this);
            default:
                return "???";
        }
    }

    public Objectives getObjective() {
        return objective;
    }

    public void setObjective(Objectives objective) {
        this.objective = objective;
    }

    public long getStartTime() {
        return startTime;
    }

    public void setStartTime(long startTime) {
        this.startTime = startTime;
    }

    public long getTimeLimit() {
        return timeLimit;
    }

    public void setTimeLimit(long timeLimit) {
        this.timeLimit = timeLimit;
    }

    public float getWallSize() {
        return wallSize;
    }

    public void setWallSize(float wallSize) {
        this.wallSize = wallSize;
    }

    public TextureRegion getObjIcon() {
        return objIcon;
    }

    public void setObjIcon(TextureRegion objIcon) {
        this.objIcon = objIcon;
    }

    public Class getNextLevel() {
        return nextLevel;
    }

    public void setNextLevel(Class nextLevel) {
        this.nextLevel = nextLevel;
    }

    public void initWalls() {
        addActor(nextStage);
        addActor(nextStage.getPhysicalBlock());
        nextStage.getPhysicalBlock().setZIndex(terrainIndex);

        AWall lw = new AWall(assetManager, new Rectangle(0, 0, wallSize, CONFIG_HEIGHT));
        // two separate walls for the exit
        AWall rwBot = new AWall(assetManager, new Rectangle(CONFIG_WIDTH - wallSize, 0, wallSize, nextStage.getY()));
        AWall rwTop = new AWall(assetManager, new Rectangle(CONFIG_WIDTH - wallSize, nextStage.getY() + nextStage.getHeight(),
                wallSize, CONFIG_HEIGHT - nextStage.getY() + nextStage.getHeight()));
        AWall tw = new AWall(assetManager, new Rectangle(0, CONFIG_HEIGHT - wallSize, CONFIG_WIDTH, wallSize));
        AWall bw = new AWall(assetManager, new Rectangle(0, 0, CONFIG_WIDTH, wallSize));

        // add actors
        addActor(lw);
        addActor(rwBot);
        addActor(rwTop);
        addActor(tw);
        addActor(bw);

        bw.setZIndex(terrainIndex + 1);
        tw.setZIndex(terrainIndex + 1);
        rwTop.setZIndex(terrainIndex);
        rwBot.setZIndex(terrainIndex);
        lw.setZIndex(terrainIndex);
    }

    public void initNextLevel() {
        ALevel nextLevelObject = LevelFactory.createLevel(nextLevel, assetManager);
        loadLevel(nextLevelObject);
    }

    public void loadLevel(ALevel toBeLoaded) {
        toBeLoaded.initLevel();

        TradersOfTheLastCarp.level = toBeLoaded;
        Gdx.input.setInputProcessor(TradersOfTheLastCarp.level);
    }

    // Full restore of the player's health.
    public void restorePlayerHealth() {
        player.setHpCurrent(player.getHpMax());
    }

    public NextStage getNextStage() {
        return nextStage;
    }

    public void setNextStage(NextStage nextStage) {
        this.nextStage = nextStage;
    }

}
