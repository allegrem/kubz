package utilities;

/**
 * Objet representant un point en 2D
 * par ses coordonnees x et y
 * 
 * @author paul
 *
 */
public class Point {
	private double x;
	private double y;
	
	/**
	 * Nouveau point defini par ses coordonnees
	 * @param x coordonnee x
	 * @param y coordonnee y
	 */
	public Point(double x, double y){
		this.x=x;
		this.y=y;
		
	}

	/**
	 * 
	 * @return La coordonnee x du point
	 */
	public double getX(){
		
		return x;
	}
	
	/**
	 * 
	 * @return La coordonne y du point
	 */
	public double getY(){
		
		return y;
	}
	
	/**
	 * Deplace le point en un autre point
	 * @param x nouvelle coordonnee x
	 * @param y nouvelle coordonnee y
	 */
	public void move(double x,double y){
		this.x=x;
		this.y=y;
	}
	
	/**
	 * Deplace le point de dx et dy
	 * @param dx le deplacement relatif dx
	 * @param dy le deplacement relatif dy
	 */
	public void translate(double dx, double dy){
		x+=dx;
		y+=dy;
		
	}
	
	/**
	 * Placer le point a l'emplacement d'un autre point
	 * @param p L'endroit ou doit etre place le point
	 */
	public void setLocation(Point p){
		x=p.getX();
		y=p.getY();
		
	}
	
	/**
	 * Definit la coordonnee x du point
	 * @param x
	 */
	public void setX(double x){
		this.x=x;
		
	}
	
	/**
	 * Definit la coordonnee y du point
	 * @param y
	 */
	public void setY(double y){
		this.y=y;
		
	}
}
