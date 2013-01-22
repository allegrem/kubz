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

}
