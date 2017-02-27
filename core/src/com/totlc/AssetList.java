package com.totlc;

import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.maps.tiled.TiledMap;

public enum AssetList {
    //Sounds
    TRAP_ACTIVATION("sounds/trap_activation.mp3", Sound.class),

    // UI elements and icons.
    UI_BAR("UI/UIBar.png", Texture.class),
    LIFE_GAUGE("UI/LifeGauge.png", Texture.class),
    LIFE_BAR("UI/LifeGaugeBar.png", Texture.class),
    INVENTORY_BOX("UI/Satchel.png", Texture.class),
    ICON_PACK("UI/icon_pak.atlas", TextureAtlas.class),
    LOVELO_FONT("UI/Lovelo.fnt", null),
    LOVELO_IMAGE("UI/Lovelo.png", Texture.class),
    ITEM_PACK("sprites/items/items.atlas", TextureAtlas.class),
    DIED_SCREEN("UI/udied.png", Texture.class),
    TITLE_SCREEN("UI/Splash.png", Texture.class),
    BUTTON_PROMPT_ESC("UI/ButtonPromptESC.png", Texture.class),
    BUTTON_PROMPT_SPACE("UI/ButtonPromptSpace.png", Texture.class),
    OPTION_QUICKPLAY("UI/splash_options_0.png", Texture.class),
    OPTION_LVLSELECT("UI/splash_options_1.png", Texture.class),
    LEVEL_SELECT_SCREEN("UI/LevelSelect.png", Texture.class),
    QUESTION_MARK_SELECT("UI/QuestionMarkSelect.png", Texture.class),
    QUESTION_MARK_SELECT_BORDER("UI/QuestionMarkSelectBorder.png", Texture.class),
    CRYSTAL_CARP_TURN("sprites/CrystalCarp/carp_turn.atlas", TextureAtlas.class),
    CRYSTAL_CARP_TALK("sprites/CrystalCarp/carp_talk.atlas", TextureAtlas.class),

    // Enemy Sprites.
    SPIDER_IDLE("sprites/spider/spider_idle.atlas", TextureAtlas.class),
    SPIDER_WALK("sprites/spider/spider_walk.atlas", TextureAtlas.class),
    STARGAZER_BODY("sprites/stargazer/stargazer_body.atlas", TextureAtlas.class),
    STARGAZER_EYE("sprites/stargazer/stargazer_eye.atlas", TextureAtlas.class),
    STARGAZER_SPIN("sprites/stargazer/stargazer_spin.atlas", TextureAtlas.class),
    STARGAZER_GAZE("sprites/stargazer/stargazer_gaze.atlas", TextureAtlas.class),
    SHADOW("sprites/float_shadow.png", Texture.class),
    FLAN("sprites/flan/flan_walk.atlas", TextureAtlas.class),
    FLAN_JUMP("sprites/flan/flan_jump.atlas", TextureAtlas.class),
    FLAN_PRIME("sprites/flan/flan_prime.atlas", TextureAtlas.class),
    FLAN_JUMP_PRIME("sprites/flan/flan_prime_jump.atlas", TextureAtlas.class),
    PANGOLINI("sprites/pangolini/pangolini.png", Texture.class),
    JELLYFISH("sprites/gelatin_king/jelly_fish.atlas", TextureAtlas.class),
    GELATIN_KING("sprites/gelatin_king/gelatin_king.atlas", TextureAtlas.class),

    // Player sprites.
    PLAYER_STAND_LEFT("dummy/stand/left.png", Texture.class),
    PLAYER_STAND_RIGHT("dummy/stand/right.png", Texture.class),
    PLAYER_STAND_DOWN("dummy/stand/front.png", Texture.class),
    PLAYER_STAND_UP("dummy/stand/back.png", Texture.class),
    PLAYER_WALK_LEFT("dummy/walk/left.atlas", TextureAtlas.class),
    PLAYER_WALK_RIGHT("dummy/walk/right.atlas", TextureAtlas.class),
    PLAYER_WALK_DOWN("dummy/walk/front.atlas", TextureAtlas.class),
    PLAYER_WALK_UP("dummy/walk/back.atlas", TextureAtlas.class),
    PLAYER_WHIP_LEFT("dummy/whip/left.atlas", TextureAtlas.class),
    PLAYER_WHIP_RIGHT("dummy/whip/right.atlas", TextureAtlas.class),
    PLAYER_WHIP_DOWN("dummy/whip/front.atlas", TextureAtlas.class),
    PLAYER_WHIP_UP("dummy/whip/back.atlas", TextureAtlas.class),
    WHIP_LEFT("sprites/whip/left.atlas", TextureAtlas.class),
    WHIP_RIGHT("sprites/whip/right.atlas", TextureAtlas.class),
    WHIP_DOWN("sprites/whip/front.atlas", TextureAtlas.class),
    WHIP_UP("sprites/whip/back.atlas", TextureAtlas.class),

