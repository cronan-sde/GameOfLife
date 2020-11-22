package com.cronan.gameoflife.game;

import java.util.ArrayList;
import java.util.Arrays;
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

    /**
     * Method uses {@see generateCell} to create a list of {@code Cell} that
     * will be used to populate the {@code Universe}
     * @param dimensions - represents the length of the universe
     */
    private void setCells(int dimensions) {
        int size = dimensions * dimensions;
        this.cells = new ArrayList<>(size);

        for (int i = 0; i < size; i++) {
            cells.add(generateCell());
        }
    }

    /**
     * Method used to help set the initial cells in the {@code Universe}
     * used by {@see setCells}, uses {@see Random} to get a random boolean
     * then creates a new {@code Cell} based on that boolean, each time an
     * alive {@code Cell} is created {@see cellsAlive} is incremented
     * @return {@code Cell} - that is either alive or dead
     */
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

    /**
     * Static nested class {@code Direction} used by {@code Universe} to help
     * find all neighbors of a {@code Cell}
     */
    static class Direction {
        int x;
        int y;

        public Direction(int x, int y) {
            this.x = x;
            this.y = y;
        }
    }

    /*
     * Creating all directions for neighboring cells N, S, E, W, SE, SW, NE, NW
     * Each cell has 8 neighboring cells
     */
    Direction north = new Direction(-1, 0);
    Direction south = new Direction(1, 0);
    Direction east = new Direction(0, 1);
    Direction west = new Direction(0, -1);
    Direction southEast = new Direction(1, 1);
    Direction southWest = new Direction(1, -1);
    Direction northEast = new Direction(-1, 1);
    Direction northWest = new Direction(-1, -1);

    /*
     * hold each direction in a list for quick access to find alive neighbors
     */
    List<Direction> directions = Arrays.asList(north, south, east, west, southEast, southWest, northEast, northWest);

    /**
     * Method helps determine how many neighboring {@code Cell} of a specified {@code Cell} are alive
     * @param row - represents the row coordinate value of the cell whose neighboring cells are to be checked
     * @param col - represents the column coordinate value of cell whose neighboring cells are to be checked
     * @return int - representing the total number of neighboring cells that are alive
     */
    public int aliveNeighborsCount(int row, int col) {
        int alive = 0;

        for (Direction direction : directions) {
            int r = (direction.x + row + currentUniverse.length) % currentUniverse.length; //formula to wrap around the grid
            int c = (direction.y + col + currentUniverse.length) % currentUniverse.length; //formula to wrap around the grid

            if (currentUniverse[r][c] == 'O') {
                alive++;
            }
        }

        return alive;
    }
}
