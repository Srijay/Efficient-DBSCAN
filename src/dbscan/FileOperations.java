package dbscan;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;


public class FileOperations {

	
	public static ArrayList<Point> readFile(String filename)
	{
				
		double x,y;
		ArrayList<Point> pointList = new ArrayList<Point>();
		BufferedReader br = null;

		
		try
		{
	        
	        br = new BufferedReader(new FileReader(filename));
			
			String l;
		   	
       	    String[] coords;
    
			while((l = br.readLine()) != null)
		       {
		       		   	
		       	    coords = l.split(" ");
		       	    		       	    
		       	    x = Double.parseDouble(coords[1]);
		   	    
		       	    y = Double.parseDouble(coords[2]);
		       	      		       
			       	pointList.add(new Point(coords[0],x,y));	
		       	   
		       	    
		       } 
			
			br.close();
			
			
		    
			
		}
		catch (IOException e) {
			System.out.println("file not found");
		   }
		
		
		 return pointList;
		
	}
	
	public static ConstructGrid constructGrid(ArrayList<Point> pointList, double epsilon) //Construction of grid
	{
		
		    double minX,minY,maxX,maxY;
		
	     	ConstructGrid cg = new ConstructGrid();
					
			Iterator<Point> iterator = pointList.iterator();
			
	        Point p;
	        
	        minX = 1000000;
       	    maxX = -1000000;
       	    minY = 1000000;
    	    maxY = -1000000;
       	    
	        
	        while (iterator.hasNext()) {
	        	
	        	    p = iterator.next();
	        	    	        	 		       	    
		       	    if(p.x < minX)
		       	    	minX = p.x;
		       	    else if(p.x > maxX)
		       	    	maxX = p.x;
		       	   		       	    
		       	    if(p.y < minY)
		       	    	minY = p.y;
		       	    else if(p.y > maxY)
		       	    	maxY = p.y;
	            
	        }
	        						
			cg.setParameters(epsilon, minX, minY, maxX, maxY);
	        cg.setRowsCols();
	         
	        
	        iterator = pointList.iterator();
	        
	        while (iterator.hasNext()) {
	        	
	           p = iterator.next();

	       	   cg.AddPointToGrid(p); 	

	        }
	        
		
		 return cg;
		
	}

    public static void writeClustersToFile(HashMap<Integer, ArrayList<Point>> clusters, String output)
    {
    	BufferedWriter br = null;
    	int nBorder = 0;
    	    	
    	try
    	{
    		br = new BufferedWriter(new FileWriter(output));
        	
    		Iterator<Integer> iterator = clusters.keySet().iterator();
            
            while (iterator.hasNext()) {
            	
               int key = iterator.next();

               ArrayList<Point> pl = clusters.get(key);
               
               Iterator<Point> it = pl.iterator();
               
               br.write("Total number of objects in below cluster is " + pl.size() + "\n");
                              
               while(it.hasNext())
               {
            	   Point p = it.next();
            	   
            	   br.write(key + " " + p.ObjectId + " " + p.x + " " + p.y + "\n");
            	   
            	   if(p.Classifier == "BORDER")
            		   nBorder ++;            	               	   
               }
               
               
            }
        	
          //  System.out.println("The number of border points are " + nBorder);
            
        	br.close();
    	}
    	catch (IOException e) {
			System.out.println("Problem in writing file");
		   }
    	
    	
    	
    }
}



