# Hashing
Author: Daniel Yuan
Summary: Implementing a hash table to analyze collision handling methods

README - Instructions for the user to use this program.
The three files (AKA classes) are Hashing, HashTable, and TableEntry. This program is coded with NetBeansIDE with the Java programming language.

Most of what the user will need to change in the code in order to run it is in Hashing.java, where the main method is located. The parameters: TABLE_SIZE, DIV_MOD, and PRT_CNTR will need to be modified accordingly. These pre-determined values are specified later on in the code comments later on in Hashing.java. Note that the user also needs to change the TABLE_SIZE static variable in the HashTable.java code as well, matching that of the one in Hashing.java. The PRT_CNTR will determine if the printing of the hashtable will be successful. The specific values for the static variables is written above in the comments of each block of collision handling code.

The user does not need to touch the input, which is located in a text file named 'numbers.txt' within the hashing folder. The output will be automatically printed out onto the text file appropriately named the "output.txt". Note that each time the code is compiled the output file gets wiped and the records are written over what was on it previously.
I have saved a text file that shows how the values would look like if you ran the code. (all_possible_outputs.txt)

When choosing which collision handling method to run, simply uncomment the block of code located on the hashing.java file. In addition, within each block of collision handling code, there exists two different hash functions: division and multiplication. Division is the top line and multiplication is the bottom line (two lines of multiplications). Simply choose one hash function and comment out the other one prior to compiling the program. Note that the user can run multiple collision handling methods and blocks of code, AS LONG AS the static variables TABLE_SIZE, DIV_MOD, and PRT_CNTR remains the same for all collision handling methods. (e.g. the user can run linear probing, quadratic probing, and chaining with division, when static parameters remain unchanged).

The program will print out certain statistics upon finishing filling the hash table, specifically the primary collisions, secondary collisions, and the time it took for the program to sort the objects. In this program specifically, secondary collision is defined as colliding with another object that has already had a collision.
