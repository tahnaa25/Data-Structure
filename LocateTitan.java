package avengers;
/**
 * 
 * Using the Adjacency Matrix of n vertices and starting from Earth (vertex 0), 
 * modify the edge weights using the functionality values of the vertices that each edge 
 * connects, and then determine the minimum cost to reach Titan (vertex n-1) from Earth (vertex 0).
 * 
 * Steps to implement this class main method:
 * 
 * Step 1:
 * LocateTitanInputFile name is passed through the command line as args[0]
 * Read from LocateTitanInputFile with the format:
 *    1. g (int): number of generators (vertices in the graph)
 *    2. g lines, each with 2 values, (int) generator number, (double) funcionality value
 *    3. g lines, each with g (int) edge values, referring to the energy cost to travel from 
 *       one generator to another 
 * Create an adjacency matrix for g generators.
 * 
 * Populate the adjacency matrix with edge values (the energy cost to travel from one 
 * generator to another).
 * 
 * Step 2:
 * Update the adjacency matrix to change EVERY edge weight (energy cost) by DIVIDING it 
 * by the functionality of BOTH vertices (generators) that the edge points to. Then, 
 * typecast this number to an integer (this is done to avoid precision errors). The result 
 * is an adjacency matrix representing the TOTAL COSTS to travel from one generator to another.
 * 
 * Step 3:
 * LocateTitanOutputFile name is passed through the command line as args[1]
 * Use Dijkstra’s Algorithm to find the path of minimum cost between Earth and Titan. 
 * Output this number into your output file!
 * 
 * Note: use the StdIn/StdOut libraries to read/write from/to file.
 * 
 *   To read from a file use StdIn:
 *     StdIn.setFile(inputfilename);
 *     StdIn.readInt();
 *     StdIn.readDouble();
 * 
 *   To write to a file use StdOut (here, minCost represents the minimum cost to 
 *   travel from Earth to Titan):
 *     StdOut.setFile(outputfilename);
 *     StdOut.print(minCost);
 *  
 * Compiling and executing:
 *    1. Make sure you are in the ../InfinityWar directory
 *    2. javac -d bin src/avengers/*.java
 *    3. java -cp bin avengers/LocateTitan locatetitan.in locatetitan.out
 * 
 * @author Yashas Ravi
 * 
 */

public class LocateTitan {
	
    public static void main (String [] args) {
    	
        if ( args.length < 2 ) {
            StdOut.println("Execute: java LocateTitan <INput file> <OUTput file>");
            return;
        }

    	// WRITE YOUR CODE HERE
        String locateTitanInputFile = args[0];
        String locateTitanOutputFile = args[1];

        StdIn.setFile(locateTitanInputFile);

        int g = StdIn.readInt();
        double [][] arr = new double [2][g];        //only two indexes: 0 and 1
        
        for(int j = 0; j < g; j++){
            int first = StdIn.readInt();
            arr[0][j] = (int) first;                //store the integer value in index 0
            //System.out.println(arr[i][j]); 
            double second = StdIn.readDouble();     
            arr[1][j] = second;                     //store the double value in index 1
            //System.out.println(arr[1][j]);  
            } 

        //Create an adjacency matrix for g generators.
        int [][] matrix = new int[g][g];
        for(int i = 0; i < matrix.length; i++){      // populate the array with the matrix  
            for(int j = 0; j < matrix[i].length; j++){
                int read2 = StdIn.readInt();
                matrix[i][j] = read2; 
                //System.out.print(matrix[i][j] + " ");      
            } 
            //System.out.println();   
        }
        /*
         * Update the adjacency matrix to change EVERY edge weight (energy cost) by DIVIDING it 
         * by the functionality of BOTH vertices (generators) that the edge points to. Then, 
         * typecast this number to an integer (this is done to avoid precision errors). The result 
         * is an adjacency matrix representing the TOTAL COSTS to travel from one generator to another.
         */
        StdOut.setFile(locateTitanOutputFile);

        for(int i = 0; i < matrix.length; i++){   
            for(int j = 0; j < matrix[i].length; j++){
                int index = matrix[i][j];                            //get the index value that is on the matrix. E.g (1,4) has the value 7
                double func = arr[1][i] * arr[1][j];                 //multiply the functionality values for both i and j
                int divide = (int) ((double) index / (double) func); //cast to double for both values, then cast to an int
                matrix[i][j] = divide;                               //update the matrix
                //System.out.print(matrix[i][j] + " ");  
                //StdOut.print(matrix[i][j] + " ");
            }
            //System.out.println();  
            //StdOut.println(); 
        } 

        // Use Dijkstra’s Algorithm to find the path of minimum cost between Earth and Titan.
        int node = matrix.length;                                    // number of nodes
        int[] minCost = new int[node];                               // array of minimum cost to reach every node
        boolean[] dijkstraSet = new boolean[node];                   // array of nodes already visited
         
        for(int i = 0; i < minCost.length; i++){        
            if (i == 0){                                             //if i is 0, update mincost to be o
                minCost[i] = 0;
            }
            else{
                minCost[i] = Integer.MAX_VALUE;                      //else mincose gets the max value
            }
        }

        for(int i = 0; i < node-1; i++){                                             // Find the smallest path for all nodes
            int currentSource = getminCostNode(g, minCost, dijkstraSet);             // Find the node with minimum cost from start node
            //System.out.print(currentSource);
            dijkstraSet[currentSource] = true;                                       // Node visited
                    
            for (int j = 0; j < node; j++){                                          // Check with all the neighbor nodes
                if (dijkstraSet[j] == false && matrix[currentSource][j]> 0 &&        // Check if j is not visited and there is an edge from currentsource to j
                    minCost[currentSource] != Integer.MAX_VALUE &&
                    (minCost[currentSource] + matrix[currentSource][j] < minCost[j])){
                        minCost[j] = minCost[currentSource] + matrix[currentSource][j]; //update the mincost accordingly
                    }
                }
            }
            StdOut.println(minCost[node-1]);        
    }

    // Find the node with minimum cost from start node
    private static int getminCostNode(int g, int[] minCost, boolean[] dijkstraSet){
        int min = Integer.MAX_VALUE; //max node
        int min2 = -1;  

        for (int i = 0; i < g; i++){ //find the minimum cost from node 0
            if (!dijkstraSet[i] && minCost[i] < min){
                min = minCost[i];
                min2 = i;
            }
        }
        return min2;
    }    
}
