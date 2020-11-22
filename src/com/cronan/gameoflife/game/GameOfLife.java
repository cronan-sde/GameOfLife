package com.cronan.gameoflife.game;

import javax.swing.*;
import java.awt.*;

public class GameOfLife extends JFrame {
    //Universe
    private Universe universe;
    //Labels
    private JLabel generationLabel;
    private JLabel cellsAliveLabel;
    //Buttons
    private JToggleButton pauseToggle;
    private JButton reset;

    public GameOfLife(int universeSize) {
        universe = new Universe(universeSize);
        setTitle("Game of Life");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(500, 500);
        setLocationRelativeTo(null);
        setVisible(true);
        start();
    }

    public void start() {
        //panel for generationLabel and cellsAliveLabel
        JPanel topPanel = new JPanel();

        //JLabels
        generationLabel = new JLabel();
        generationLabel.setName("genLabel");
        generationLabel.setText("Generation #:" + Universe.getGeneration());
        cellsAliveLabel = new JLabel();
        cellsAliveLabel.setName("cellsAlive");
        cellsAliveLabel.setText("Alive:" + Universe.getCellsAlive());

        //add labels to top panel
        topPanel.add(generationLabel);
        topPanel.add(cellsAliveLabel);
        topPanel.setLayout(new BoxLayout(topPanel, BoxLayout.Y_AXIS)); //stacking labels vertically

        getContentPane().add(BorderLayout.NORTH, topPanel); // adding topPanel to top of frame
        setVisible(true);
    }
}
