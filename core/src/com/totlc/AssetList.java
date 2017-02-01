package com.totlc;

public enum AssetList {
    // UI elements and icons.
    UI_BAR("UI/UIBar.png"),
    LIFE_GAUGE("UI/LifeGauge.png"),
    LIFE_BAR("UI/LifeGaugeBar.png"),
    INVENTORY_BOX("UI/Satchel.png"),
    ICON_PACK("UI/icon_pak.atlas"),
    LOVELO_FONT("UI/Lovelo.fnt"),
    LOVELO_IMAGE("UI/Lovelo.png"),
    ITEM_PACK("sprites/items/items.atlas"),

    // Enemy Sprites.
    SPIDER_IDLE("sprites/spider/spider_idle.atlas"),
    SPIDER_WALK("sprites/spider/spider_walk.atlas"),
    STARGAZER_BODY("sprites/stargazer/stargazer_body.atlas"),
    STARGAZER_EYE("sprites/stargazer/stargazer_eye.atlas"),
    STARGAZER_SPIN("sprites/stargazer/stargazer_spin.atlas"),
    STARGAZER_GAZE("sprites/stargazer/stargazer_gaze.atlas"),
    SHADOW("sprites/float_shadow.png"),
    FLAN("sprites/flan/badflan.png"),
    PANGOLINI("sprites/pangolini/pangolini.png"),

    // Player sprites.
    PLAYER_STAND_LEFT("dummy/stand/left.png"),
    PLAYER_STAND_RIGHT("dummy/stand/right.png"),
    PLAYER_STAND_DOWN("dummy/stand/front.png"),
    PLAYER_STAND_UP("dummy/stand/back.png"),
    PLAYER_WALK_LEFT("dummy/walk/left.atlas"),
    PLAYER_WALK_RIGHT("dummy/walk/right.atlas"),
    PLAYER_WALK_DOWN("dummy/walk/front.atlas"),
    PLAYER_WALK_UP("dummy/walk/back.atlas"),
    PLAYER_WHIP_LEFT("dummy/whip/left.atlas"),
    PLAYER_WHIP_RIGHT("dummy/whip/right.atlas"),
    PLAYER_WHIP_DOWN("dummy/whip/front.atlas"),
    PLAYER_WHIP_UP("dummy/whip/back.atlas"),
    WHIP_LEFT("sprites/whip/left.atlas"),
    WHIP_RIGHT("sprites/whip/right.atlas"),
    WHIP_DOWN("sprites/whip/front.atlas"),
    WHIP_UP("sprites/whip/back.atlas"),

    // Special Effects.
    IMPACT("effects/impact.atlas"),
    PARTICLES("effects/particles.atlas"),
    REGEN("effects/regen.particle"),
    STUN("effects/stun.particle"),
    ARROW_BREAK("effects/arrowbreak.particle"),

    // Stage Element Sprites.
    PLATE_BROWN("traps/plate_brown.atlas"),
    ARROW_TRAP("traps/arrow_trap.atlas"),
    SPIDER_TRAP("traps/spider_trap.atlas"),

    // Projectile sprites and effects.
    PROJECTILE_ARROW("traps/arrow.png"),
    PROJECTILE_STAR_SHOT("sprites/starshot/star_shot.atlas"),
    STAR_TRAIL("sprites/starshot/star_effect.particle"),
    STAR_PARTICLES("sprites/starshot/star_particles.atlas"),

    // Tilesets and terrain
    DEFAULT_TILESET("tileset/map.png"),
    WALL_LEFT("tileset/wall_left.png"),
    WALL_RIGHT("tileset/wall_right.png"),
    WALL_BOTTOM("tileset/wall_bottom.png"),
    WALL_TOP("tileset/wall_top.png");

    private String pathname;
    AssetList(String s) {
        this.pathname = s;
    }

    @Override
    public String toString() {
        return this.pathname;
    }
}
