package traitementVideo;

/**
 * 
 * @author Felix
 */


import java.util.ArrayList;

import cube.*;
import utilities.Point;

public class VideoCube {
	
	private Point pos1;
	private Point pos2;
	private double x1Speed;
	private double x2Speed;;
	private double y1Speed;
	private double y2Speed;
	private final Cube owner;
	private ArrayList<Point> pos;
	private Point meanPos;
	private double x1;
	private double x2;
	private double y1;
	private double y2;
	
	
	
	
	public VideoCube(Point pos1, Point pos2, /*Point pos3,*/Cube owner) {
		super();
		this.pos1 = pos1;
		this.pos2 = pos2;
		x2 = pos2.getX();
		y2 = pos2.getY();
		x1 = pos1.getX();
		y1 = pos1.getY();
		this.owner = owner;
		x1Speed = 0;
		x2Speed = 0;
		y1Speed = 0;
		y2Speed = 0;
		pos = new  ArrayList<Point>();
		pos.add(this.pos1);
		pos.add(this.pos2);		
		meanPos = new Point((x1+x2)/2, (y1+y2)/2);
	}
	
	public Point getPos1() {
		return pos1;
	}
	public void setPos1(Point pos1) {
		this.pos1 = pos1;
		x1 = pos1.getX();
		y1 = pos1.getY();
	}
	public Point getPos2() {
		return pos2;
	}
	public void setPos2(Point pos2) {
		this.pos2 = pos2;
		x2 = pos2.getX();
		y2 = pos2.getY();
	}
	public double getX1Speed() {
		return x1Speed;
	}
	public void setX1Speed(double x1Speed) {
		this.x1Speed = x1Speed;
	}
	public double getX2Speed() {
		return x2Speed;
	}
	public void setX2Speed(double x2Speed) {
		this.x2Speed = x2Speed;
	}
	public double getY1Speed() {
		return y1Speed;
	}
	public void setY1Speed(double y1Speed) {
		this.y1Speed = y1Speed;
	}
	public double getY2Speed() {
		return y2Speed;
	}
	public void setY2Speed(double y2Speed) {
		this.y2Speed = y2Speed;
	}
	public Cube getOwner(){
		return owner;
	}	
	public ArrayList<Point> getPos(){
		return pos;
	}
	
	public void updateMeanPos(){
		meanPos.move((x1+x2)/2, (y1+y2)/2);
	}
	
	public Point getMeanPos(){
		return meanPos;
	}
	
}
