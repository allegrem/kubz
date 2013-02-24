package utilities;
/**
 * Sert a calculer differetentes distances
 * @author valeh
 *
 */
public class Distance {
	
	/**
	 * Distance d'un point a une droite
	 * @param A Premiere extremite de la droite
	 * @param B Autre extremite de la droite
	 * @param P Point
	 * @return la distance
	 */
	public static double distanceToLine(Point A, Point B, Point P)
	{
	 double normalLength = Math.hypot(B.getX() - A.getX(), B.getY() - A.getY());
	 return Math.abs((P.getX() - A.getX()) * (B.getY() - A.getY()) - (P.getY() - A.getY()) * (B.getX() - A.getX())) / normalLength;
	}
	
	
	
}
