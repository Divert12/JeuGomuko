package model;

public enum Direction {
    N, NE, E, SE, S, SO, O, NO;

    public Direction oppose() {
        return values()[(ordinal() + 4) % 8];
    }
}