package utilities;

/**
 * Classe permettant de creer des vecteurs 3D
 * @author paul
 *
 */
public class Vector {

	private double x;
	private double y;
	private double z;
	
	/**
	 * Definit le nouveau vecteur par ses coordonnees
	 * x, y et z
	 * @param x
	 * @param y
	 * @param z
	 */
	public Vector(double x, double y,double z){
		this.x=x;
		this.y=y;
		this.z=z;
		
	}
	/**
	 * Pour un vecteur en 2D.
	 * Definit ses coordonnes x et y, z etant mis a 0.
	 * @param x
	 * @param y
	 */
	public Vector(double x, double y){
		this.x=x;
		this.y=y;
		this.z=0;
		
	}

	/**
	 * 
	 * @return La composante x du vecteur
	 */
	public double getX(){
		
		return x;
	}
	
	/**
	 * 
	 * @return La composante Y du vecteur
	 */
	public double getY(){
		
		return y;
	}

	/**
	 * 
	 * @return La composante z du vecteur
	 */
	public double getZ(){
	
	return z;
}
	/**
	 * Definir la composante x du vecteur
	 * @param x
	 */
	public void setX(double x){
		this.x=x;
		
	}
	
	/**
	 * Definir la composante y du vecteur 
	 * @param y
	 */
	public void setY(double y){
		this.y=y;
		
	}
	
	/**
	 * Definir la composante z du vecteur
	 * @param z
	 */
	public void setZ(double z){
		this.z=z;
		
	}
	
	/**
	 * 
	 * @return La norme du vecteur
	 */
	public  double norme(){
		return Math.hypot(getX(),getY());
	}
}
