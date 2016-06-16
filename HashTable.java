/**
 * @author Daniel Yuan
 * This class (HashTable) creates an array of TableEntries (defined in the
 * TableEntry class) and store the entries based on different collision handling
 * methods. The methods include linear probing, quadratic probing, and chaining.
 * The actual usage of the methods are located in Hashing.java. The user can
 * call on the methods defined in this class to store the elements into the 
 * Hash table.
 */
package hashing;



public class HashTable {
    //***CHANGE TABLE SIZE ACCORDINGLY, MATCH TABLE_SIZE OF Hashing.java***
    private final static int TABLE_SIZE = 120;
    
   
    
    private TableEntry[] table;
    //A temporary TableEntry object used later to traverse through lists
    //found in the chaining collision handling method
    private TableEntry temp; 
    //for chaining, keeps track of free space in the hash table
    private int[] freeSpace;
    
    //Number of collisions of each type
    //Note what my secondary collision means in README
    private int priCollision;
    private int secCollision;
    //Used to track time it takes to perform each collision handling method
    private long time;
    
    HashTable() {
        //initialize our tables
        table = new TableEntry[TABLE_SIZE];
        for (int i = 0; i < table.length; i++) {
            table[i] = null;
        }
        
        //1 for empty, 0 for full
        freeSpace = new int[TABLE_SIZE];
        for (int i = 0; i < table.length; i++){
            freeSpace[i] = 1;
        }
        
        temp = null;
        priCollision = 0;
        secCollision = 0;
        time = 0;
        
    }
    
    
    
    /**Collision handling via linear probing. When a
     * collision occurs, the method will add one to the hash function (or
     * address) and check if that location is free. The method will loop to the
     * beginning of the hash table if adding one will go beyond the table bounds
     * . One key can be stored before a collision occurs.
     * 
     * @param key The key (also) acts as the item being stored.
     * @param func The address calculated by the hash function used for 
     * collision handling in this method, which will be used to calculate new
     * addresses if collisions occur.
     * 
     */
    public void insertLin(int key, int func){
        //time tracking
        long startTime = System.nanoTime();
        int hashFunc = func;
        //In case the function results in over the table size    
        if(hashFunc >= table.length){
                hashFunc = hashFunc - TABLE_SIZE;
            }
        //Marks the initial address calculated by the hash function
        int marker = hashFunc;
        
        boolean collision = false;
        
        //Collision happened
        while (table[hashFunc] != null && table[hashFunc].getKey() != key){
            collision = true;
            
            //Collision counts, takes into account primary or secondary
            if (table[hashFunc].hasCollided()){
                this.secCollision++;
            }
            else{
                this.priCollision++;
            }
            
            
            //handles looping to the beginning of the hash table
            if ((hashFunc + 1) > table.length){
                hashFunc = 0;
            }
            else{
                hashFunc = hashFunc + 1;
            }
            
            //Checks if the looping has returned to initial location
            //calculated by the hash function
            if (hashFunc == marker){
                System.out.println("Table full, unable to store " + key);
                return;
            }
        }
        table[hashFunc] = new TableEntry(key);
        //For collision counts, see earlier - sets collided boolean in table
        //element to be true
        if(collision){
            table[hashFunc].setCollide();
        }
        
        long endTime = System.nanoTime();
        this.time = endTime - startTime;
    }
    
    
    
