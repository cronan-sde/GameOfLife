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
    public void testAliveNeighborsCount() {
        
    }
}