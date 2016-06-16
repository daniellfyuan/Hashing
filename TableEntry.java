/**
 *
 * @author Daniel Yuan
 * 
 * This class is the element that will be stored in the hash table (array
 * created in HashTable.java). The user can store up to three keys in this
 * class, which will act as the "bucket" in my program. This class has getter
 * and setter methods for the key(s), collision tracking, and methods used for
 * implementing linked lists for the chaining collision handling method.
 */
package hashing;


public class TableEntry {
    private int key;
    
    //use the below keys when bucket size increases to 3
    private int key2;
    private int key3;
    
    //use when dealing with chaining
    private TableEntry next;
    
    private boolean collided;
    
    TableEntry(int key) {
        this.key = key;
        this.key2 = 0;
        this.key3 = 0;
        this.next = null;
        this.collided = false;
    }
    
    //Getters and setters for multiple keys
    public int getKey() {
        return key;
    }
    
    public int getKeyII(){
        return key2;
    }
    
    public int getKeyIII(){
        return key3;
    }
    
    public void setKeyII(int key2){
        this.key2 = key2;
    }
    
    public void setKeyIII(int key3){
        this.key3 = key3;
    }
    
    //The two following methods are used to differentiate table entry objects
    //that has bumped into a collision vs. those that did not
    public void setCollide(){
        this.collided = true;
    }
    
    public boolean hasCollided(){
        return this.collided;
    }
    
    //Two Methods below are for the linked list/chaining sections
    //Get the next entry in the list/chain.
    public TableEntry getNext(){
        return next;
    }
    
    //Add a new element to the chain, used for chaining collision handling
    public void addEntry (TableEntry newEntry){
        this.next = newEntry;
    }
    
    //Boolean to check if the bucket is full, return true if full, not used
    //in this program.
    public boolean fullBucket(){
        if((key != 0) && (key2 != 0) && (key3 != 0)){
            return true;
        }
        return false;
    }
        
}   

