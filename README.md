# GameOfLife
Implementation of Conway's game of life

# 4 rules of Conway's game of life
* Living cell with less than 2 living neighbors will die in next generation - under population
* Living cell with greater than 3 living negihbors will die in next generation - over population
* Living cell with 2-3 living neighbors will live in next generation
* Dead cell with exactly 3 living neighbors will be reborn - reproduction

# How to play - Buttons
* Space Bar - allows users to pause and unpause the game of life
* Left Mouse Button - users can hold down to drag and draw, upon release game unpauses
* C key - creates a blank slate where users can draw their own initial patterns 
* R key - If C key is used, R key needs to be pressed to resume game after user has drawn their own patterns
* P key - generates random cool patterns for user to enjoy

# How to run game
* Download the GameOfLife.jar file from the current release
* run java -jar "path to jar" in terminal to launch game
