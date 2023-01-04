package avengers;

/**
 * Given a Set of Edges representing Vision's Neural Network, identify all of the 
 * vertices that connect to the Mind Stone. 
 * List the names of these neurons in the output file.
 * 
 * 
 * Steps to implement this class main method:
 * 
 * Step 1:
 * MindStoneNeighborNeuronsInputFile name is passed through the command line as args[0]
 * Read from the MindStoneNeighborNeuronsInputFile with the format:
 *    1. v (int): number of neurons (vertices in the graph)
 *    2. v lines, each with a String referring to a neuron's name (vertex name)
 *    3. e (int): number of synapses (edges in the graph)
 *    4. e lines, each line refers to an edge, each line has 2 (two) Strings: from to
 * 
 * Step 2:
 * MindStoneNeighborNeuronsOutputFile name is passed through the command line as args[1]
 * Identify the vertices that connect to the Mind Stone vertex. 
 * Output these vertices, one per line, to the output file.
 * 
 * Note 1: The Mind Stone vertex has out degree 0 (zero), meaning that there are 
 * no edges leaving the vertex.
 * 
 * Note 2: If a vertex v connects to the Mind Stone vertex m then the graph has
 * an edge v -> m
 * 
 * Note 3: use the StdIn/StdOut libraries to read/write from/to file.
 * 
 *   To read from a file use StdIn:
 *     StdIn.setFile(inputfilename);
 *     StdIn.readInt();
 *     StdIn.readDouble();
 * 
 *   To write to a file use StdOut:
 *     StdOut.setFile(outputfilename);
 *     //Call StdOut.print() for EVERY neuron (vertex) neighboring the Mind Stone neuron (vertex)
 *  
 * Compiling and executing:
 *    1. Make sure you are in the ../InfinityWar directory
 *    2. javac -d bin src/avengers/*.java
 *    3. java -cp bin avengers/MindStoneNeighborNeurons mindstoneneighborneurons.in mindstoneneighborneurons.out
 *
 * @author Yashas Ravi
 * 
 */


public class MindStoneNeighborNeurons {
    
    public static void main (String [] args) {
        
    	if ( args.length < 2 ) {
            StdOut.println("Execute: java MindStoneNeighborNeurons <INput file> <OUTput file>");
            return;
        }
    	
    	// WRITE YOUR CODE HERE
        String mindStoneNeighborNeuronsInputFile = args[0];
        String mindStoneNeighborNeuronsOutputFile = args[1];

        StdIn.setFile(mindStoneNeighborNeuronsInputFile);

        int v = StdIn.readInt();                        //number of neurons (vertices in the graph)
        String [] arr = new String[v];                  //each with a String referring to a neuron's name (vertex name)
        
        for (int i = 0; i < arr.length; i++){           //populate the string array with the vertex names
            String read = StdIn.readString();
            arr[i] = read; 
            //System.out.println(arr[i]);         
        }

        int e = StdIn.readInt();                        //number of synapses (edges in the graph)
        //System.out.println(e);
        String [][] arr2 = new String [e][2];           //each line refers to an edge, each line has 2 (two) Strings: from to

        for (int i = 0; i < e; i++){
                String first = StdIn.readString();
                arr2[i][0] = first;                     //store the from in index 0
                //System.out.println(arr2[0][i]);
                String second = StdIn.readString();     
                arr2[i][1] = second;                    //store the to index 1
                //System.out.println(arr2[1][j]);   
        }

        StdOut.setFile(mindStoneNeighborNeuronsOutputFile);
        
        String newString = arr2[0][1];                         //create a new string for the from and to
        
        while (true){
            String currentString = searchNode(newString, arr2);// call the search method with the new string

            if (currentString.equals(newString)){              // if both strings are the same, break out of the loop
                break;
            }
            else{
                newString = currentString;                     // update the newstring to be the current one
            }
        }

        for (int i = 0; i < arr2.length; i++){                 //do a for loop to iterate through the whole array to find the vertices
            if (arr2[i][1].equals(newString)){                 //if ther vertices are connected to the mindstone, print the 0 index that stores the vertex names
                StdOut.println(arr2[i][0]);
            }
        }
    }

    // A search method to look for connected vertices to the mindstone
    private static String searchNode(String first, String[][] arr2){
        for (int i = 0; i < arr2.length; i++){                 // iterate through the array to search for connected vertices
            if (arr2[i][0].equals(first)){
                return arr2[i][1];
            }
        }
        return first; 
    }
}
