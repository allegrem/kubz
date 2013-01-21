package utilities;
public class Distance {
	
	public static double distanceToLine(Point A, Point B, Point P)
	{
	 double normalLength = Math.hypot(B.getX() - A.getX(), B.getY() - A.getY());
	 return Math.abs((P.getX() - A.getX()) * (B.getY() - A.getY()) - (P.getY() - A.getY()) * (B.getX() - A.getX())) / normalLength;
	}
	
	
	
}
