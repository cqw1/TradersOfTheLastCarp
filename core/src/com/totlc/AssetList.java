package com.totlc;

public enum AssetList {
    SPIDER_IDLE("sprites/spider/spider_idle.atlas"),
    SPIDER_WALK("sprites/spider/spider_walk.atlas"),
    STARGAZER_BODY("sprites/stargazer/stargazer_body.atlas"),
    STARGAZER_EYE("sprites/stargazer/stargazer_eye.atlas"),
    STARGAZER_SPIN("sprites/stargazer/stargazer_spin.atlas"),
    STARGAZER_GAZE("sprites/stargazer/stargazer_gaze.atlas"),
    PLAYER_STAND_LEFT("dummy/stand/left.png"),
    PLAYER_STAND_RIGHT("dummy/stand/right.png"),
    PLAYER_STAND_DOWN("dummy/stand/front.png"),
    PLAYER_STAND_UP("dummy/stand/back.png"),
    PLAYER_WALK_LEFT("dummy/walk/left.atlas"),
    PLAYER_WALK_RIGHT("dummy/walk/right.atlas"),
    PLAYER_WALK_DOWN("dummy/walk/front.atlas"),
    PLAYER_WALK_UP("dummy/walk/back.atlas"),
    ARROW_TRAP("traps/arrowTotem0.png"),
    PROJECTILE_ARROW("traps/arrow.png");


    private String pathname;
    AssetList(String s) {
        this.pathname = s;
    }

    @Override
    public String toString() {
        return this.pathname;
    }
}
