package dbscan;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

public class DetermineCorePoints {
	
	ConstructGrid cg;
	
	DetermineCorePoints(ConstructGrid cg)
	{
		this.cg = cg;
	}
	
	public ArrayList<Point> getNeighbourHoodPointsFromCell(Point p, long neighbouringKey) //Get all neighbourhood points from given point
	{
		
		double epsilon = cg.epsilon;
				
		 ArrayList<Point> pointList = cg.grid.get(neighbouringKey);
		 
		 ArrayList<Point> neighbourPointList = new ArrayList<Point>();
         
		 if(pointList == null)
			 return neighbourPointList;
		       	   
      	   Iterator<Point> it = pointList.iterator();
	           
	           while(it.hasNext())
	           {
	        	   Point np = it.next();
	        	   
	        	   if(p.computeDistanceBetweenPoints(np) <= epsilon)
	        	   {
	        		   neighbourPointList.add(np);
	        	   }
	           }
		
		
		
		return neighbourPointList;
	}
	
	public ArrayList<Point> getNeighbourHoodPoints(Point p,ArrayList<Long> aroundKeys, int listSize, int minPoints) //Given a point, get all neighboring points from around 21 cells. 
	{
		
		int threshHold = minPoints - listSize + 1;
		
		Iterator<Long> it = aroundKeys.iterator();
			   
	    ArrayList<Point> neighbouringPoints = new ArrayList<Point>();
	    
	    long neighbourKey;
	    
	    while(it.hasNext())
	    {
	    	neighbourKey = it.next();
	    	neighbouringPoints.addAll(getNeighbourHoodPointsFromCell(p,neighbourKey));
	    	
	    	if(neighbouringPoints.size() >= threshHold)
	    		return neighbouringPoints;
	    }
	    
	    
	   return neighbouringPoints;
  
	}
	
	public void markCorePoints(int minPoints) //Mark all core points
	{
		
		 HashMap<Long,ArrayList<Point>> grid = cg.grid;
				
		 Iterator<Long> iterator = grid.keySet().iterator();
	        
	        while (iterator.hasNext()) {
	        	
	           long key = iterator.next();

	           ArrayList<Point> pointList = grid.get(key);
	           
	           Iterator<Point> it = pointList.iterator();
	           
	           int listSize = pointList.size();
	           int listSizeOfPoint;
	           
	           
	           
	           if(listSize > minPoints)
	           {
	        	   
		           while(it.hasNext())
		           {
		        	   Point p = it.next();
		        	  
		        	   
		        	   p.Classifier = "CORE";
		           }
	        	   
	           }
	           else
	           {
	        	   
	        	   
	        	   ArrayList<Long> aroundKeys = cg.get21AroundKeys(key);
	        	   
	        	   
	        	  
	        	   while(it.hasNext())
		           {
		        	   Point p = it.next();
		        	   
	   
		        	   listSizeOfPoint = listSize + getNeighbourHoodPoints(p,aroundKeys,listSize,minPoints).size() - 1;
		        	  		        	   
		        	   
		        	   if(listSizeOfPoint >= minPoints)
		        	   {
		        	     p.Classifier = "CORE";
		        	   }
		        	   
		           }
	        	   
	           }
	           
	        }
	}	
    
}
