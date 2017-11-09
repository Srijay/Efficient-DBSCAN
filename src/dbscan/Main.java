package dbscan;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;


public class Main {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		double epsilon = 0.0001; //distance threshold
		int minPoints = 10; //density threshold
		int numfiles = 0;
		long times[] = new long[100000];
		int totalClusters = 0;
		long totalTime = 0;
		ConstructGrid cg;
		
		String inDirNameD = "TestSnapshot/";
		
		File theDir = new File(inDirNameD.replaceAll("/", "_") + "clusters");

		// if the directory for cluster does not exist, create it
		if (!theDir.exists()) {
		    System.out.println("creating directory for clusters ");
		    boolean result = false;

		    try{
		        theDir.mkdir();
		        result = true;
		    } 
		    catch(SecurityException se){
		        //handle it
		    }        
		    if(result) {    
		        System.out.println("DIR created");  
		    }
		}
		
		String outFileNameD = theDir.toString();
		
		String outFileName;
		int i = 0;
		
	try
   	 {
    	
    	   totalClusters = 0;
    	   totalTime = 0;
    	   
    	   File dir = new File(inDirNameD);
    	   File[] directoryListing = dir.listFiles();
    	   
    	   if (directoryListing != null) {
    		   
    	    for (File child : directoryListing) {
    	   
    	      
			outFileName = outFileNameD + "/" + child.toString().replaceAll("/", "_");
			
			//System.out.println("Here " + child.toString());
									
			ArrayList<Point> pointList = FileOperations.readFile(child.toString()); //Reading file
			
			long startTime = System.currentTimeMillis();
					    
			cg = FileOperations.constructGrid(pointList, epsilon); //Constructing grid
			
			DBSCAN dbscan = new DBSCAN(cg);
			
			HashMap<Integer, ArrayList<Point>> clusters = dbscan.DBSCAN_CALL(minPoints, epsilon); //call for clustering
			
			long endTime = System.currentTimeMillis();
			
			times[i] = endTime - startTime; //time calculation
			
			totalTime += times[i];
	        
	        totalClusters += clusters.size();
			
	        			        			
			FileOperations.writeClustersToFile(clusters, outFileName);  //writing result into file
			
			i++;
			numfiles++;
					
			
    	    }
    	   }
		
		System.out.println("Total Time Taken to form clusters of " + numfiles + " snapshots is " + totalTime + " milliseconds ");
		System.out.println("Total number of clusters is " + totalClusters);
		
		 BufferedWriter br = null;
	    	
	     br = new BufferedWriter(new FileWriter("timeplot.txt"));
						
		
    		
    		for(i=0;i<numfiles;i++)
    		{
    		  br.write((i+1) + " " + times[i] + "\n");
    		}
    		
		br.close();
 
		Point p1 = new Point("153_20070721",39.967159,116.345122);
		Point p2 = new Point("163_20080704",39.967077,116.345172);
		
		//System.out.println("Here distance is " + p1.computeDistanceBetweenPoints(p2));
				
     }catch (IOException e) {
		     System.out.println("Problems in writing file");
		   }
	
	}

}
