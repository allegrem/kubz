package utilities;

/**
 * Differents outils mathematiques en complement de
 * la classe Math de Java
 * @author paul
 *
 */
public class Maths {

	/**
	 * Calcul d'un produit vectoriel
	 * @param vect1 Premier vecteur
	 * @param vect2 Second vecteur
	 * @return
	 */
	public static Vector vect(Vector vect1, Vector vect2){  //compute cross product
		Vector vect3 = new Vector(0,0,0);
		vect3.setX(vect1.getY()*vect2.getZ()-vect1.getZ()*vect2.getY());
		vect3.setY(vect1.getZ()*vect2.getX()-vect1.getX()*vect2.getZ());
		vect3.setZ(vect1.getX()*vect2.getY()-vect1.getY()*vect2.getX());
		return vect3;
	}
	
/**
 * Création d'un vecteur normé à partir des 
 * coordonnees de ses deux extremites
 * @param x1 coordonnee x de la premiere extremite
 * @param y1 coordonnee y de la premiere extremite
 * @param z1 coordonnee z de la premiere extremite
 * @param x2 coordonnee x de la seconde extremite
 * @param y2 coordonnee y de la seconde extremite
 * @param z2 coordonnee z de la seconde extremite
 * @return Le vecteur norme
 */
	public static Vector makeNormalizedVector(double x1, double y1, double z1, double x2, double y2, double z2){

		Vector vect=new Vector(0,0,0);
		Double norme=Math.sqrt((x2-x1)*(x2-x1)+(y2-y1)*(y2-y1)+(z2-z1)*(z2-z1));
		vect.setX((x2-x1)/norme);
		vect.setY((y2-y1)/norme);
		vect.setZ((z2-z1)/norme);
		return vect;
	
	}
	
	/**
	 * Création d'un vecteur à partir des 
	 * coordonnees de ses deux extremites
	 * @param x1 coordonnee x de la premiere extremite
	 * @param y1 coordonnee y de la premiere extremite
	 * @param z1 coordonnee z de la premiere extremite
	 * @param x2 coordonnee x de la seconde extremite
	 * @param y2 coordonnee y de la seconde extremite
	 * @param z2 coordonnee z de la seconde extremite
	 * @return Le vecteur
	 */
	public static Vector makeVector(double x1, double y1, double z1, double x2, double y2, double z2){
		Vector vect=new Vector(0,0,0);
		vect.setX((x2-x1));
		vect.setY((y2-y1));
		vect.setZ((z2-z1));
		return vect;
	
	}
	
	/**
	 * Retourne un vecteur a partir de ses 2 extremites
	 * @param p1
	 * @param p2
	 * @return
	 */
	public static Vector makeVector(Point p1, Point p2){
		Vector vect=new Vector(0,0,0);
		vect.setX(p2.getX()-p1.getX());
		vect.setY(p2.getY()-p1.getY());
		return vect;
	}

	/**
	 * Retourne un vecteur normal a un autre vector
	 * @param vector
	 * @return
	 */
	public static Vector getNormalvector(Vector vector) {
		Vector vect= new Vector(0,0,0);
		vect.setX(1);
		vect.setY(-vector.getX()/vector.getY());
		normalize(vect);
		return vect;
	}
	
	/**
	 * Normalise un vecteur
	 * @param vect
	 * @return
	 */
	public static void normalize(Vector vect){
		double norme=vect.norme();
		vect.setX(vect.getX()/norme);
		vect.setY(vect.getY()/norme);
		vect.setZ(vect.getZ()/norme);
		
	}

	/**
	 * Change le sens du vector
	 * @param normalvector
	 * @return
	 */
	public static Vector opposite(Vector vect) {
		Vector vector =new Vector(0,0,0);
		vector.setX(-vect.getX());
		vector.setY(-vect.getY());
		vector.setZ(-vect.getZ());
		return vector;
	}

}
