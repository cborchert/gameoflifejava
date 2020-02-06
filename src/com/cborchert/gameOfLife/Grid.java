package com.cborchert.gameOfLife;

import java.util.Random;

/**
 * Represents an N by M matrix where each entry is a cell which can be on (1) or off (0)
 */
public class Grid {

    /**
     * The population
     */
    private boolean[][] grid;

    /**
     * A random number generator used by our class
     */
    public static Random random = new Random();

    /**
     * Creates a new empty Grid with the given number of rows and columns
     * @param numRows the number of rows in the grid
     * @param numCols the number of columns in the grid
     */
    public Grid(int numRows, int numCols) {
        this.grid = getEmptyGrid(numRows, numCols);
    }

    /**
     * Creates a new Grid, with the given number of rows and columns, populated with up to the specified population
     * @param numRows the number of rows in the grid
     * @param numCols the number of columns in the grid
     * @param initialPopulation the initial population
     */
    public Grid(int numRows, int numCols, int initialPopulation) {
        this.grid = getPopulatedGrid(numRows, numCols, initialPopulation);
    }

    /**
     * Returns a string representation of the grid
     * @return the string representation of the grid
     */
    public String toString() {
        return getGridAsString(" ",  "■");
    }

    /**
     * Returns a string representation of the grid
     * @param emptyIcon representation of an empty cell
     * @param filledIcon representation of a filled cell
     * @return the string representation of the grid
     */
    public String toString(String emptyIcon, String filledIcon) {
        return getGridAsString( emptyIcon,  filledIcon);
    }

    /**
     * Returns a string representation of the grid
     * @param emptyIcon representation of an empty cell
     * @param filledIcon representation of a filled cell
     * @return the string representation of the grid
     */
    private String getGridAsString(String emptyIcon, String filledIcon) {
        int numCols = grid.length;
        int numRows = grid[0].length;

        String gridString = "";

        // create the grid
        // TODO: use an "enhanced for loop" boolean[] booleans : grid
        for (int i = 0; i < numCols; i++) {
            // fill a new row with number of 0s defined by rows
            for (int j = 0; j < numRows; j++) {
                // TODO: use a "StringBuilder" to speed things up
                gridString += (grid[i][j]) ? filledIcon : emptyIcon;
            }
            gridString += "\n";
        }
        return gridString;
    }

    /**
     * Get the cell value at provided x, y coordinates
     * @param x the x coordinate
     * @param y the y coordinate
     * @return the cell value
     */
    public boolean cellAt(int x, int y){
        // if out of bounds, return false
        if(x < 0 || y < 0 || y >= grid.length || x >= grid[y].length){
            return false;
        }
        return grid[y][x];
    }

    /**
     * Get the number of number of neighbors of cell at provided x, y coordinates
     * "Neighbors" includes any orthogonally or diagonally adjacent cell but not the cell itself
     * @param x the x coordinate
     * @param y the y coordinate
     * @return the number of active cells around the given coordinate
     */
    public int numNeighbors(int x, int y){
        // the population of the neighborhood 🏘
        int pop = 0;

        // check all cells from -1 to +1 x and y
        for(int dx = -1; dx <= 1; dx++){
            for(int dy = -1; dy <= 1; dy++) {
                if(dx == 0 && dy == 0){
                    // the current cell does not count
                    continue;
                }
                // otherwise, if the cell is populated, add one to the population
                int x1 = x + dx;
                int y1 = y + dy;
                boolean neighborAtX1Y1 = cellAt(x1, y1);
                if (neighborAtX1Y1) {
                    pop++;
                }
            }
        }
        return pop;
    }

    /**
     * Will the cell at the provided x, y survive the into the next generation?
     * @param x the x coordinate
     * @param y the y coordinate
     * @return will the cell survive ?
     */
    public boolean nextCellAt(int x, int y){
        int numNeighbors = numNeighbors( x, y);
        boolean cellAlive = cellAt(x, y);
        if (cellAlive) {
            // rules of the game of life
            // Any live cell with fewer than two live neighbours dies, as if by
            // underpopulation.
            // Any live cell with two or three live neighbours lives on to the next
            // generation.
            // Any live cell with more than three live neighbours dies, as if by
            // overpopulation.
            return (numNeighbors == 2 || numNeighbors == 3);
        } else {
            // rules of the game of life pt 2
            // Any dead cell with exactly three live neighbours becomes a live cell, as if
            // by reproduction.
            return (numNeighbors == 3);
        }
    }

    /**
     * Return the next grid state from the given grid state
     * @return the next grid state
     */
    public boolean[][] getNextGrid() {
        int numCols = grid.length;
        int numRows = grid[0].length;
        boolean[][] nextGrid = getEmptyGrid(numRows, numCols);


        for (int y = 0; y < nextGrid.length; y++) {
            for (int x = 0; x < nextGrid[y].length; x++) {
                boolean cellAlive = nextCellAt(x, y);
                nextGrid[y][x] = cellAlive;
            }
        }
        return nextGrid;
    }

    /**
     * Following the rules of the game of life, advance the grid
     */
    public void next(){
        grid = getNextGrid();
    }

    /**
     * Creates an empty grid of specified size
     * @param numRows the number of rows in the grid
     * @param numCols the number of columns in the grid
     * @return the empty grid
     */
    public static boolean[][] getEmptyGrid( int numRows, int numCols) {
        // instantiate an empty grid of c by r
        boolean[][] grid = new boolean[numCols][numRows];

        // fill with 0's
        for (int i = 0; i < numCols; i++) {
            // fill a new row with number of 0s defined by rows
            for (int j = 0; j < numRows; j++) {
                grid[i][j] = false;
            }
        }
        return grid;
    }

    /**
     * Creates an empty grid of specified size with up to defined population
     * @param numRows the number of rows in the grid
     * @param numCols the number of columns in the grid
     * @param population the upper limit if cells to include in grid
     * @return the populated grid
     */
    public static boolean[][] getPopulatedGrid(int numRows, int numCols, int population) {
        boolean[][] grid = getEmptyGrid(numRows, numCols);

        //   It's totally possible to randomly populate the same cell several times and
        //     get an initial population less than p
        for (int i = 0; i < population; i++) {
            int y = random.nextInt(grid.length);
            int x = random.nextInt(grid[y].length);
            grid[y][x] = true;
        }
        return grid;
    }

    /**
     * Given a grid returns a deep copy of that grid
     * @param grid
     * @return the copied grid
     */
    public static boolean[][] copyGrid(boolean[][] grid) {
        int numCols = grid.length;
        int numRows = grid[0].length;

        boolean[][] newGrid = new boolean[numCols][numRows];
        // create a
        for (int i = 0; i < numCols; i++) {
            // fill a new row with number of 0s defined by rows
            for (int j = 0; j < numRows; j++) {
                newGrid[i][j] = grid[i][j];
            }
        }
        return newGrid;
    }


    /**
     * TODO: REMOVE. FOR DEBUGGING ONLY
     * Sets the grid state
     * @param newGrid the new grid state
     */
    public void setGrid(boolean[][] newGrid){
        grid = newGrid;
    }


}
