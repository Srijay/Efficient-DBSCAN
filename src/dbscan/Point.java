package dbscan;


class Point { 
   
   String ObjectId;
   
   double x;   
   double y;   
   
   String Classifier;
   
   int clusterId;
  
   public Point() {
       x = Math.random();
       y = Math.random();
       Classifier = "UNCLASSIFIED";
       clusterId = 0;
   }

   public Point(String ObjectId, double x, double y) {
	   this.ObjectId = ObjectId;
       this.x = x;
       this.y = y;
       this.Classifier = "UNCLASSIFIED";
   }
   
   public double computeDistanceBetweenPoints(Point p2) //Euclidean distance
	{
		double x_diff = x - p2.x;
		double y_diff = y - p2.y;
		double ret_val = x_diff*x_diff + y_diff*y_diff;	
		
		
		ret_val = Math.sqrt(ret_val);
		
		return ret_val;
	}
   
//   public double computeDistanceBetweenPoints(Point p2) //Great Circle Distance
//	{
//		
//		 double angle1 = Math.acos(Math.sin(x) * Math.sin(p2.x)
//                + Math.cos(x) * Math.cos(p2.x) * Math.cos(y - p2.y));
//
//        // convert back to degrees
//		  angle1 = Math.toDegrees(angle1);
//
//		 // each degree on a great circle of Earth is 60 nautical miles
//		  double distance = 60 * angle1;
//		
//		System.out.println("Here distance is " + distance);
//		return distance;
//	}

   public String toString() {
       return "The ID - "+ ObjectId +" (" + x + ", " + y + ")";
   } 

}




