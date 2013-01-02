package map2;

import org.lwjgl.util.ReadablePoint;
/**
 * Objet Point représentant un point en 2D
 * par ses coordonnées x et y
 * 
 * @author paul
 *
 */
public class Point {
	private double x;
	private double y;
	
	public Point(double x, double y){
		this.x=x;
		this.y=y;
		
	}

	public double getX(){
		
		return x;
	}
	
	public double getY(){
		
		return y;
	}
	
	public void move(double x,double y){
		this.x=x;
		this.y=y;
	}
	
	public void translate(double dx, double dy){
		x+=dx;
		y+=dy;
		
	}
	
	public void setLocation(Point p){
		x=p.getX();
		y=p.getY();
		
	}
	
	public void setX(double x){
		this.x=x;
		
	}
	
	public void setY(double y){
		this.y=y;
		
	}
}
