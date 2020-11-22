package com.cronan.gameoflife.game;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

/**
 * @author Cody Cronberger
 * @version 1.0
 *
 * {@code Universe} represents a world that has {@code Cell} that are either alive or dead
 * There are generations of {@code Cell} and they live and die based on 4 rules, a count is kept of
 * how many {@code Cell} are alive. {@see life} which creates an initial {@see currentUniverse} and then
 * continues evolving and getting the next generations of {@code Cell} and updating the {@see currentUniverse}
 */
public class Universe {
    //class level fields
    private static int generation;
    private static int cellsAlive = 0;

    // fields
    private char[][] currentUniverse;
    private List<Cell> cells;
    private Random rand;

    //ctor
    public Universe(int dimensions) {
        setCurrentUniverse(new char[dimensions][dimensions]); //sets new empty universe
        setCells(dimensions); //sets cells to have all cells in universe
        setGeneration(1); //sets generation to 1
    }

    //business methods
    /**
     * Initializes the {@code Universe} utilizing {@see cells}
     */
    public void initializeUniverse() {
        //place cells into the current universe
        int cellIndx = 0;
        for (int i = 0; i < currentUniverse.length; i++) {
            for (int j = 0; j < currentUniverse[i].length; j++) {
                Cell cell = cells.get(cellIndx);
                currentUniverse[i][j] = cell.getAliveOrDeadSym();
                cell.setLocation(new Direction(i, j)); //set x,y in cell to location in universe
                cellIndx++;
            }
        }
        displayCurrentUniverse();
    }

    /**
     * Method sets the {@see currentUniverse} to hold the next generation of
     * {@code Cell} using the {@see cells} list
     */
    public void nextGenerationUniverse() {
        //use cells list to set cell positions in currentUniverse
        generation++;
        for (Cell cell : cells) {
            int x = cell.getLocation().x;
            int y = cell.getLocation().y;
            currentUniverse[x][y] = cell.getAliveOrDeadSym();
        }
        displayCurrentUniverse();
    }

    public void life() {
        initializeUniverse();
        while (generation < 20) {
            nextGeneration();
        }
    }

    //TODO: display currentUniverse, delay, then display the new Universe
    public void displayCurrentUniverse() {
        System.out.println("Generation: " + getGeneration() + ", Alive: " + cellsAlive);
        for (int i = 0; i < currentUniverse.length; i++) {
            for (int j = 0; j < currentUniverse[i].length; j++) {
                System.out.print(currentUniverse[i][j]);
            }
            System.out.println();
        }
    }

    /**
     * Method checks each {@code Cell} and determines weather it will be alive or
     * dead in the next generation of the {@code Universe}, utilizes
     * {@see aliveNeighborsCount} to determine fate of the {@code Cell}
     */
    public void nextGeneration() {
        //determine which cells are alive or dead for next generation
        //Go through cells and check how many of each cells neighbors are alive
        for (Cell cell : cells) {
            int aliveNeighbors = aliveNeighborsCount(cell);
            //set cells to alive or dead based on neighbor alive count
            if (cell.isAlive()) {
                if (aliveNeighbors < 2 || aliveNeighbors > 3) {
                    cell.setAlive(false);
                    cellsAlive--;
                }
            }
            else {
                if (aliveNeighbors == 3) {
                    cell.setAlive(true);
                    cellsAlive++;
                }
            }
        }
        nextGenerationUniverse();
    }

    //getters and setters
    public static int getGeneration() {
        return generation;
    }

    public static void setGeneration(int generation) {
        Universe.generation = generation;
    }

    public static int getCellsAlive() {
        return cellsAlive;
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
     * @param cell - {@code Cell} representing the cell whose neighbors are to be checked for life
     * @return int - representing the total number of neighboring cells that are alive
     */
    public int aliveNeighborsCount(Cell cell) {
        int alive = 0;
        int row = cell.getLocation().x; //getting cells x,y location in universe
        int col = cell.getLocation().y;

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
