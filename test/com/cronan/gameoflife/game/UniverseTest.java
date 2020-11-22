package com.cronan.gameoflife.game;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class UniverseTest {
    private Universe universe;

    @Before
    public void init() {
        universe = new Universe(5);
    }

    @Test
    public void initializeCells() {
        assertEquals(25, universe.getCells().size());
        assertEquals(5, universe.getCurrentUniverse().length);
    }

    @Test
    public void testAliveNeighborsCount_shouldReturn8_whenAllNeighborsAreAlive() {
        Cell testCell = new Cell(true);
        testCell.setLocation(new Universe.Direction(0,0));
        universe.getCurrentUniverse()[4][0] = 'O'; // North neighbor of 0,0
        universe.getCurrentUniverse()[1][0] = 'O'; // South neighbor of 0,0
        universe.getCurrentUniverse()[0][1] = 'O'; // East neighbor of 0,0
        universe.getCurrentUniverse()[0][4] = 'O'; // West neighbor of 0,0
        universe.getCurrentUniverse()[1][1] = 'O'; // SE neighbor of 0,0
        universe.getCurrentUniverse()[1][4] = 'O'; // SW neighbor of 0,0
        universe.getCurrentUniverse()[4][1] = 'O'; // NE neighbor of 0,0
        universe.getCurrentUniverse()[4][4] = 'O'; // NW neighbor of 0,0

        assertEquals(8, universe.aliveNeighborsCount(testCell));
    }

    @Test
    public void testAliveNeighborsCount_shouldReturnZero_whenNoNeighborsAreAlive() {
        Cell testCell = new Cell(true);
        testCell.setLocation(new Universe.Direction(0,0));
        universe.getCurrentUniverse()[4][0] = Character.MIN_VALUE; // North neighbor of 0,0
        universe.getCurrentUniverse()[1][0] = Character.MIN_VALUE; // South neighbor of 0,0
        universe.getCurrentUniverse()[0][1] = Character.MIN_VALUE; // East neighbor of 0,0
        universe.getCurrentUniverse()[0][4] = Character.MIN_VALUE; // West neighbor of 0,0
        universe.getCurrentUniverse()[1][1] = Character.MIN_VALUE; // SE neighbor of 0,0
        universe.getCurrentUniverse()[1][4] = Character.MIN_VALUE;// SW neighbor of 0,0
        universe.getCurrentUniverse()[4][1] = Character.MIN_VALUE; // NE neighbor of 0,0
        universe.getCurrentUniverse()[4][4] = Character.MIN_VALUE; // NW neighbor of 0,0

        assertEquals(0, universe.aliveNeighborsCount(testCell));
    }
}