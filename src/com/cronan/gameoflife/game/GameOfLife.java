package com.cronan.gameoflife.game;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ItemEvent;

public class GameOfLife extends JFrame  {
    //Universe
    private Universe universe;
    //Universe Size
    private static final int UNIVERSE_SIZE = 50;
    //Labels
    private JLabel generationLabel;
    private JLabel cellsAliveLabel;
    //Buttons
    private JToggleButton playToggle;
    private JButton reset;

    //button Icons - TODO: add icons later
//    ImageIcon pauseIcon = new ImageIcon();
//    ImageIcon playIcon = new ImageIcon();
//    ImageIcon reloadIcon = new ImageIcon();

    //lock object monitors the thread to allow for pause/resume and reset
    private final Object lock = new Object();
    //controls pausing/resuming of thread
    private volatile boolean isPaused = false;


    public GameOfLife() {
        universe = new Universe(UNIVERSE_SIZE);
        setTitle("Game of Life");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(600, 600);
        setLocationRelativeTo(null);
        setVisible(true);
    }


    public void start() {
        //panel for buttons on left of frame
        JPanel leftBtnPan = new JPanel();
        //panel for labels on left
        JPanel leftPan = new JPanel();
        //main panel to hold buttons and labels
        JPanel mainPan = new JPanel();

        //pause/resume toggle button and reset button
        playToggle = new JToggleButton("Pause");
        playToggle.setName("PlayToggleButton");
        reset = new JButton("Reset");
        reset.setName("ResetButton");


        playToggle.addItemListener(this::setPlayToggle); //allows for pause/resume operation
        reset.addActionListener(this::reset); //resets the universe to new random universe


        //add buttons to panel
        leftBtnPan.add(playToggle);
        leftBtnPan.add(reset);
        leftBtnPan.setLayout(new BoxLayout(leftBtnPan, BoxLayout.X_AXIS)); //sets buttons next to each other
        leftBtnPan.setAlignmentX(Component.CENTER_ALIGNMENT);

        //JLabels generation and alive labels
        generationLabel = new JLabel();
        generationLabel.setName("GenerationLabel");
        cellsAliveLabel = new JLabel();
        cellsAliveLabel.setName("AliveLabel");
        //update label text
        updateLabels();


        //adding labels to left panel
        leftPan.add(generationLabel);
        leftPan.add(cellsAliveLabel);
        leftPan.setLayout(new BoxLayout(leftPan, BoxLayout.Y_AXIS)); //setting box layout to stack labels vertical
        leftPan.setAlignmentX(Component.CENTER_ALIGNMENT);

        //Adding button panel and label panel to main panel
        mainPan.add(leftBtnPan);
        mainPan.add(leftPan);

        mainPan.setLayout(new BoxLayout(mainPan, BoxLayout.Y_AXIS)); //Stacking panels vertically
        mainPan.setBorder(BorderFactory.createEmptyBorder(20,5,5,5)); //setting spacing


        getContentPane().add(BorderLayout.WEST, mainPan); //adding main panel to the left side of frame
        setVisible(true); //setting frame to be visible

//        evolution();
    }

//    public void displayCurrentUniverse() {
//        char[][] universeGrid = universe.getCurrentUniverse();
//        JPanel cellPanel = new JPanel();
//        cellPanel.setLayout(new GridLayout(UNIVERSE_SIZE, UNIVERSE_SIZE));
//
//        for (char[] cell : universeGrid) {
//            for (int j = 0; j < UNIVERSE_SIZE; j++) {
//                JButton cellBtn = new JButton();
//                cellBtn.setSize(1,1);
//                cellBtn.setEnabled(false);
//                if (cell[j] == 'O') {
//                    cellBtn.setBackground(Color.WHITE);
//                }
//                else {
//                    cellBtn.setBackground(Color.BLACK);
//                    cellBtn.setBorder(BorderFactory.createEmptyBorder());
//                }
//                cellPanel.add(cellBtn);
//            }
//        }
//        updateLabels();
//        getContentPane().add(BorderLayout.CENTER, cellPanel); //putting grid in center of pane
//        setVisible(true);
//    }



    public void updateLabels() {
        generationLabel.setText("Generation # " + Universe.getGeneration());
        cellsAliveLabel.setText("Alive: " + Universe.getCellsAlive());
    }
//
//    public void evolution() {
//        displayCurrentUniverse();
//        while (true) {
//            allowPause();
//            universe.nextGeneration();
//            slowDisplay();
//            displayCurrentUniverse();
//        }
//    }

    //allow thread to be paused using lock object
    public void allowPause() {
        synchronized (lock) {
            while (isPaused) {
                try {
                    lock.wait();
                }
                catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public void slowDisplay() {
        try {
            Thread.sleep(600);
        }
        catch (InterruptedException ignored) {

        }
    }

    public void setPlayToggle(ItemEvent e) {
        if (playToggle.isSelected()) {
            playToggle.setText("Play");
            isPaused = true;
        }
        else {
            playToggle.setText("Pause");
            isPaused = false;
        }
        synchronized (lock) {
            lock.notify();
        }
    }

    public void reset(ActionEvent e) {
        universe = new Universe(UNIVERSE_SIZE);
        isPaused = false;
        playToggle.setSelected(false);
        synchronized (lock) {
            lock.notify();
        }
    }
}
