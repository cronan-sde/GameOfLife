package com.cronan.gameoflife.game;

import javax.swing.*;

public class GOLFrame extends JFrame {

    public GOLFrame() {
        // adding panel
        add(new GOLPanel());
        //set title of frame
        setTitle("Game of Life");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);
        pack(); //ensure frame fits snugly around any added components
        setVisible(true);
        setLocationRelativeTo(null); //sets the frame to center of screen
    }
}
