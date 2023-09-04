// Jaztin Tabunda
// Project 1
// 9/25/2022
// Checking Stability


import java.util.*;
import java.io.*;

public class Project1
{
   public static void main(String[]args)
   {
      Scanner txtInput = null;
      try
      {
         txtInput = new Scanner(new File("input.txt"));
      }
      catch(FileNotFoundException e)
      {
         System.out.println("Did you forget the input file?");
         System.exit(1);
      } //borrowed scanner from project0
      
      int N = txtInput.nextInt(); // number of men and women
            
      int[][] menPref = new int[N][N]; //2d array to store men preferences
      int[][] womenPref = new int[N][N]; //2d array to store women preferences
      
      HashMap<Integer, Integer> currentMatching = new HashMap<Integer, Integer>(); //HashMap to store the current matching: (key, value) -> (M, W)
      
      int[][] menPossibility = new int[N][N - 1]; //2d array to store the possible women the men might leave for
      int[][] womenPossibility = new int[N][N - 1];  //2d array to store the possible men the women might leave for
      
      boolean stabilityStatus = false; //variable to check if matching is stable
      int numberOfSwaps = 0; //counts the number of swaps
      
      menPref = fillPrefArray(N, txtInput); //filling up men preference 2-dimensional array
      womenPref = fillPrefArray(N, txtInput); //filling up women preference 2-dimensional array
      
      for(int counter = 0; counter < N; counter++)
      {
         currentMatching.put(txtInput.nextInt(), txtInput.nextInt());
      } //filling up current matching HashMap
      
      menPossibility = fillPossibArray(N, menPref, currentMatching); //filling up the men possibility array
      currentMatching = swapKeyVal(currentMatching); //women become the keys in the map. (swapping to utilize HashMap "get" method)
      womenPossibility = fillPossibArray(N, womenPref, currentMatching); //filling up the women possibility array
      currentMatching = swapKeyVal(currentMatching); //currentMatching is reverted back to original format
      
      while((stabilityStatus == false) && (numberOfSwaps <= 100)) //loop will keep going as long as there is an instability and as long as it's less than 100 swaps
      {
         for(int counter = 0; counter < N; counter++)
         {
            for(int counter2 = 0; counter2 < (N - 1); counter2++)
            {
               if( (isPossibArrayEmpty(menPossibility) == true) || (isPossibArrayEmpty(womenPossibility) == true) )
               {
                  counter = N;
                  stabilityStatus = true;
                  break;
               } //checks if possibility arrays are filled with 0s. If filled with 0s, no possible switches
               
               //a possible preference is between 1 to N. 
               if(menPossibility[counter][counter2] != 0)
               {
                  //checkStability passes in the possible woman that a man would want, the man that wants the woman, and the possible affairs for women 
                  stabilityStatus = checkStability(menPossibility[counter][counter2], counter + 1, womenPossibility); 
                  
                  //if there is an instability
                  if((stabilityStatus == false))
                  {   
                     currentMatching = instabilitySwap(currentMatching, menPossibility, counter, counter2); //swaps 2 couples' partners
                     numberOfSwaps++; //adds 1 every time a swap occurs
                     menPossibility = fillPossibArray(N, menPref, currentMatching); //updating the Men Possibility Array with new possibilities
                     currentMatching = swapKeyVal(currentMatching); //women become the keys in the map. (W, M) (swapping to utilize HashMap get method)
                     womenPossibility = fillPossibArray(N, womenPref, currentMatching); //updating the women Possibility Array with new possibilities
                     currentMatching = swapKeyVal(currentMatching); //swapping HashMap back to (M, W) format
      
                     counter = 0; //checks back at the beginning of the newly made possibility array
                     counter2 = -1; //checks back at the beginning of the newly made possibility array.
                                    //counter2 is -1 because when it loops around and adds 1 right away. 
                                    //we want both counters to start at [0][0] when checking the possibility array
                                    
                  } //"stabilityStatus" if statement
               } //"menPossibility" if statement
            } //for loop 2
         } //for loop 1
     } //while statement     
      printOutput(numberOfSwaps, currentMatching);
   } //end of main method
   
