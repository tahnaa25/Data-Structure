package conwaygame;
import java.util.ArrayList;
/**
 * Conway's Game of Life Class holds various methods that will
 * progress the state of the game's board through it's many iterations/generations.
 *
 * Rules 
 * Alive cells with 0-1 neighbors die of loneliness.
 * Alive cells with >=4 neighbors die of overpopulation.
 * Alive cells with 2-3 neighbors survive.
 * Dead cells with exactly 3 neighbors become alive by reproduction.

 * @author Seth Kelley 
 * @author Maxwell Goldberg
 */
public class GameOfLife {

    // Instance variables
    private static final boolean ALIVE = true;
    private static final boolean  DEAD = false;

    private boolean[][] grid;    // The board has the current generation of cells
    private int totalAliveCells; // Total number of alive cells in the grid (board)

    /**
    * Default Constructor which creates a small 5x5 grid with five alive cells.
    * This variation does not exceed bounds and dies off after four iterations.
    */
    public GameOfLife() {
        grid = new boolean[5][5];
        totalAliveCells = 5;
        grid[1][1] = ALIVE;
        grid[1][3] = ALIVE;
        grid[2][2] = ALIVE;
        grid[3][2] = ALIVE;
        grid[3][3] = ALIVE;
    }

    /**
    * Constructor used that will take in values to create a grid with a given number
    * of alive cells
    * @param file is the input file with the initial game pattern formatted as follows:
    * An integer representing the number of grid rows, say r
    * An integer representing the number of grid columns, say c
    * Number of r lines, each containing c true or false values (true denotes an ALIVE cell)
    */
    public GameOfLife (String file) {

        // WRITE YOUR CODE HERE
        //StdIn.readInt();
         //File name = new File ("grid0.txt")

        StdIn.setFile(file);         //set the file
    
         int r = StdIn.readInt();    //rows
         int c = StdIn.readInt();    //cols
         grid = new boolean [r][c];  //to store the boolean

         //int totalAliveCells = 0;

        // put the elements in the array
        // need to read the table of the boolean values using StdIn.readBoolean()
        //grid[r][c] = StdIn.readBoolean();

        for(int i = 0; i < grid.length; i++){
            for(int j = 0; j < grid[i].length; j++){
                grid[i][j] = StdIn.readBoolean(); //read the boolean values and store them in the grid
                if (grid[i][j] == ALIVE){
                    totalAliveCells ++;
                    }   
                }   
            }    
        }

    /**
     * Returns grid
     * @return boolean[][] for current grid
     */
    public boolean[][] getGrid () {
        return grid;
    }
    
    /**
     * Returns totalAliveCells
     * @return int for total number of alive cells in grid
     */
    public int getTotalAliveCells () {
        return totalAliveCells;
    }

    /**
     * Returns the status of the cell at (row,col): ALIVE or DEAD
     * @param row row position of the cell
     * @param col column position of the cell
     * @return true or false value "ALIVE" or "DEAD" (state of the cell)
     */
    public boolean getCellState (int row, int col) {

        // WRITE YOUR CODE HERE
        // we do no loop since check only one cell when we click on the cell state on the driver
        // need to access elements in a 2d array.

        if (grid[row][col] == ALIVE){ // based on the constructor, check whether the cells from the text file are alive or dead
            return true;              // return true if the cell is alive (the grey ones)
        }else {
            return false;             // return false if dead
        }
         // update this line, provided so that code compiles
    }

    /**
     * Returns true if there are any alive cells in the grid
     * @return true if there is at least one cell alive, otherwise returns false
     */
    public boolean isAlive () {

        // WRITE YOUR CODE HERE

        if (totalAliveCells > 0){
            return true;     //found alive cells
        }
        else{
            return false;   //no alive cells
        }
    }

    /**
     * Determines the number of alive cells around a given cell.
     * Each cell has 8 neighbor cells which are the cells that are 
     * horizontally, vertically, or diagonally adjacent.
     * 
     * @param col column position of the cell
     * @param row row position of the cell
     * @return neighboringCells, the number of alive cells (at most 8).
     */
    public int numOfAliveNeighbors (int row, int col) {

        // WRITE YOUR CODE HERE
        int count = 0;      //store the numofaliveneighbors

     for (int i = -1; i < 2; i++){
        int r = row +i;
        if (r == grid.length){
            r = 0;
        }
        if (r == -1){
            r = grid.length - 1;
        }
        for (int j = -1; j < 2; j++){
            int c = col+j;
            if (c == grid[r].length){
                c = 0;
            }
            if (c == -1){
                c = grid[r].length - 1;
            }
            if (grid[r][c] == ALIVE){
                count++;
            }
        }
     }
     if ( ALIVE == grid[row][col] ){     //if a cell is alive and has alive neighbirs, then decrement 1 so it does not count itself
        count = count-1;
    } 
        
        return count;  
                //if (i >= 0 && i < row && j >= 0 && j < col && i != row && j != col){
                //if ( ALIVE == grid[i][j] ){
                    //count ++;  
               // }                       
        //issue- if i make it count-1 then the dead cells also returns 1 less alive cell near it
        //return 0;// update this line, provided so that code compiles
    }

