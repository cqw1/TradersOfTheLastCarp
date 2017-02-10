package com.totlc.Actors.damage;

public enum ProjEnum {

    ARROW(0),
    STAR_SHOT(1);

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
