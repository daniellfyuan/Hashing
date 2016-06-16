/**
 *
 * @author Daniel Yuan
 * 
 * The main class for implementing a hash table and utilizing different
 * collision handling methods. This class will get the integers that needed to
 * be stored from numbers.txt and store them into an array. The user can then
 * choose which collision handling method to implement. The program will then
 * print the finished hash table in an output file called output.txt. Please see
 * the README file for detailed instructions for the user.
 */
package hashing;
import java.io.*;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;


public class Hashing {
    //Constants
    private final static int SAMPLE_SIZE = 90;
    private final static double CONSTANT_A = 0.5;
    
    //***CHANGE THESE VALUES ACCORDINGLY***
    private final static int TABLE_SIZE = 120;
    private final static int DIV_MOD = 120;
    private final static int PRT_CNTR = 4;
   
     
    public static void main(String[] args) throws FileNotFoundException {
        //Scanning the provided ints into an array from numbers.txt
        int[] numsForTable = new int[SAMPLE_SIZE];
        try {
            Scanner s = new Scanner(new File("numbers.txt"));
            
            int i = 0;
            while(s.hasNextInt())
            {
                 numsForTable[i] = s.nextInt();
                 i++;
            }
        } 
        catch (FileNotFoundException ex) {
            Logger.getLogger(Hashing.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        //Prints the output from the console to a text file (output.txt)
        PrintStream out = new PrintStream(new FileOutputStream("output.txt"));
        System.setOut(out);
        
               
        //INITIALIZE HASHTABLE PROGRAM, HOORAY!
        HashTable hashTable = new HashTable();
        
        
        //***TO USER: CHANGE STUFF BELOW***
        //Using commenting to decide which collision handling method to use
        //and the hash function used (either division or multiplication)
        
        
        
        //Linear Probing Collision Handling
        //TABLE_SIZE = 120, DIV_MOD = 120 or 127, PRT_CNTR = 4
        for (int i = 0; i < numsForTable.length; i++){
            
            int hashFunc = (numsForTable[i] % DIV_MOD);
            //int hashFunc = (int)(Math.floor(TABLE_SIZE*(numsForTable[i]
                  // *CONSTANT_A % 1)));
            hashTable.insertLin(numsForTable[i], hashFunc);
        }
        System.out.println("Hashing with Linear Probing");
        hashTable.printTable(PRT_CNTR);
        hashTable.printStats();
        
        
        
        /**
        //Linear Probing Collision Handling with bucket size 3
        //TABLE_SIZE = 40, DIV_MOD = 41, PRT_CNTR = 2 (or not 4 in general)
        for (int i = 0; i < numsForTable.length; i++){
            
            int hashFunc = (numsForTable[i] % DIV_MOD);
            
             
            hashTable.insertLinTwo(numsForTable[i], hashFunc);
        }
        System.out.println("Hashing with Linear Probing, Bucket = 3");
        hashTable.printTable(PRT_CNTR);
        hashTable.printStats();
        **/
        
        
        
        //Quadratic Probing Collision Handling
        //TABLE_SIZE = 120, DIV_MOD = 120 or 127, PRT_CNTR = 4
        for (int i = 0; i < numsForTable.length; i++){
            
            int hashFunc = (numsForTable[i] % DIV_MOD);
            //int hashFunc = (int)(Math.floor(TABLE_SIZE*(numsForTable[i]
                    //*CONSTANT_A % 1)));
            
            hashTable.insertQuad(numsForTable[i], hashFunc, DIV_MOD);
        }
        System.out.println("Hashing with Quadratic Probing");
        hashTable.printTable(PRT_CNTR);
        hashTable.printStats();
        
        
        
        /**
        //Quadratic Probing Collision Handling with bucket size 3
        //TABLE_SIZE = 40, DIV_MOD = 41, PRT_CNTR = 2 (or not 4 in general)
        for (int i = 0; i < numsForTable.length; i++){
            
            int hashFunc = (numsForTable[i] % DIV_MOD);
            
            
            hashTable.insertQuadTwo(numsForTable[i], hashFunc, DIV_MOD);
        }
        System.out.println("Hashing with Quadratic Probing, Bucket = 3");
        hashTable.printTable(PRT_CNTR);
        hashTable.printStats();
        **/
        
        
        
        //Chaining Collision Handling
        //TABLE_SIZE = 120, DIV_MOD = 120 or 127, PRT_CNTR = 4
        for (int i = 0; i < numsForTable.length; i++){
            
            int hashFunc = (numsForTable[i] % DIV_MOD);
            //int hashFunc = (int)(Math.floor(TABLE_SIZE*(numsForTable[i]
                    //*CONSTANT_A % 1)));
            
            hashTable.insertChain(numsForTable[i], hashFunc);
        }
        System.out.println("Hashing with Chaining");
        hashTable.printTable(PRT_CNTR);
        hashTable.printStats();
        
        
    }
    
   
}