    /**
     * Creates a new grid with the next generation of the current grid using 
     * the rules for Conway's Game of Life.
     * 
     * 1. Alive cells with no neighbors or one neighbor die of loneliness.
     * 2. Dead cells with exactly three neighbors become alive by reproduction.
     * 3. Alive cells with two or three neighbors survive.
     * 4. Alive cells with four or more neighbors die of overpopulation.
     * 
     * @return boolean[][] of new grid (this is a new 2D array)
     */
    public boolean[][] computeNewGrid () { 

        // WRITE YOUR CODE HERE

        //check with certain number of alive cells
        //check with nextgeneration and numofaliveneighbor
        int row = grid.length;
        int col = grid[0].length;
        boolean [][] computegrid = new boolean[row][col];         //initialize a new boolean

        //iterate through the grid and check with Conways' rules
        //update the totalalivecells in each if statements
        for (row = 0; row < grid.length; row++){
            for (col = 0; col < grid[0].length; col++){
                int alive = numOfAliveNeighbors(row, col);       //create a variable that will store the current numofaliveneighbors
                if ((grid[row][col] == ALIVE)){
                    if ((alive <= 1)){                            //((alive >= 4)) ||
                        totalAliveCells = totalAliveCells-1;
                        computegrid[row][col] = DEAD;            //rule 1 , 4
                    }
                    else if (alive <= 3){
                        totalAliveCells = totalAliveCells+1;
                        computegrid[row][col] = ALIVE; 
                    }
                    else{
                        computegrid[row][col] = DEAD; 
                        totalAliveCells = totalAliveCells-1;    //rule 3
                    }
                }
                else{
                    if ((alive == 3)){
                        totalAliveCells = totalAliveCells+1;    //rule 2
                        computegrid[row][col] = ALIVE; 
                    }
                    else {                    //dead cells with not 3 cells stay dead
                        computegrid[row][col] = DEAD;
                    }
                }
            }
        }
        return computegrid;                                    //return the new grid
        //return new boolean[1][1]; update this line, provided so that code compiles
    }

    /**
     * Updates the current grid (the grid instance variable) with the grid denoting
     * the next generation of cells computed by computeNewGrid().
     * 
     * Updates totalAliveCells instance variable
     */
    public void nextGeneration () {

        // WRITE YOUR CODE HERE
        grid = computeNewGrid();
        //totalAliveCells = getTotalAliveCells();
    }

    /**
     * Updates the current grid with the grid computed after multiple (n) generations. 
     * @param n number of iterations that the grid will go through to compute a new grid
     */
    public void nextGeneration (int n) {

        // WRITE YOUR CODE HERE
        //loop through n time
        for (int i = 0; i < n; i++){
            nextGeneration();
        }
    }

    /**
     * Determines the number of separate cell communities in the grid
     * @return the number of communities in the grid, communities can be formed from edges
     */
    public int numOfCommunities() {

        // WRITE YOUR CODE HERE
        //cells that are connected to each other
        int r = grid.length;
        int c = grid[0].length;
        int count= 0; //to store the number of community
        //int root = 0;
        WeightedQuickUnionUF community = new WeightedQuickUnionUF(r, c); //call a java file as a variable
        //loop the grid
        for (int row = 0; row < r ; row++){
            for (int col = 0; col < c; col++){
                if (grid[row][col] == ALIVE){
                    for (int i = -1; i < 2; i++){
                        for (int j = -1; j < 2; j++){
                            if (grid[row+i][col+j] == ALIVE){
                                community.union(row, col, row+i, col+j); 
                            //community.find(r,c) ; parent node
                            }
                        }
                    } 
                }
            }
        }
        ArrayList <Integer> root = new ArrayList<Integer>();
        for (int row = 0; row < r ; row++){
            for (int col = 0; col < c; col++){
                if (grid[row][col] == ALIVE){
                    Integer storeRoot = community.find(row, col);
                    if (root.isEmpty() || !root.contains(storeRoot)){ //array list is empty 
                        root.add(storeRoot);
                    }
                }
            }
        }
        count = root.size(); 
        return count;
    }
}