   //this method turns the keys into values and values into keys
   //this is used for implementing HashMap methods that pertains to keys for both men and women 
   public static HashMap<Integer, Integer> swapKeyVal(HashMap<Integer, Integer> map) 
   {
      HashMap<Integer, Integer> newMap = new HashMap<Integer, Integer>();
      map.forEach((key, value) ->
      {
         newMap.put(value, key);
      }); //for each mapping, the key will be placed in the value spot
         //and the value will be placed in the key spot in the new mapping
      return newMap;
   }
   
   //fills the preference arrays for both men and women
   //takes input from Scanner/input file
   public static int[][] fillPrefArray(int arrSize, Scanner txtInput)
   {
      int[][] pref = new int[arrSize][arrSize];
      //double for loop to traverse entire 2d array and store input at each index
      for(int counter = 0; counter < arrSize; counter++)
      {
         for(int counter2 = 0; counter2 < arrSize; counter2++)
         {
            pref[counter][counter2] = txtInput.nextInt();
         } //for loop column
      } //for loop row
      
      return pref;
   }
   
   //fills the possibility arrays for both men and women
   //this array is used to record all of the possible men/women that each man/woman would leave for
   public static int[][] fillPossibArray(int arrSize, int[][] pref, HashMap<Integer, Integer> currentMatching)
   {
      int[][] possibility = new int[arrSize][arrSize - 1];
      //counter/counter2 to go through entire 2d array and fill if needed
      for(int counter = 0; counter < arrSize; counter++)
      {
         int counter2 = 0;
         //goes through a row (one man's/woman's preferences) and stores each value into the new array
         //until it reaches the man's current match
         //then proceeds to the next row
         while(pref[counter][counter2] != (int)currentMatching.get(counter + 1))
         {
            possibility[counter][counter2] = pref[counter][counter2];
            counter2++; //column counter
         } //while loop
      } //for loop
      
      return possibility;
   }
   
   //checks for stability
   //passes in one possible woman for a male, the male that wants the woman, and the affair possibilities for women
   public static boolean checkStability(int maleOnePossibility, int maleNumber, int[][] femalePossibility)
   {
      //checks the row to see if the male is in the preferred woman's row in the femalePossibility array
      //if the man is in the row, there is an instability because both man and woman are more interested in each other
      //compared to their current matching
      for(int counter = 0; counter < femalePossibility[0].length; counter++)
      {
          if((femalePossibility[maleOnePossibility - 1][counter] == maleNumber))
          {
             return false;
          }
      } 
      return true;
   }
   
   //swaps the couples that are experiencing instability in the Hashmap
   //in other words, swap the keys between 2 matches
   public static HashMap<Integer, Integer> instabilitySwap(HashMap<Integer, Integer> currentMatching, int[][] menPossibility, int row, int column)
   {
      //using 2 variables to temporarily hold the value and the key that is to be swapped
      int temp = (int)currentMatching.get(row + 1);
      int swappingKey;
      
      //turns the matching to (W, M)              
      currentMatching = swapKeyVal(currentMatching);
      //gets the other key (man) by using the woman selected in the menPossibility array
      swappingKey = (int)currentMatching.get(menPossibility[row][column]);
      //turns matching back to (M, W)
      currentMatching = swapKeyVal(currentMatching);
      //overwrites previous matchings with the newly swapped ones
      currentMatching.put(row + 1, currentMatching.get(swappingKey));
      currentMatching.put(swappingKey, temp);
      
      return currentMatching;
   }
   
   //checks possibility arrays if all values are 0
   //if it is then that means no one will switch
   //false = all values in array are not 0
   //true = all values in array are 0 
   public static boolean isPossibArrayEmpty(int[][] possibArray)
   {
      boolean check = true;
      for(int counter = 0; counter < possibArray[0].length; counter++)
      {
         for(int counter2 = 0; counter2 < possibArray[0].length; counter2++)
         {
            if(possibArray[counter][counter2] != 0)
            {
               check = false;
            }
         }
      }
      return check;
   }
   
   //prints final output
   public static void printOutput(int numSwaps, HashMap<Integer, Integer> map)
   {
      System.out.println(numSwaps);
      map.forEach((key, value) ->
      {
         System.out.println(key + " " + value);
      });
   }
   
}