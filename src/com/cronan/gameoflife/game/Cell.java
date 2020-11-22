package com.cronan.gameoflife.game;

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
