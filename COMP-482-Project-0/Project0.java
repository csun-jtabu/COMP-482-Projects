// John Noga
// Project 0
// 1/20/2021
// Read in a set of ints and find the median.


  
// ************************************************************************

// Note the submitted file begins with my name and the name of the project.
// Not starting with these 2 lines will lose you at least 5 points.

// Note that there is absolutely no line that says package.
// Putting your class into a package will lose you at least 5 points

// Note that the name of the class is capital "P" little "roject"
// and a single digit number indicating which project it is. Using 
// a different name will lose you at least 5 points.

// Note that the class compiles exactly as it is. Submitting a file
// that does not compile will lose you at least 5 points even if it 
// is a "trivial and easily fixed mistake that you just forgot about".

// Note that the class actually accomplishes the goal. Failing to
// get the correct answer to a valid input will lose you at least 5
// points.

// Note that the class is structured reasonably (consistent proper 
// indentation, no extremely long methods, no extremely long lines, 
// suggestive variable names). Failing to structure your code 
// reasonably will lose you at least 5 points.

// Note that (in addition to the comments helping to explain the 
// requirements) the code contains enough comments that it is clear 
// how it works. Failing to make your code clear will lose you at
// least 5 points.

// Note the class reads the data from a file called "input.txt"
// which is in the same directory as the java file. Requiring
// the input to be called anything else or to be anywhere else
// will lose you at least 5 points.

// Note that the class handles input.txt formatted exactly as 
// described in the project description. Requiring any other
// format or restricting to a given size will lose you at least 
// 5 points.

// Note the output is formatted exactly as explained in the project
// description. Failing to match the spec will lose you at least 5 points.


//*************************************************************************


import java.util.*;
import java.io.*;

// This project reads a set of integers from a file and finds the median.
// Note the name of the class is Project0 as required.
public class Project0 {

   public static void main(String[] args) {
      int[] theData = getInput();
      hmmm(theData);
      output(theData);
   }

   // Reading from input.txt and filling theData
   private static int[] getInput() {
      Scanner sc = null;
      try {
         //Note the filename is input.txt without any mention of the path
         sc = new Scanner(new File("input.txt"));
      }
      catch(FileNotFoundException e) {
         System.out.println("Did you forget the input file?");
         System.exit(1);
      }
      int pos=0, size = sc.nextInt();
      int[] theData = new int[size];
      while (sc.hasNextInt()) {
         theData[pos++] = sc.nextInt();
      }
      return theData;
   }

   // Sorting the array
   private static void hmmm(int[] theData) {
      boolean done = false;
      int j=0;
      while (!done) {
         done = true;
         j++;
         for (int i=0; i<theData.length - j; i++) {
            if (theData[i] > theData[i+1]) {
               // These three lines swap the 2 values???
               theData[i+1] = theData[i]+theData[i+1];
               theData[i] = theData[i+1]-theData[i];
               theData[i+1] = theData[i+1]-theData[i];
               done=false;
            }
         }
      }
   }

   // Finding and printing out the median. In the case of an even number
   // of data items it averages the 2 in the middle.
   public static void output(int[] theData) {
      double med;
      if (theData.length % 2 != 0) 
         med = theData[theData.length/2];
      else
         med = ((double) theData[(theData.length-1)/2] 
               + theData[theData.length/2])/2;
      System.out.println(med);
   }
}