    /**Collision handling via linear probing with bucket size 3. When a
     * collision occurs, the method will add one to the hash function (or
     * address) and check if that location is free. The method will loop to the
     * beginning of the hash table if adding one will go beyond the table bounds
     * . Three keys can be stored before a collision occurs.
     * 
     * @param key The key (also) acts as the item being stored.
     * @param func The address calculated by the hash function used for 
     * collision handling in this method, which will be used to calculate new
     * addresses if collisions occur.
     * 
     */
    public void insertLinTwo(int key, int func){
        long startTime = System.nanoTime();
        int hashFunc = func;
        //In case the function results in over the table size    
        if(hashFunc >= table.length){
                hashFunc = hashFunc - TABLE_SIZE;
            }
        //Marks the initial address calculated by the hash function
        int marker = hashFunc;
        
        boolean collision = false;
        
        //Collision happened, all three keys filled/bucket full
        while (table[hashFunc] != null && table[hashFunc].getKeyIII() != 0){
            collision = true;
            
            //Collision counts, takes into account primary or secondary
            if (table[hashFunc].hasCollided()){
                this.secCollision++;
            }
            else{
                this.priCollision++;
            }
            
            
            //handles looping to the beginning of the hash table
            if ((hashFunc + 1) > table.length){
                hashFunc = 0;
            }
            else{
                hashFunc++;
            }
            
            //Checks if the looping has returned to initial location
            //calculated by the hash function
            if (hashFunc == marker){
                System.out.println("Table full, unable to store " + key);
                return;
            }
        }
        
        //Fills the keys one by one until all three are full - then see
        //while loop above
        if (table[hashFunc] == null){
            table[hashFunc] = new TableEntry(key);
        }
        else if(table[hashFunc].getKey() != 0 && table[hashFunc].getKeyII() == 0){
            table[hashFunc].setKeyII(key);
        }
        else{
            table[hashFunc].setKeyIII(key);
        }
        
        //For collision counts, see earlier - sets collided boolean in table
        //element to be true
        if(collision){
            table[hashFunc].setCollide();
        }
        
        long endTime = System.nanoTime();
        this.time = endTime - startTime;
    }
    
    
    
    
    /**Collision handling via Quadratic probing. When a 
     * collision occurs, the method will calculate a new hash function based
     * on the number of times the collision occurs - the more collisions, the
     * larger the hash (quadratic relationship). One key can be stored before
     * a collision occurs.
     * 
     * @param key The key (also) acts as the item being stored.
     * @param func The address calculated by the hash function used for 
     * collision handling in this method, which will be used to calculate new
     * addresses if collisions occur.
     * @param mod Used in calculating the new address when a collision occurs.
     */
    public void insertQuad(int key, int func, int mod){
        long startTime = System.nanoTime();
        
        int hashFunc = func;
        //Makes sure that the address is not larger than the hash table
        if(hashFunc >= table.length){
                hashFunc = hashFunc - TABLE_SIZE;
            }
        
        //keeps track of # of times we collide, used in hash function recalc
        int attempt = 0;
        
        boolean collision = false;
        
        //Collision happened
        while (table[hashFunc] != null && table[hashFunc].getKey() != key){
            attempt++;
            collision = true;
            
            //Collision counts, takes into account primary or secondary
            if (table[hashFunc].hasCollided()){
                this.secCollision++;
            }
            else{
                this.priCollision++;
            }
            
            
            //Self-set cut off point, or computer will keep running
            //and reach out of bounds
            if(attempt == mod){
                System.out.println("Unable to store " + key);
                return;
            }
            
            //Based on homework problem 11-3
            hashFunc = (hashFunc + attempt) % mod;
        }
        table[hashFunc] = new TableEntry(key);
        //For collision counts, see earlier - sets collided boolean in table
        //element to be true
        if(collision){
            table[hashFunc].setCollide();
        }
        
        long endTime = System.nanoTime();
        this.time = endTime - startTime;
    }
    
    
    
    
    /**Collision handling via Quadratic probing with bucket size 3. When a 
     * collision occurs, the method will calculate a new hash function based
     * on the number of times the collision occurs - the more collisions, the
     * larger the hash (quadratic relationship). Three keys can be stored before
     * a collision occurs.
     * 
     * @param key The key (also) acts as the item being stored.
     * @param func The address calculated by the hash function used for 
     * collision handling in this method, which will be used to calculate new
     * addresses if collisions occur.
     * @param mod Used in calculating the new address when a collision occurs.
     */
    public void insertQuadTwo(int key, int func, int mod){
        long startTime = System.nanoTime();
        
        int hashFunc = func;
        //Makes sure that the address is not larger than the hash table
        if(hashFunc >= table.length){
                hashFunc = hashFunc - TABLE_SIZE;
            }
        
        //keeps track of # of times we collide, used in hash function recalc
        int attempt = 0;
        
        boolean collision = false;
        
        //Collision happened, all three keys filled/bucket full
        while (table[hashFunc] != null && table[hashFunc].getKeyIII() != 0){
            attempt++;
            collision = true;
            
            //Collision counts, takes into account primary or secondary
            if (table[hashFunc].hasCollided()){
                this.secCollision++;
            }
            else{
                this.priCollision++;
            }
            
            //Self-set cut off point, or computer will keep running
            //and reach out of bounds
            if(attempt == mod){
                System.out.println("Table full, unable to store " + key);
                return;
            }
            //Based on homework problem 11-3
            hashFunc = (hashFunc + attempt) % mod;
        }
        
        //Fills the keys one by one until all three are full - then see
        //while loop above
        if (table[hashFunc] == null){
            table[hashFunc] = new TableEntry(key);
        }
        else if(table[hashFunc].getKey() != 0 && table[hashFunc].getKeyII() == 0){
            table[hashFunc].setKeyII(key);
        }
        else{
            table[hashFunc].setKeyIII(key);
        }
        
        //For collision counts, see earlier - sets collided boolean in table
        //element to be true
        if(collision){
            table[hashFunc].setCollide();
        }
        
        long endTime = System.nanoTime();
        this.time = endTime - startTime;
    }
    
    
    
