package com.totlc.Actors.projectiles;

public enum ProjEnum {

    ARROW(0);

    int type;
    ProjEnum(int id) {
        type = id;
    }

    public String toString() {
        switch (type) {
            case 0:
                return "ARROW";
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
