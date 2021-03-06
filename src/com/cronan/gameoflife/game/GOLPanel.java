package com.cronan.gameoflife.game;

import com.cronan.gameoflife.util.PatternCSVReadWrite;

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

    private boolean isRunning = true;
    private boolean isUserDrawing = false;
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

    /*
     * Used to draw grid lines, probably will be taken out in final version
     */
    public void drawGrid(Graphics g) {
        for (int i = 0; i < grid.length; i++) {
            g.drawLine(i * CELL_SIZE, 0, i * CELL_SIZE, SCREEN_SIZE); //row
            g.drawLine(0, i * CELL_SIZE, SCREEN_SIZE, i * CELL_SIZE);  //col
        }
    }

    /*
     * Displays the cells that are alive as orange and sets them to fill up
     * the grid by utilizing the CELL_SIZE value. ensures cell takes up the entire space
     * it is given
     */
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


    //pause game
    public void pause() {
        isRunning = false;
        timer.stop();
    }

    //If the user is not drawing on emptyUniverse after pressing 'c' key
    //set game to run, otherwise wait on user to inform game to start by
    //pressing the 'R' key
    public void resume() {
        if (!isUserDrawing) {
            isRunning = true;
            timer.start();
        }
    }

    /*
     * Used to create an emptyUniverse with all dead cells, used to allow user
     * to draw their own patterns
     */
    private void emptyUniverse() {
        isUserDrawing = true;

        universe.getCells().forEach(cell -> cell.setAlive(false));
        updateUniverse();

        repaint();
    }


    @Override
    public void actionPerformed(ActionEvent e) {
        if (isRunning) {
            universe.nextGeneration();
        }
        repaint();
    }

    /*
     * helper method to change the value of the UI grid to reflect the state of the Cell in that
     * location
     */
    private void updateUniverse() {
        universe.getCells().forEach(cell -> {
            grid[cell.getLocation().x][cell.getLocation().y] = cell.getAliveOrDeadSym();
        });
    }

    /*
     * gathers data from pattern csv files of coordinate locations
     * uses change cell to change the cell at those locations to be alive
     * once all cells are updated the UI universe is updated to reflect the changes
     * in cells
     */
    private void displayPattern() {
        isUserDrawing = false;
        java.util.List<Universe.Direction> patternLocations = PatternCSVReadWrite.readPatterns();
        patternLocations.forEach(direction -> changeCell(direction.x, direction.y));
        updateUniverse();
        repaint();
    }

    //changes the cells to alive that the user draws themselves to ensure
    //cells are updated with the changes to the UI
    private void changeCell(int x, int y) {
        universe.getCells().stream()
                .filter(cell -> cell.getLocation().x == x && cell.getLocation().y == y)
                .forEach(cell -> cell.setAlive(true));

//        getPatterns(x, y); //only uncomment to track new patterns to a file
    }

    /*
     * only to be used to add locations to pattern csv files,
     * no need for use in game
     */
    private void getPatterns(int x, int y) {
        StringBuilder sb = new StringBuilder();
        sb.append(x);
        sb.append(",");
        sb.append(y);


        //send list of cells to CSVWriter
        PatternCSVReadWrite.writePatters(sb.toString());
    }

    @Override
    public void mouseMoved(MouseEvent e) {

    }

    /*
     * nested class to check for key press events
     * Done: space = pause/unpause
     * Done: 'c' = pause/clear screen to allow user to start from blank slate
     * Done: 'R' = resume program when user is done drawing after pressing 'C' key
     * Done: 'P' = puts cool pattern on screen
     */
    class LifekeyAdapter extends KeyAdapter {

        @Override
        public void keyPressed(KeyEvent e) {
            switch(e.getKeyCode()) {
                case KeyEvent.VK_SPACE:
                    if (isRunning) {
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
                case KeyEvent.VK_R:
                    isUserDrawing = false;
                    resume();
                    break;
                case KeyEvent.VK_P:
                    emptyUniverse();
                    displayPattern();
                    break;
            }
        }
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

    //pause while user makes small changes to running program
    @Override
    public void mousePressed(MouseEvent e) {
        pause();
    }

    //when user releases mouse and user not drawing on emptyUniverse, start running
    @Override
    public void mouseReleased(MouseEvent e) {
        if (!isUserDrawing) {
            resume();
        }
    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }

    //********MouseMotionListener methods**********//
    //allow users to make cells alive that they click and drag over
    @Override
    public void mouseDragged(MouseEvent e) {
        int xCoord = e.getX() / CELL_SIZE;
        int yCoord = e.getY() / CELL_SIZE;

        if (grid[xCoord][yCoord] == Character.MIN_VALUE) {
            grid[xCoord][yCoord] = 'O';
            changeCell(xCoord,yCoord);
        }

        repaint();
    }
}
