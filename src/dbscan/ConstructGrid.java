
package dbscan;
import java.util.HashMap;
import java.util.Iterator;
import java.util.ArrayList;

 class ConstructGrid { 
	 	 
	 HashMap<Long,ArrayList<Point>> grid = new HashMap<Long,ArrayList<Point>>();
	 double epsilon;
	 double minX;
	 double minY;
	 double maxX;
	 double maxY;
	 double cellWidth;
	 int nRows;
	 int nCols;
    
	public void setParameters(double epsilon, double minX, double minY, double maxX, double maxY)
	{
		this.minX = minX;
		this.minY = minY;
		this.maxX = maxX;
		this.maxY = maxY;
		this.epsilon = epsilon;
		this.cellWidth = epsilon / Math.sqrt(2);  
	}
	
	public void setRowsCols()
	{
		nRows = (int) Math.floor(((maxX - minX)/cellWidth) + 1);
		nCols = (int) Math.floor(((maxY - minY)/cellWidth) + 1);
	}
	
    public void AddPointToGrid(Point p) //Adding points to grid
    {
    	
    	int []location = getLocationInGridFromPoint(p);
    	
    	int X = location[0];
    	int Y = location[1];
   	
     	long key = (long)X * (long)(nCols + 1) + (long)Y;
     	      
        if(grid.containsKey(key))
        {
        	grid.get(key).add(p);
        }
        else
        {
        	ArrayList<Point> pl = new ArrayList<Point>();
        	pl.add(p);
        	grid.put(key,pl);
        }

    }
    
    public int[] getLocationInGridFromKey(long key) //Given a key, get location pair (x,y) of grid
    {
    	
    	int x_coord = (int)(key / (long)(nCols + 1));
    	int y_coord = (int)(key % (long)(nCols + 1));
    	
    	int[] location = {x_coord,y_coord};
    	
    	return location;
     	
    }
    
    public int[] getLocationInGridFromPoint(Point p)//Given a point, get location pair (x,y) of grid
    {
		    	
    	int X = (int) Math.floor(((p.x - minX)/cellWidth) + 1);
    	int Y = (int) Math.floor(((p.y - minY)/cellWidth) + 1);
    	
    //	System.out.println("here the location is " + nRows + " " + nCols);
    	
    	int[] location = {X,Y};
    	 
    	return location;
     	
    }
    
    public long getKeyFromLocationInGrid(int x_coord, int y_coord)
    {
    	long ans = ((long)x_coord * (long)(nCols + 1) + (long)y_coord);
    	return ans;
    }
    
    public ArrayList<Long> get21AroundKeys(long key) //Given a key of location in grid, get all 21 keys neighbour around the given key
	{
		
	   int[] location = getLocationInGridFromKey(key);
	   
	   ArrayList<Long> aroundKeys = new ArrayList<Long>();
	   
	   int x = location[0];
	   int y = location[1];
	   
	   
	   int xn,yn;
	   
	   for(int i = -2; i<=2; i++)
	   {
		   for(int j = -2; j<=2; j++)
		   {
			   xn = x + i;
			   yn = y + j;
			   
			   if((xn >= 1) && (xn <= nRows ) && (yn >= 1) && (yn <= nCols))
			   {
				   
					   if((i!=0) || (j!=0))
					   {
					     aroundKeys.add(getKeyFromLocationInGrid(xn,yn));
					   }
				   
			   }
		   }
		   
	   }
	   
	   
	   return aroundKeys;
	   
	   
	}

    public boolean isCellClassified(long key) //Check if grid cell is already classified into cluster
	{
		
		ArrayList<Point> list = grid.get(key);
		
		Iterator<Point> it = list.iterator();
		
		Point p;
		
		p = it.next();
			
		if(p.clusterId == 0)
		  return false;
		
		return true;
		
	}
	
	public boolean isCellCore(long key) //Given a grid cell key, check if all points in cell are marked as core
	{
		
		ArrayList<Point> list = grid.get(key);
		
		Iterator<Point> it = list.iterator();
		
		Point p;
		
		while(it.hasNext())
		{
			
			p = it.next();
			
			if(p.Classifier == "CORE")
			  return true;
			
		}
		
		
		return false;
		
	}
	
	public boolean areCellsNeighbour(long key1, long key2) //Given keys of two cells, check if two cells are neighbour or not
	{

		ArrayList<Point> list1 = grid.get(key1);
		ArrayList<Point> list2 = grid.get(key2);
		
		Iterator<Point> it1 = list1.iterator();
		Iterator<Point> it2; 
		
		Point p1,p2;
		
		while(it1.hasNext())
		{
			p1 = it1.next();
			
			it2 = list2.iterator();
			
			if(p1.Classifier == "CORE")
			{
				while(it2.hasNext())
				{
					p2 = it2.next();
					
					if(p2.Classifier == "CORE")
					{
						if((p1.computeDistanceBetweenPoints(p2)) <= epsilon)
							return true;
					}
				
				}
			}
					
			
		}


		return false;
		
	}

 }




