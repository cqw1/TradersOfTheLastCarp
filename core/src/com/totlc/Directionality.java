package com.totlc;

public enum Directionality {
    LEFT("LEFT"),
    UP("UP"),
    RIGHT("RIGHT"),
    DOWN("DOWN");

    private String direction;

    Directionality(String direction) {
        this.direction = direction;
    }

    public String toString() {
        return direction;
    }

    public boolean isFacingLeft() {
        return direction.equals("LEFT");
    }

    public boolean isFacingUp() {
        return direction.equals("UP");
    }

    public boolean isFacingRight() {
        return direction.equals("RIGHT");
    }

    public boolean isFacingDown() {
        return direction.equals("DOWN");
    }
}
