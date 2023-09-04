// Jaztin Tabunda
// Project 2
// 11/6/2022
// Minimize Number Late Algorithm Testing

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.lang.Math;
import java.util.Scanner;

public class Project2 {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Scanner txtInput = null;
	    try
	    {
	       txtInput = new Scanner(new File("input.txt"));
	    }
	    catch(FileNotFoundException e)
	    {
	       System.out.println("Did you forget the input file?");
	       System.exit(1);
	    } //borrowed scanner from project0 and project1
	      
	    int N = txtInput.nextInt(); // number of Jobs
	    int numLate = 0; //number of late
	    
	    ArrayList<Pair> arrList = new ArrayList<Pair>(); //arraylist to hold inputted jobs
	    ArrayList<Pair> lateList = new ArrayList<Pair>(); //arraylist to hold late jobs
	    
	    //defining the comparators for comparing edf, sjf, and lsf
	    CompareDead edf = new CompareDead(); 
	    CompareProc sjf = new CompareProc();
	    CompareSlack lsf = new CompareSlack();
	    
	    //storing pairs into an Array List
	    for(int counter = 0; counter < N; counter++)
	    {
	    	Pair pair = new Pair(txtInput.nextInt(), txtInput.nextInt());
	    	arrList.add(pair);
	    }
	    
	    Collections.sort(arrList, edf); //using collections to 'schedule' the list according to edf
	    lateList = lateJobs(arrList); //looks in arrList for the jobs that were late and stores in lateList
	    numLate = lateList.size(); //looks in the lateList to get the number of late 
	    System.out.println("EDF " + numLate); 
	    numLate = 0; // resets number of late jobs
	    lateList.clear(); // clears the lateList
	    
	    Collections.sort(arrList, sjf); //using collections to 'schedule' the list according to sjf
	    lateList = lateJobs(arrList); 
	    numLate = lateList.size(); 
	    System.out.println("SJF " + numLate);
	    numLate = 0; 
	    lateList.clear(); 
	    
	    Collections.sort(arrList, lsf); //using collections to 'schedule' the list according to lsf
	    lateList = lateJobs(arrList); 
	    numLate = lateList.size();
	    System.out.println("LSF " + numLate);
	    numLate = 0;
	    lateList.clear();
	    
	}
	
	//checks the scheduled arrayList for latejobs
	//this method does this by having a runningTotal called currentSum which keeps track of the currently scheduled processing times
	//if the currentsum is greater than the deadline of a job that is being looked at, 
	//then it's considered late and is added to the latelist 
	public static ArrayList<Pair> lateJobs(ArrayList<Pair> scheduledList)
	{
		ArrayList<Pair> lateList = new ArrayList<Pair>();
		int currentSum = 0;
		for(int counter = 0; counter < scheduledList.size(); counter++)
	    {
	    	currentSum = currentSum + scheduledList.get(counter).getKey();
	    	if( currentSum > scheduledList.get(counter).getValue() )
	    	{
	    		lateList.add(scheduledList.get(counter));
	    	}
	    }
		return lateList;
	}

}

//this class I made is going to be used to store the (ProcessingTime, Deadline)
//added getters and setters just in case I need them. 
//the getSlack method will give the slack that we need for lsf
class Pair{
	
	private int key = 0;
	private int value = 0;
	private int slack = 0;
	
	public Pair()
	{
	}
	
	public Pair(int val1, int val2)
	{
		key = val1;
		value = val2;
	}
	
	public void setKey(int val1)
	{
		key = val1;
	}
	
	public void setValue(int val2)
	{
		value = val2;
	}
	
	public int getKey()
	{
		return key; 
	}
	
	public int getValue()
	{
		return value; 
	}
	
	public int getSlack()
	{
		slack = Math.abs(value - key);
		return slack;
	}
	
	@Override
	public String toString(){
		String pairFormat = "(" + key + "," + value + ")";
		return pairFormat;
	}  
	
}

//these comparator classes will be used to sort the arrayList
//comparator for sjf
class CompareProc implements Comparator<Pair>
{
	@Override
	public int compare(Pair o1, Pair o2) {
		// TODO Auto-generated method stub
		return (o1.getKey() - o2.getKey());
	}
}
//comparator for edf
class CompareDead implements Comparator<Pair>
{
	@Override
	public int compare(Pair o1, Pair o2) {
		// TODO Auto-generated method stub
		return (o1.getValue() - o2.getValue());
	}
}
//comparator for lsf
class CompareSlack implements Comparator<Pair>
{
	@Override
	public int compare(Pair o1, Pair o2) {
		// TODO Auto-generated method stub
		return (o1.getSlack() - o2.getSlack());
	}
}