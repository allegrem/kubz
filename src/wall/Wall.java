package wall;

/**
 * Classe qui permet de repr�senter les murs dans le mod�le
 * @author Felix
 */

import utilities.Point;
import utilities.Vector;
import views.staticViews.WallView;

public class Wall {
	private Point extremity1;
	private Point extremity2;
	//private int thickness;                 Ce param�tre est utilis� ?
	private Vector vect; 
	private float normev;
	private double angle;
	private WallView view;
	
	public Wall(Point extremity1, Point extremity2, int thickness) {
		super();
		this.extremity1 = extremity1;
		this.extremity2 = extremity2;
		//this.thickness = thickness;
		this.vect = new Vector(extremity2.getX()-extremity1.getX(),extremity2.getY()-extremity1.getY());
		this.normev = (float) Math.sqrt(vect.getX()*vect.getX()+vect.getY()*vect.getY());
	}
	
	public void ChangeWall(Point extremity1, Point extremity2, int thickness) {
		this.extremity1 = extremity1;
		this.extremity2 = extremity2;
		//this.thickness = thickness;
		view.ChangeWall(extremity1, extremity2, thickness);
	}
	public void translateX(double longueur) {
		extremity1.setX(extremity1.getX() + longueur);
		extremity2.setX(extremity2.getX() + longueur);
		view.translateX(longueur);
	}
	public void translateY(double longueur) {
		extremity1.setY(extremity1.getY() + longueur);
		extremity2.setY(extremity2.getY() + longueur);
		view.translateY(longueur);
	}
	public void rotate(double inc){
		Point centre = new Point(extremity1.getX() + vect.getX() / 2,extremity1.getY() + vect.getY() / 2);
		angle = angle + inc * Math.PI / 180;
		extremity1.setX(normev / 2 * Math.cos(angle + Math.PI) + centre.getX());
		extremity1.setY(normev / 2 * Math.sin(angle + Math.PI) + centre.getY());
		extremity2.setX(normev / 2 * Math.cos(angle) + centre.getX());
		extremity2.setY(normev / 2 * Math.sin(angle) + centre.getY());
		view.rotate(inc);
	}
}