    /**Collision handling via chaining. When a collision occurs, the method will
     * look in the freelist (FreeSpace) array for a free location where the hash
     * element can be stored. A chain/singly linked list is created when
     * collision occurs repeatedly at the same locations. A new list item is 
     * added to the end of the chain during such collision.
     * 
     * @param key The key (also) acts as the item being stored.
     * @param func The address calculated by the hash function used for 
     * collision handling in this method.
     */
    public void insertChain(int key, int func){
        long startTime = System.nanoTime();
        
        int hashFunc = func;
        if(hashFunc >= table.length){
                hashFunc = hashFunc - TABLE_SIZE;
            }
        
        int tracker = 0;
        boolean searching = true;
       
                
        //Collision happened
        if (table[hashFunc] != null && table[hashFunc].getKey() != key){
            
            //Collision counts, takes into account primary or secondary
            if (table[hashFunc].hasCollided()){
                this.secCollision++;
            }
            else{
                this.priCollision++;
            }
            
            //This is to traverse to the end of the chain/list prior to adding
            //a new entry to the list
            this.temp = table[hashFunc];
            while(this.temp.getNext() != null){
                this.temp = this.temp.getNext();
            }
            
            //this is to check for free spaces in the table
            //checks from slot 1 to the end
            while (searching){
                //Found a location free in the free list, create new entry
                if(this.freeSpace[tracker] == 1){
                    table[tracker] = new TableEntry(key);
                    table[tracker].setCollide();
                    
                    //the chaining, adding the new entry as a new node in the list
                    this.temp.addEntry(table[tracker]);
                    //This location is no longer free, take off the freelist
                    this.freeSpace[tracker] = 0;
                    searching = false;
                }
                else{
                    //keep looping through the freelist
                    tracker++;
                    if (tracker > freeSpace.length){
                        System.out.println("Table is full, unable to store " + key);
                        return;
                    }
                }
            }
        }
        //no collision here
        else{
            table[hashFunc] = new TableEntry(key);
            //Location no longer free, take off the freelist
            this.freeSpace[hashFunc] = 0;
        }
        
        long endTime = System.nanoTime();
        this.time = endTime - startTime;
    }
    
    
    
    /**Prints num of collisions and time it took to hash the values.
     * These numbers are tracked while the collision handling methods are
     * running. After printing, all values are reset to 0 for the next run.
     */
    public void printStats(){
        System.out.println("\nPrimary Collisions: " + this.priCollision);
        System.out.println("Secondary Collisions: " + this.secCollision);
        System.out.println("Time: " + this.time + " ns\n");
        
        this.priCollision = 0;
        this.secCollision = 0;
        this.time = 0;
    }
    
    
    /**print Hash table to the console. When printing, the method keeps track of
     * how many values are printed, and jumps into a new line after printing 5
     * values from the Hash table. For bucket size of 3, the method will print
     * 3 values before jumping into the next line. The Hash table is reset to
     * null after the printing is done.
     * 
     * @param prtCntr 
     * This parameter tells the methods which format to use, whether printing 5
     * or 3 values per line. When prtCntr = 4 it will print 5 values per line, 
     * when prtCntr != 4 it will print 3 values per line.
     */ 
    public void printTable(int prtCntr){
        int counter = 0;
        
        if(prtCntr == 4){
            for (int i = 0; i<table.length; i++){
                if(counter < prtCntr){
                    if (table[i] == null){
                        System.out.print("Null ");

                    }
                    else{
                    System.out.print(table[i].getKey() + " ");

                    }
                    counter++;
                }
                else {
                    int j = i + 1;
                    if (table[i] == null){
                        System.out.println("Null (" + j + ")");

                    }
                    else{
                        System.out.println(table[i].getKey() + " (" + j + ")");

                    }
                    counter = 0;
                }
            }
        }
        else{
            for (int i = 0; i<table.length; i++){
                int j = i + 1;
                if(table[i] == null){
                    System.out.println("Null Null Null (" + j + ")" );
                }
                else{
                    System.out.println(table[i].getKey() + " " +
                            table[i].getKeyII() + " " +
                            table[i].getKeyIII() + " (" + j + ")");
                }
            }
        }
        
        //Reset the table to null, in preparation for the next run.
        for (int i = 0; i < table.length; i++) {
            table[i] = null;
        }
        
    }
    
   
  
    
}
