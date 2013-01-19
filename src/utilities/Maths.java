package utilities;

public class Maths {

	public static Vector vect(Vector vect1, Vector vect2){
		Vector vect3 = new Vector(0,0,0);
		vect3.setX(vect1.getY()*vect2.getZ()-vect1.getZ()*vect2.getY());
		vect3.setY(vect1.getZ()*vect2.getX()-vect1.getX()*vect2.getZ());
		vect3.setZ(vect1.getX()*vect2.getY()-vect1.getY()*vect2.getX());
		return vect3;
	}
	
	public static Vector makeNormalizedVector(double x1, double y1, double z1, double x2, double y2, double z2){
		Vector vect=new Vector(0,0,0);
		Double norme=Math.sqrt((x2-x1)*(x2-x1)+(y2-y1)*(y2-y1)+(z2-z1)*(z2-z1));
		vect.setX((x2-x1)/norme);
		vect.setY((y2-y1)/norme);
		vect.setZ((z2-z1)/norme);
		return vect;
	
	}
	
	public static Vector makeVector(double x1, double y1, double z1, double x2, double y2, double z2){
		Vector vect=new Vector(0,0,0);
		vect.setX((x2-x1));
		vect.setY((y2-y1));
		vect.setZ((z2-z1));
		return vect;
	
	}

}
