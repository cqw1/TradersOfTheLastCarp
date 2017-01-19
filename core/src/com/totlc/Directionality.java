package com.totlc;

public enum Directionality {
    LEFT(0),
    UP(1),
    RIGHT(2),
    DOWN(3);

    private int direction;

    Directionality(int direction) {
        this.direction = direction;
    }

    public String toString() {
        switch (direction) {
            case 0:
                return "LEFT";
            case 1:
                return "UP";
            case 2:
                return "RIGHT";
            case 3:
                return "DOWN";
            default:
                return "";
        }
    }

    public boolean isFacingLeft() {
        return direction == 0;
    }

    public boolean isFacingUp() {
        return direction == 1;
    }

    public boolean isFacingRight() {
        return direction == 2;
    }

    public boolean isFacingDown() {
        return direction == 3;
    }
}
