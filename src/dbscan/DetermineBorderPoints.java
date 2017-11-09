package dbscan;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

public class DetermineBorderPoints {
	
	ConstructGrid cg;
	
	DetermineBorderPoints(ConstructGrid cg)
	{
		this.cg = cg;
	}

	ArrayList<Integer> getNeibouringClusterIdList(Point p, ArrayList<Long> aroundKeys) //Given a point, get cluster ids if any of occur in neighbourhood
	{
		
		Iterator<Long> it = aroundKeys.iterator();
		long neighbourKey;
		
		ArrayList<Integer> neighbourClusterIdList = new ArrayList<Integer>();
		
		
		while(it.hasNext())
	    {
	    	neighbourKey = it.next();
	    	
	    	if(cg.grid.containsKey(neighbourKey))
	    	{
	    			    		
	    		ArrayList<Point> pointList = cg.grid.get(neighbourKey);
	    		
	    		Iterator<Point> itp = pointList.iterator();
	    		
	    		while(itp.hasNext())
		           {
		        	   Point np = itp.next();
		        	   		        	   
		        	   if(np.Classifier == "CORE")
		        	   {	

		        		   if(p.computeDistanceBetweenPoints(np) <= cg.epsilon)
			        	   {
		        			  neighbourClusterIdList.add(np.clusterId);
			        	   }
		        	   }
		        	   
		           }
			
	    		
	    	}
	    	
	    }
		
		
		return neighbourClusterIdList;
		
	}
	
	public void classifyBorderPoints(HashMap<Integer,ArrayList<Point>> clusters) //Final classification of border points.
	{
		
        HashMap<Long,ArrayList<Point>> grid = cg.grid;
		
		Iterator<Long> iterator = grid.keySet().iterator();
				
		Point p;
		int flag;
		
		while (iterator.hasNext()) {
			
			long key = iterator.next();
			 
		     if(!(cg.isCellClassified(key)))
		     {

		    	 ArrayList<Point> pointList = grid.get(key);
		    	 
		    	 ArrayList<Long> aroundKeys = cg.get21AroundKeys(key);
		           
		         Iterator<Point> it = pointList.iterator();
		         
		         while(it.hasNext())
		           {
		        	   p = it.next();     
		        	   
		        	
		        	   ArrayList<Integer> clusterIdList = 	getNeibouringClusterIdList(p,aroundKeys);
		        	   
		        	  
		        	   
		        	   int clusterId;
		        	   
		        	   if(clusterIdList != null)
		        	   {
		        		   
		        		   Iterator<Integer> itr = clusterIdList.iterator();
		        		   		        		  
		        		   
		        		   
		        		   p.Classifier = "BORDER";
		        		   
		        		   flag = 0;
		        		   
		        		   while((itr.hasNext()) && (flag == 0))
		        		   {
		        			   clusterId = itr.next();
		        			   
		        			   
		        			   
		        			   if(clusterId != 0)
		        			   {
			        		     p.clusterId = clusterId;
			        		     clusters.get(clusterId).add(p);
			        		     flag = 1;
			        		    // break;
		        			   }
		        		   }
		        		   
		        		  //Uncomment below while loop to make overlapping clusters
		        		   
		        		 /* while(itr.hasNext())
		        		   {
		        			   clusterId = itr.next();
		        			   
		        			   if(clusterId != 0)
				        	   {
		        				   Point newp = new Point(p.ObjectId,p.x,p.y);
				        		   newp.Classifier = "BORDER";
				        		   newp.clusterId = clusterId;
				        		   clusters.get(clusterId).add(newp);
				        	   }
		        		   } */
		        		   
		        	   }
		        
		           }
		     }
			
		}
        	
	}

}
