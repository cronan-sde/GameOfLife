package com.cronan.gameoflife.client;

import com.cronan.gameoflife.game.Universe;

public class UniverseClient {

    public static void main(String[] args) {
        Universe universe = new Universe(10);
        universe.life();
    }
}
