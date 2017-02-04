package com.totlc.Actors.projectiles;

public enum ProjEnum {

    ARROW(0),
    STAR_SHOT(1),
    FIRE_STREAM(2);

    int type;
    ProjEnum(int id) {
        type = id;
    }

    public String toString() {
        switch (type) {
            case 0:
                return "ARROW";
            case 1:
                return "STAR_SHOT";
            case 2:
                return "FIRE_STREAM";
        }

        return "";
    }

    public int getType() {
        return type;
    }

    public boolean equals(ProjEnum other) {
        return type == other.getType();
    }
}
