package huffman;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.Collections;

/**
 * This class contains methods which, when used together, perform the
 * entire Huffman Coding encoding and decoding process
 * 
 * @author Ishaan Ivaturi
 * @author Prince Rawal
 */
public class HuffmanCoding {
    private String fileName;
    private ArrayList<CharFreq> sortedCharFreqList;
    private TreeNode huffmanRoot;
    private String[] encodings;

    /**
     * Constructor used by the driver, sets filename
     * DO NOT EDIT
     * @param f The file we want to encode
     */
    public HuffmanCoding(String f) { 
        fileName = f; 
    }

    /**
     * Reads from filename character by character, and sets sortedCharFreqList
     * to a new ArrayList of CharFreq objects with frequency > 0, sorted by frequency
     */
    public void makeSortedList() {
        StdIn.setFile(fileName);

	/* Your code goes here */

        sortedCharFreqList = new ArrayList<CharFreq>();       //arraylist from assignment to store the frequency
        int[] num = new int[128];                             // there are 128 ascii values or number of char in the file
        double count = 0.0;                                   //keep count of how many times a char appears in the file

        while(StdIn.hasNextChar() != false){                  //as long as the file has the next char
            num[StdIn.readChar()] += 1;                       //read the char of ascii value and increment
            count++;                                          //if within the same file, then increment the count
        }
            
        for(int i = 0; i < num.length; i++){                  //traverse through the num
            double frequency = (double)num[i]/count;          //count the frequenct by dividing with the number of count
            CharFreq freq4 = new CharFreq((char)i, frequency);//create a new charfreq to store the index and frequency

            if (frequency != 0){
                sortedCharFreqList.add(freq4);                //add new frequency to the array list             
            }
        }

        if(sortedCharFreqList.size() == 1){                   //check with the size of the array list
            char freqChar = sortedCharFreqList.get(0).getCharacter();
            CharFreq freq3 = new CharFreq();
            freq3.setCharacter((char)((freqChar+1) % 128));   //get the remainder
            freq3.setProbOcc(0.0);
            sortedCharFreqList.add(freq3);                    //add to the arraylist         
        } 
        Collections.sort(sortedCharFreqList);                 //do collection sort as mentioned in the instuction
    }

    
    /**
     * Uses sortedCharFreqList to build a huffman coding tree, and stores its root
     * in huffmanRoot
     */

    public void makeTree() {

	/* Your code goes here */
    
        Queue<TreeNode> source = new Queue<TreeNode>();             //store the queue of leaf
        Queue<TreeNode> target = new Queue<TreeNode>();             //store the queue of tree

        for (int i = 0; i < sortedCharFreqList.size(); i++) {
            TreeNode data = new TreeNode();                         //holds the charfreq data (check the treenode file and charfreq file)
            data.setData(sortedCharFreqList.get(i));                //insert the data point from the sorted array into the treenode 
            source.enqueue(data);                                   //enqueue the data in the source queue
            //System.out.println(data.getData().getCharacter());
        }
        
        while(source.size() + target.size() > 1){  

            ArrayList <TreeNode> store = new ArrayList<>();         //create an array list to store the two smallest prob values
            for (int i = 0; i < 2; i++){                            //without the for loop it was only printing c and d

            TreeNode sourcedata = new TreeNode();                   //first node in the queue
                
                if (!target.isEmpty() && !source.isEmpty()){
                    
                    if (source.peek().getData().getProbOcc() <= target.peek().getData().getProbOcc()){          //first source node prob lower than target node
                        sourcedata = source.dequeue();                                                          //dequeu from source the first small prob node
                        store.add(sourcedata);
                    }
                    
                    else if (source.peek().getData().getProbOcc() > target.peek().getData().getProbOcc()){      //first node on source prob is higher than target first node prob
                        sourcedata = target.dequeue();                                                          //dequeu target of the first small prob
                        store.add(sourcedata);
                    }
                }
                
                else if (target.isEmpty()){
                    sourcedata = source.dequeue();              //dequeu from source both the small prob node
                    store.add(sourcedata);
                }
                else if (source.isEmpty()){
                    sourcedata = target.dequeue();              //dequeu from target both the small prob node
                    store.add(sourcedata);
                }
            }
            
            TreeNode left = store.get(0);               //first smallest prob          
            TreeNode right = store.get(1);              //second smallest prob    
            double sumofprob = left.getData().getProbOcc() + right.getData().getProbOcc();

            CharFreq freq = new CharFreq(null, sumofprob);  //create a null char and store the sum of the prob
            TreeNode tree = new TreeNode(freq, left, right);  //create the tree with the char, left and right node
            target.enqueue(tree);                             //enque target from tree
        } 
        huffmanRoot = target.dequeue();                       //deque target for huffman
    }

    

    /**
     * Uses huffmanRoot to create a string array of size 128, where each
     * index in the array contains that ASCII character's bitstring encoding. Characters not
     * present in the huffman coding tree should have their spots in the array left null.
     * Set encodings to this array.
     */
    public void makeEncodings() {

	/* Your code goes here */
        String[] encode = new String[128];                   //create a string array of size 128
        makeencode(huffmanRoot, "", encode);              //call the recursive method
        encodings = encode;                                 //set encodings String array to the array
    }
    
