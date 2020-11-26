package com.cronan.gameoflife.game;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

public class GOLPanel extends JPanel implements ActionListener, MouseListener, MouseMotionListener {

    private static final int SCREEN_SIZE = 600;
    private static final int CELL_SIZE = 6;
    private static final int DELAY = 200;
    private boolean running = true;
    private final Timer timer;

    private final Universe universe = new Universe(SCREEN_SIZE/CELL_SIZE);
    private final char[][] grid = universe.getCurrentUniverse();

    public GOLPanel() {

        setPreferredSize(new Dimension(SCREEN_SIZE, SCREEN_SIZE));
        setBackground(Color.BLACK);
        setFocusable(true);
        addMouseListener(this);
        addMouseMotionListener(this);
        addKeyListener(new LifekeyAdapter());

        timer = new Timer(DELAY, this);
        timer.start();
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);

//        drawGrid(g);
        displayCells(g);
    }

    public void drawGrid(Graphics g) {
        for (int i = 0; i < grid.length; i++) {
            g.drawLine(i * CELL_SIZE, 0, i * CELL_SIZE, SCREEN_SIZE); //row
            g.drawLine(0, i * CELL_SIZE, SCREEN_SIZE, i * CELL_SIZE);  //col
        }
    }

    private void displayCells(Graphics g) {
        g.setColor(Color.orange);


        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid[i].length; j++) {
                if (grid[i][j] == 'O') {
                    g.fillRect(i * CELL_SIZE, j * CELL_SIZE, CELL_SIZE, CELL_SIZE);
                }
            }
        }
    }

    public void pause() {
        running = false;
        timer.stop();
    }

    public void resume() {
        running = true;
        timer.start();
    }

    private void emptyUniverse() {
        universe.getCells().forEach(cell -> cell.setAlive(false));
        universe.getCells().forEach(cell -> {
            grid[cell.getLocation().x][cell.getLocation().y] = cell.getAliveOrDeadSym();
        });
        repaint();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (running) {
            universe.nextGeneration();
        }
        repaint();
    }

    /*
     * Allow user to click and drag to make cells alive and draw across the screen
     * when clicked the game will pause while user draws, once mouse is released the generation
     * will continue
     */
    //********MouseListener methods**********//
    @Override
    public void mouseClicked(MouseEvent e) {

    }

    @Override
    public void mousePressed(MouseEvent e) {
        pause();
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        resume();
    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }

    //********MouseMotionListener methods**********//
    @Override
    public void mouseDragged(MouseEvent e) {
        int xCoord = e.getX() / CELL_SIZE;
        int yCoord = e.getY() / CELL_SIZE;

        if (grid[xCoord][yCoord] == Character.MIN_VALUE) {
            grid[xCoord][yCoord] = 'O';
        }

        repaint();
    }

    @Override
    public void mouseMoved(MouseEvent e) {

    }

    /**
     * nested class to check for key press events
     * Done: space = pause/unpause
     * Done: 'c' = pause/clear screen to allow user to start from blank slate
     * TODO: 'r' = reset game
     */
    class LifekeyAdapter extends KeyAdapter {

        @Override
        public void keyPressed(KeyEvent e) {
            switch(e.getKeyCode()) {
                case KeyEvent.VK_SPACE:
                    if (running) {
                        pause();
                    }
                    else {
                        resume();
                    }
                break;
                case KeyEvent.VK_C:
                    emptyUniverse();
                    pause();
                break;
            }
        }
    }
}