    // Special Effects.
    IMPACT("effects/impact.atlas", TextureAtlas.class),
    EXCLAMATION("effects/exclamation_mark.atlas", TextureAtlas.class),
    PARTICLES("effects/particles.atlas", TextureAtlas.class),
    REGEN("effects/regen.particle", null),
    STUN("effects/stun.particle", null),
    ARROW_BREAK("effects/arrowbreak.particle", null),
    SHIELD("effects/shield.particle", null),
    SPARKLE("effects/sparkle.particle", null),
    ELECTRICITY("effects/lightning.particle", null),
    SPLASH("effects/splash.particle", null),
    FLAN_PARTS_0("effects/flan_parts.particle", null),
    FLAN_PARTS_1("effects/flan_parts_prime.particle", null),
    TELEPORT_BEAM("effects/teleport0.particle", null),

    // Stage Element Sprites.
    PLATE_BROWN("traps/plate_brown.atlas", TextureAtlas.class),
    TELEPORT_PAD("traps/teleport_pad.atlas", TextureAtlas.class),
    ARROW_TRAP("traps/arrow_trap.atlas", TextureAtlas.class),
    SPIDER_TRAP("traps/spider_trap.atlas", TextureAtlas.class),
    FIRE_TRAP_RIGHT("traps/fire_trap/fire_trap_right.png", Texture.class),
    FIRE_TRAP_LEFT("traps/fire_trap/fire_trap_left.png", Texture.class),
    FIRE_TRAP_DOWN("traps/fire_trap/fire_trap_down.png", Texture.class),
    EYE_GLOW("traps/fire_trap/eye_glow.atlas", TextureAtlas.class),
    SPIKE_TRAP("traps/spike_trap.atlas", TextureAtlas.class),
    GOLDFISH("sprites/goldfish/goldfish_glow.atlas", TextureAtlas.class),
    GOLDFISH_UI("sprites/goldfish/goldfish_ui.atlas", TextureAtlas.class),

    // Projectile sprites and effects.
    PROJECTILE_ARROW("traps/arrow.png", Texture.class),
    PROJECTILE_STAR_SHOT("sprites/starshot/star_shot.atlas", TextureAtlas.class),
    STAR_TRAIL("sprites/starshot/star_effect.particle", null),
    STAR_PARTICLES("sprites/starshot/star_particles.atlas", TextureAtlas.class),
    FLAMETHROWER("effects/fire0.particle", null),
    SMOKE("effects/smoke.particle", null),

    // Tilesets and terrain
    DEFAULT_TILESET("tileset/map.png", Texture.class),
    WALL_LEFT("tileset/wall_left.png", Texture.class),
    WALL_RIGHT("tileset/wall_right.png", Texture.class),
    WALL_BOTTOM("tileset/wall_bottom.png", Texture.class),
    WALL_TOP("tileset/wall_top.png", Texture.class),
    END_CREDITS("UI/Victory.png", Texture.class),

    // TMX for levels
    LEVEL01_TMX("tmx/level_01.tmx", TiledMap.class),
    LEVEL02_TMX("tmx/level_02.tmx", TiledMap.class),
    LEVEL03_TMX("tmx/level_03.tmx", TiledMap.class),
    LEVEL04_TMX("tmx/level_04.tmx", TiledMap.class),
    LEVEL05_TMX("tmx/level_05.tmx", TiledMap.class),
    LEVEL06_TMX("tmx/level_06.tmx", TiledMap.class),
    LEVEL07_TMX("tmx/level_07.tmx", TiledMap.class),
    LEVEL08_TMX("tmx/level_08.tmx", TiledMap.class),
    LEVEL09_TMX("tmx/level_09.tmx", TiledMap.class);

    private String pathname;
    private Class type;
    AssetList(String s, Class c) {
        this.pathname = s;
        this.type = c;
    }

    @Override
    public String toString() {
        return this.pathname;
    }

    public Class getType() { return type; }
}
