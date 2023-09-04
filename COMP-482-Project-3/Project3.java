// Jaztin Tabunda
// Project 3
// 11/25/2022
// Generalize Binary/Bisection Search

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Project3 {

	//This is to check if one of the recursive calls has already found the answer
	//Note: Project spec doesn't say anything about duplicates and if we need to find all duplicate elements
	static boolean gl_answerFound;
	
	public static void main(String[] args) {
		
		int m = 0; //number of rows
		int n = 0; //number of columns
		int target = 0; //the number we are looking for
		gl_answerFound = false;
		
		Scanner txtInput = null;
	    try
	    {
	       txtInput = new Scanner(new File("input.txt"));
	    }
	    catch(FileNotFoundException e)
	    {
	       System.out.println("Did you forget the input file?");
	       System.exit(1);
	    } //borrowed scanner from project0 and project1 (reading input)
	    
	    m = txtInput.nextInt(); //row size input
	    n = txtInput.nextInt(); //column size input
	    target = txtInput.nextInt(); //target input
	    
	    int array[][] = new int[m][n]; 
	    
	    //filling in 2d array with inputs
	    for(int counter = 0; counter < m; counter++)
	    {
	    	for(int counter2 = 0; counter2 < n; counter2++)
	    	{
	    		array[counter][counter2] = txtInput.nextInt();
	    	}
	    }
	    
	    Project3.TwoDimensionalBS(array, target, 0, 0, m - 1, n - 1); //beginning actual 2d binary search with call to method
	    
	    //if the target is not found in any of the recursion calls
	    if(gl_answerFound == false)
	    {
	    	System.out.println("NOT FOUND");
	    }
	}
	
	public static void TwoDimensionalBS(int[][] array, int target, int mLow, int nLow, int mHigh, int nHigh)
	{
		int mMiddle = (mLow + (mHigh-mLow)/2); //finding the middle row index
		int nMiddle = (nLow + (nHigh-nLow)/2); //finding the middle column index
		
		if(array[mMiddle][nMiddle] == target)
		{
			mMiddle++; //this is to match the sample output since sample row starts at index 1
			nMiddle++; //this is to match the sample output since sample column starts at index 1
			System.out.println(mMiddle + " " + nMiddle);
			gl_answerFound = true; //notifies that the target has already been found
		}
		else
		{	
			//it will only run the recursive call if the target isn't found
			if(gl_answerFound == false)
			{	
			
				//if middle is smaller than target. we rule out top left subarray as stated from spec.
				//then we run recursion on the remaining 3 subarrays
				if((array[mMiddle][nMiddle] < target))
				{
					//if statement to make sure subarray being checked is in bounds
					if(mMiddle < mHigh)
					{
						//bottom left subarray
						TwoDimensionalBS(array, target, mMiddle + 1, nLow, mHigh, nMiddle);
					}
					//if statement to make sure subarray being checked is in bounds
					if((mMiddle != mHigh) && (nMiddle != nHigh))
					{
						//bottom right subarray
						TwoDimensionalBS(array, target, mMiddle + 1, nMiddle + 1, mHigh, nHigh);
					}
					//if statement to make sure subarray being checked is in bounds
					if(nMiddle < nHigh)
					{
						//top right subarray
						TwoDimensionalBS(array, target, mLow, nMiddle + 1, mMiddle, nHigh);
					}
				
				}
				//if middle is bigger than target. we rule out bottom right subarray as stated from example.
				//then we run recursion on the remaining 3 subarrays like in the problem spec
				else //(array[mMiddle][nMiddle] > target)
				{
					//if statement to make sure subarray being checked is in bounds
					if(nMiddle > nLow)
					{
						//bottom left subarray
						TwoDimensionalBS(array, target, mMiddle, nLow, mHigh, nMiddle - 1);
					}
					//if statement to make sure subarray being checked is in bounds
					if((mMiddle != mLow) && (nMiddle != nLow))
					{
						//top left subarray
						TwoDimensionalBS(array, target, mLow, nLow, mMiddle - 1, nMiddle - 1);
					}
					//if statement to make sure subarray being checked is in bounds
					if(mMiddle > mLow)
					{
						//top right subarray
						TwoDimensionalBS(array, target, mLow, nMiddle, mMiddle - 1, nHigh);
					}
					
				} //end of 'else' middle is greater than target
			} //end of 'if' target isn't found in other recursions
		} //end of 'else' target not found statement
	} //end of TwoDimensionalBS Recursive Method
} //end of class