    private static void makeencode(TreeNode codetree, String s, String[] arr){
        
        if (codetree.getData().getCharacter() != null){    //check if the char is not null
            arr[codetree.getData().getCharacter()] = s;    //assign the strign arr char to the current string
        }
        if (codetree.getLeft() != null){
            makeencode(codetree.getLeft(), s + "0", arr);  //add 0 to the string when move to the left
        }
        if (codetree.getRight() != null){
            makeencode(codetree.getRight(), s + "1", arr); //add 1 to the string when move to the right
        }
    }

    /**
     * Using encodings and filename, this method makes use of the writeBitString method
     * to write the final encoding of 1's and 0's to the encoded file.
     * 
     * @param encodedFile The file name into which the text file is to be encoded
     */
    public void encode(String encodedFile) {
        StdIn.setFile(fileName);

	/* Your code goes here */
        String encodesString = "";            //empty string

        while(StdIn.hasNextChar()){           //while there is a next char
            char read = StdIn.readChar();
            encodesString += encodings[read]; //add and read the next one; encodings is the String array
        }
        writeBitString(encodedFile, encodesString); //write the encoding
        //StdOut.print(encodesString);

        }


        //OUTPUT: 0000101010111111110       -----------for input1.txt file
        
        //         four a and a has 0s so four 0
        //         three b and b has 10s so three 10
        //         two c and c has 111s so two 111
        //         one d and d has 110 so one 110

    
    /**
     * Writes a given string of 1's and 0's to the given file byte by byte
     * and NOT as characters of 1 and 0 which take up 8 bits each
     * DO NOT EDIT
     * 
     * @param filename The file to write to (doesn't need to exist yet)
     * @param bitString The string of 1's and 0's to write to the file in bits
     */
    public static void writeBitString(String filename, String bitString) {
            byte[] bytes = new byte[bitString.length() / 8 + 1];
            int bytesIndex = 0, byteIndex = 0, currentByte = 0;
    
            // Pad the string with initial zeroes and then a one in order to bring
            // its length to a multiple of 8. When reading, the 1 signifies the
            // end of padding.
            int padding = 8 - (bitString.length() % 8);
            String pad = "";
            for (int i = 0; i < padding-1; i++) pad = pad + "0";
            pad = pad + "1";
            bitString = pad + bitString;
    
            // For every bit, add it to the right spot in the corresponding byte,
            // and store bytes in the array when finished
            for (char c : bitString.toCharArray()) {
                if (c != '1' && c != '0') {
                    System.out.println("Invalid characters in bitstring");
                    return;
                }
    
                if (c == '1') currentByte += 1 << (7-byteIndex);
                byteIndex++;
                
                if (byteIndex == 8) {
                    bytes[bytesIndex] = (byte) currentByte;
                    bytesIndex++;
                    currentByte = 0;
                    byteIndex = 0;
                }
            }
            
            // Write the array of bytes to the provided file
            try {
                FileOutputStream out = new FileOutputStream(filename);
                out.write(bytes);
                out.close();
            }
            catch(Exception e) {
                System.err.println("Error when writing to file!");
            }
        }
    

    /**
     * Using a given encoded file name, this method makes use of the readBitString method 
     * to convert the file into a bit string, then decodes the bit string using the 
     * tree, and writes it to a decoded file. 
     * 
     * @param encodedFile The file which has already been encoded by encode()
     * @param decodedFile The name of the new file we want to decode into
     */
    public void decode(String encodedFile, String decodedFile) {
        StdOut.setFile(decodedFile);

        /* Your code goes here 
        */

        String read = readBitString(encodedFile); //read the encoded file
        decodedFile = ""; //initialize the file to be empty for now

        while (read.length() != 0){
            TreeNode codetree = huffmanRoot;
            int count = 0;
            while(codetree.getData().getCharacter() == null){
                if(read.substring(count,count+1).equals("0")){ //make sure account for values more than onde digit
                    codetree = codetree.getLeft();
                }
                else{
                    codetree = codetree.getRight();
                }
                count++;  
            }
            decodedFile += codetree.getData().getCharacter();   
            read = read.substring(count);
        }
    StdOut.print(decodedFile);
    }

    /**
     * Reads a given file byte by byte, and returns a string of 1's and 0's
     * representing the bits in the file
     * DO NOT EDIT
     * 
     * @param filename The encoded file to read from
     * @return String of 1's and 0's representing the bits in the file
     */
    public static String readBitString(String filename) {
        String bitString = "";
        
        try {
            FileInputStream in = new FileInputStream(filename);
            File file = new File(filename);

            byte bytes[] = new byte[(int) file.length()];
            in.read(bytes);
            in.close();
            
            // For each byte read, convert it to a binary string of length 8 and add it
            // to the bit string
            for (byte b : bytes) {
                bitString = bitString + 
                String.format("%8s", Integer.toBinaryString(b & 0xFF)).replace(' ', '0');
            }

            // Detect the first 1 signifying the end of padding, then remove the first few
            // characters, including the 1
            for (int i = 0; i < 8; i++) {
                if (bitString.charAt(i) == '1') return bitString.substring(i+1);
            }
            
            return bitString.substring(8);
        }
        catch(Exception e) {
            System.out.println("Error while reading file!");
            return "";
        }
    }

    /*
     * Getters used by the driver. 
     * DO NOT EDIT or REMOVE
     */

    public String getFileName() { 
        return fileName; 
    }

    public ArrayList<CharFreq> getSortedCharFreqList() { 
        return sortedCharFreqList; 
    }

    public TreeNode getHuffmanRoot() { 
        return huffmanRoot; 
    }

    public String[] getEncodings() { 
        return encodings; 
    }
}
