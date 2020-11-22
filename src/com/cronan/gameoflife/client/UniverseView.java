package com.cronan.gameoflife.client;

import com.cronan.gameoflife.game.Universe;

public class UniverseView {

    public static void main(String[] args) {
        Universe universe = new Universe(10);
        universe.initializeUniverse();
    }
}
