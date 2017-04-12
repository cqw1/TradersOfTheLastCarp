package com.totlc.Actors.damage;

public enum DamageEnum {

    ARROW(0),
    STAR_SHOT(1),
    LIGHTNING_PATCH(2),
    LASER(3),
    BULLET(4);

    int type;
    DamageEnum(int id) {
        type = id;
    }

    public String toString() {
        switch (type) {
            case 0:
                return "ARROW";
            case 1:
                return "STAR_SHOT";
            case 2:
                return "LIGHTNING_PATCH";
            case 3:
                return "LASER";
            case 4:
                return "BULLET";
        }

        return "";
    }

    public int getType() {
        return type;
    }

    public boolean equals(DamageEnum other) {
        return type == other.getType();
    }
}
