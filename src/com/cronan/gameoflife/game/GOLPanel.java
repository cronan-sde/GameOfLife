package com.cronan.gameoflife.game;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class GOLPanel extends JPanel implements ActionListener {

    private static final int SCREEN_HEIGHT = 600;
    private static final int SCREEN_WIDTH = 600;
    private static final int CELL_SIZE = 8;
    private static final int DELAY = 100;
    private boolean running = true;
    private Timer timer;

    private Universe universe = new Universe(SCREEN_HEIGHT/CELL_SIZE);
    private char[][] grid = universe.getCurrentUniverse();

    public GOLPanel() {

        setPreferredSize(new Dimension(SCREEN_WIDTH, SCREEN_HEIGHT));
        setBackground(Color.BLACK);
        setFocusable(true);

        timer = new Timer(DELAY, this);
        timer.start();
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        drawUniverse(g);
        displayCells(g);
    }

    public void drawUniverse(Graphics g) {
        for (int i = 0; i < grid.length; i++) {
            g.drawLine(i * CELL_SIZE, 0, i * CELL_SIZE, SCREEN_HEIGHT); //row
            g.drawLine(0, i * CELL_SIZE, SCREEN_WIDTH, i * CELL_SIZE);  //col
        }
    }

    private void displayCells(Graphics g) {
        g.setColor(Color.CYAN);


        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid[i].length; j++) {
                if (grid[i][j] == 'O') {
                    g.fillRect(i * CELL_SIZE, j * CELL_SIZE, CELL_SIZE, CELL_SIZE);
                }
            }
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        universe.nextGeneration();
        repaint();
    }
}
