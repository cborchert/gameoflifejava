package com.cborchert.gameOfLife;

public class Main {

    /**
     * Colors lifted from
     * https://stackoverflow.com/questions/5762491/how-to-print-color-in-console-using-system-out-println
     */
    public static final String ANSI_BLACK_BACKGROUND = "\u001B[40m";
    public static final String ANSI_RED_BACKGROUND = "\u001B[41m";
    public static final String ANSI_GREEN_BACKGROUND = "\u001B[42m";
    public static final String ANSI_YELLOW_BACKGROUND = "\u001B[43m";
    public static final String ANSI_BLUE_BACKGROUND = "\u001B[44m";
    public static final String ANSI_PURPLE_BACKGROUND = "\u001B[45m";
    public static final String ANSI_CYAN_BACKGROUND = "\u001B[46m";
    public static final String ANSI_WHITE_BACKGROUND = "\u001B[47m";
    public static final String ANSI_RESET = "\u001B[0m";
    public static final String ANSI_BLACK = "\u001B[30m";
    public static final String ANSI_RED = "\u001B[31m";
    public static final String ANSI_GREEN = "\u001B[32m";
    public static final String ANSI_YELLOW = "\u001B[33m";
    public static final String ANSI_BLUE = "\u001B[34m";
    public static final String ANSI_PURPLE = "\u001B[35m";
    public static final String ANSI_CYAN = "\u001B[36m";
    public static final String ANSI_WHITE = "\u001B[37m";


    /**
     * Params for generating the initial grid
     */
    static int numRows = 100;
    static int numCols = 30;
    static int initPopulation = 500;

    /**
     * Params for displaying the grid
     */
    static String emptyIcon = " ";
    static String filledIcon = ANSI_GREEN + "■" + ANSI_RESET;

    /**
     * Params for the animation
     */
    static int numGens = 500;
    static int frameMs = 100;


    Grid grid;

    /**
     * Spin it up
     * @param args the args
     */
    public static void main(String[] args) {
       // create a game and animate the grid
	   Main game = new Main();
	   game.play();
    }

    /**
     * Constructor
     */
    public Main() {
        grid = new Grid(numRows, numCols, initPopulation);
    }

    /**
     * Clears the console
     */
    public static void clearScreen() {
        System.out.print("\033[H\033[2J");
        System.out.flush();
    }

    /**
     * Displays the current grid state on the console
     */
    public void displayGrid() {
        clearScreen();
        System.out.print(grid.toString(emptyIcon, filledIcon));
    }

    /**
     * Animates the grid for numGens generations on the console
     */
    private void play(){
        int generation = 0;
        while (generation < numGens) {
            // display the current grid
            displayGrid();
            // advance the grid to its next state
            grid.next();

            // Wait _at least_ frameMs before next iteration
            // lifted from
            // https://stackoverflow.com/questions/24104313/how-do-i-make-a-delay-in-java
            try {
                Thread.sleep(frameMs);
            } catch (InterruptedException ex) {
                Thread.currentThread().interrupt();
            }
            generation++;
        }
    }

}
