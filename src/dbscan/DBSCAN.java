package dbscan;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class DBSCAN {
	
	ConstructGrid cg;
	
	DBSCAN(ConstructGrid cg)
	{
		this.cg = cg;
	}
	
	public void printGrid()
	{
        Iterator<Long> iterator = cg.grid.keySet().iterator();
        
        while (iterator.hasNext()) {
        	
           long key = iterator.next();
           
          
           ArrayList<Point> pl = cg.grid.get(key);
           
           Iterator<Point> it = pl.iterator();
           
    	   System.out.println("Key is " + key);
    	   
    	   System.out.println("The values are ");
           
           while(it.hasNext())
           {
        	   Point p = it.next();
        	   
        	   int []location = cg.getLocationInGridFromPoint(p);
        	   
        	   System.out.println("The point is " + p + " and the classification of point is " + p.Classifier + " The location is "+location[0] +  " and " + location[1]);      
        	   
           }
        }
	}
	
	public void printOutLiers()
	{
        Iterator<Long> iterator = cg.grid.keySet().iterator();
        
 	    System.out.println("The outLiers are ");

        while (iterator.hasNext()) {
        	
           long key = iterator.next();
           
          
           ArrayList<Point> pl = cg.grid.get(key);
           
           Iterator<Point> it = pl.iterator();
              	            
           while(it.hasNext())
           {
        	   Point p = it.next();
        	   
        	   if(p.Classifier == "UNCLASSIFIED")
        	   {
            	   System.out.println("The point is " + p);      
        	   }      	         	         	
        	   
           }
        }
	}

	public void printCluster(HashMap<Integer,ArrayList<Point>> clusters)
	{
		 Iterator<Integer> iterator = clusters.keySet().iterator();
	        
	        while (iterator.hasNext()) {
	        	
	           int key = iterator.next();

	           ArrayList<Point> pl = clusters.get(key);
	           
	           Iterator<Point> it = pl.iterator();
	           
	    	   System.out.println("Key is " + key);   
	           
	           while(it.hasNext())
	           {
	        	   Point p = it.next();
	        	   
	        	   System.out.println("The point is " + p + " and the classification of point is " + p.Classifier);
	        	   
	           }
	           
	        }
	        
	}
	
	public Map<Integer, ArrayList<Integer>> getObjectIdsFromClusters(HashMap<Integer,ArrayList<Point>> clusters)   //For Swarm
	{
		Map<Integer, ArrayList<Integer>> ids = new HashMap<Integer, ArrayList<Integer>>();
		
		 Iterator<Integer> iterator = clusters.keySet().iterator();
		 
		 
	        while (iterator.hasNext()) {
	        	
	           int key = iterator.next();

	           ArrayList<Point> pl = clusters.get(key);
	           ArrayList<Integer> il = new ArrayList<Integer>();
	           
	           Iterator<Point> it = pl.iterator();
	           	    	 
	           while(it.hasNext())
	           {
	        	   Point p = it.next();
	        	   il.add(Integer.parseInt(p.ObjectId));	        	  
	           }
	           
	           ids.put(key, il);
	           
	        }
	        
		return ids;
		
	}
	
	public HashMap<Integer, ArrayList<Point>> DBSCAN_CALL(int minPoints,double epsilon) //Actual call to clustering algorithm
	{
        
        DetermineCorePoints cp = new DetermineCorePoints(cg);
        cp.markCorePoints(minPoints);
                        
		MergeClusters mgc = new MergeClusters(cg);
			
        mgc.mergeClusters();
                
        DetermineBorderPoints dbp = new DetermineBorderPoints(cg);
        dbp.classifyBorderPoints(mgc.clusters);
               
	    return mgc.clusters;
	    
	}

}
