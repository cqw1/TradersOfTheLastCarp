package com.totlc;

public enum AssetList {
    SPIDER_IDLE("sprites/spider/spider_idle.atlas"),
    SPIDER_WALK("sprites/spider/spider_walk.atlas"),
    STARGAZER_BODY("sprites/stargazer/stargazer_body.atlas"),
    STARGAZER_EYE("sprites/stargazer/stargazer_eye.atlas"),
    STARGAZER_SPIN("sprites/stargazer/stargazer_spin.atlas"),
    STARGAZER_GAZE("sprites/stargazer/stargazer_gaze.atlas"),
    SHADOW("sprites/float_shadow.png"),
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
    IMPACT("effects/impact.atlas"),
    PLATE_BROWN("traps/plate_brown.atlas"),
    ARROW_TRAP("traps/arrow_trap.atlas"),
    PROJECTILE_ARROW("traps/arrow.png"),
    PROJECTILE_STAR_SHOT("sprites/starshot/star_shot.atlas"),
    STAR_TRAIL("sprites/starshot/star_effect.particle"),
    STAR_PARTICLES("sprites/starshot/star_particles.atlas");


    private String pathname;
    AssetList(String s) {
        this.pathname = s;
    }

    @Override
    public String toString() {
        return this.pathname;
    }
}
