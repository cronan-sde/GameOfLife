package com.cronan.gameoflife.game;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Universe {
    //class level fields
    private static int generation;
    private static int cellsAlive = 0;

    // fields
    private char[][] currentUniverse;
    private char[][] futureUniverse;
    private List<Cell> cells;
    private Random rand;

    //ctor
    public Universe(int dimensions) {
        setCurrentUniverse(new char[dimensions][dimensions]);
        setCells(dimensions);
        setGeneration(1);
    }

    //business methods
    public void initializeCurrentUniverse() {
        //place cells into the current universe
        int cellIndx = 0;
        for (int i = 0; i < currentUniverse.length; i++) {
            for (int j = 0; j < currentUniverse[i].length; j++) {
                currentUniverse[i][j] = cells.get(cellIndx).getAliveOrDeadSym();
                cellIndx++;
            }
        }
        displayCurrentUniverse();
    }

    public void displayCurrentUniverse() {
        System.out.println(getGeneration() + " " + cellsAlive);
        for (int i = 0; i < currentUniverse.length; i++) {
            for (int j = 0; j < currentUniverse[i].length; j++) {
                System.out.print(currentUniverse[i][j]);
            }
            System.out.println();
        }
    }

    //TODO: set futureUniverse by checking cells and neigboring cells
    public void futureUniverse() {

    }

    //getters and setters
    public static int getGeneration() {
        return generation;
    }

    public static void setGeneration(int generation) {
        Universe.generation = generation;
    }

    public char[][] getCurrentUniverse() {
        return currentUniverse;
    }

    public void setCurrentUniverse(char[][] currentUniverse) {
        this.currentUniverse = currentUniverse;
    }

    public List<Cell> getCells() {
        return cells;
    }

    private void setCells(int dimensions) {
        int size = dimensions * dimensions;
        this.cells = new ArrayList<>(size);

        for (int i = 0; i < size; i++) {
            cells.add(generateCell());
        }
    }

    private Cell generateCell() {
        rand = new Random();
        Cell cell;

        if (rand.nextBoolean()) {
            cell = new Cell(true);
            cellsAlive++;
        }
        else {
            cell = new Cell(false);
        }
        return cell;
    }
}
