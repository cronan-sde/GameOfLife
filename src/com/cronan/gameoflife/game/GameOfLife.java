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
        cellsAliveLabel = new JLabel();
        cellsAliveLabel.setName("cellsAlive");
        updateLabels();

        //Buttons

        //add labels to top panel
        topPanel.add(generationLabel);
        topPanel.add(cellsAliveLabel);
        topPanel.setLayout(new BoxLayout(topPanel, BoxLayout.Y_AXIS)); //stacking labels vertically

        getContentPane().add(BorderLayout.WEST, topPanel); // adding topPanel to top of frame
        setVisible(true);

        evolution();
    }

    public void displayCurrentUniverse() {
        int universeLength = universe.getCurrentUniverse().length;
        char[][] universeGrid = universe.getCurrentUniverse();
        System.out.println(universeLength);
        JPanel cellPanel = new JPanel();
        cellPanel.setLayout(new GridLayout(universeLength, universeLength));

        for (char[] cell : universeGrid) {
            for (int j = 0; j < universeLength; j++) {
                JButton cellBtn = new JButton();
                cellBtn.setSize(1,1);
                cellBtn.setEnabled(false);
                if (cell[j] == 'O') {
                    cellBtn.setBackground(Color.WHITE);
                }
                else {
                    cellBtn.setBackground(Color.BLACK);
                    cellBtn.setBorder(BorderFactory.createEmptyBorder());
                }
                cellPanel.add(cellBtn);
            }
        }
        updateLabels();
        getContentPane().add(BorderLayout.CENTER, cellPanel); //putting grid in center of pane
        setVisible(true);
    }

    public void updateLabels() {
        generationLabel.setText("Generation # " + Universe.getGeneration());
        cellsAliveLabel.setText("Alive: " + Universe.getCellsAlive());
    }

    public void evolution() {
        displayCurrentUniverse();
        while (true) {
            universe.nextGeneration();
            slowDisplay();
            displayCurrentUniverse();
        }
    }

    public void slowDisplay() {
        try {
            Thread.sleep(500);
        }
        catch (InterruptedException ignored) {

        }
    }
}
