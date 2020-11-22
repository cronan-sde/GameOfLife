package com.cronan.gameoflife.game;

/**
 * @author Cody Cronberger
 * @version 1.0
 *
 * A {@code Cell} represents a living or dead organism to be utilized inside of a {@code Universe}
 * {@code Cell} know weather they are alive or dead, and they know their location in the {@code Universe}
 * they are in utilizing {@code Universe.Direction}
 */
class Cell {
    private boolean isAlive; //tells weather a cell is alive or dead
    private char aliveOrDeadSym;
    private Universe.Direction location; //test

    public Cell(boolean isAlive) {
        setAlive(isAlive);
    }

    public boolean isAlive() {
        return isAlive;
    }

    public void setAlive(boolean alive) {
        aliveOrDeadSym = alive ? 'O' : Character.MIN_VALUE;
        isAlive = alive;
    }

    public char getAliveOrDeadSym() {
        return aliveOrDeadSym;
    }


    public Universe.Direction getLocation() {
        return location;
    }

    public void setLocation(Universe.Direction location) {
        this.location = location;
    }
}
