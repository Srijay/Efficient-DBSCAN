package dbscan;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Stack;

public class MergeClusters {
	
	HashMap<Integer,ArrayList<Point>> clusters;
	static int clusterId;
	ConstructGrid cg;
		
	MergeClusters(ConstructGrid cg)
	{
		clusters = new HashMap<Integer,ArrayList<Point>>();
		clusterId = 0;
		this.cg = cg;
	}
	
	void addPointsToClusterFromCell(long key, ArrayList<Point> clusterPoints)
	{
		
		Iterator<Point> it = cg.grid.get(key).iterator();
		
		Point p;
		
		while(it.hasNext())
		{
			p = it.next();
			
			p.clusterId = clusterId;
			
			if(p.Classifier.equals("UNCLASSIFIED"))
			{
					p.Classifier = "BORDER";

			}
			
			clusterPoints.add(p);
		}
				
	}
	
    public ArrayList<Point> expandCluster(long key)
    {
    	
    	
    	 
    	 Stack<Long> st = new Stack<Long>();
    	 
    	 ArrayList<Point> clusterPoints = new ArrayList<Point>();
    	     	 
    	 st.push(key);
    	 
    	 addPointsToClusterFromCell(key,clusterPoints);
    	 
    	 long popKey;
    	 long nkey;
    	 
    	 while(!(st.empty()))
    	 {
    		
    		 popKey = st.pop();

    		 ArrayList<Long> aroundKeys = cg.get21AroundKeys(popKey);
    		 
    		 Iterator<Long> it = aroundKeys.iterator();
    		 
    		 while(it.hasNext())
    		 {
    			 nkey = it.next();
    			 
    			
    			 if(cg.grid.containsKey(nkey))
    			 {
    				 if(!(cg.isCellClassified(nkey)))
    				 {
    					 if(cg.isCellCore(nkey))
    					 {			 
    						 if(cg.areCellsNeighbour(popKey,nkey))
    						 {
    	    					   st.push(nkey);
    	    				       addPointsToClusterFromCell(nkey,clusterPoints);
    						 }
    					 }
    				 }
    			 }
    		 } 
    		 
    	 }
    	 
    	 
		return clusterPoints;
    	
    }
    
	public void mergeClusters()
	{
		
		HashMap<Long,ArrayList<Point>> grid = cg.grid;
		
		Iterator<Long> iterator = grid.keySet().iterator();
		
		while (iterator.hasNext()) {
        	
	     long key = iterator.next();
	     

	     if(!(cg.isCellClassified(key)))
	     {

	    	 if(cg.isCellCore(key))
	    	 {
	    		 
	    		 clusterId = clusterId + 1;
	    		 ArrayList<Point> clusterElements = expandCluster(key);
	    		 clusters.put(clusterId, clusterElements);
	    	 }    	 	 
	     }
	     
		}
		 
		 
	}
 

}
