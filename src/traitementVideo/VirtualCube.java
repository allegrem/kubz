package traitementVideo;

import cube.*;
import utilities.Point;

public class VirtualCube {
	
	private Point pos1;
	private Point pos2;
	private Point pos3;
	private double x1Speed;
	private double x2Speed;
	private double x3Speed;
	private double y1Speed;
	private double y2Speed;
	private double y3Speed;
	private final Cube owner;
	
	
	
	
	
	public VirtualCube(Point pos1, Point pos2, Point pos3, Cube owner) {
		super();
		this.pos1 = pos1;
		this.pos2 = pos2;
		this.pos3 = pos3;
		this.owner = owner;
		x1Speed = 0;
		x2Speed = 0;
		x3Speed = 0;
		y1Speed = 0;
		y2Speed = 0;
		y3Speed = 0;
		
	}
	
	public Point getPos1() {
		return pos1;
	}
	public void setPos1(Point pos1) {
		this.pos1 = pos1;
	}
	public Point getPos2() {
		return pos2;
	}
	public void setPos2(Point pos2) {
		this.pos2 = pos2;
	}
	public Point getPos3() {
		return pos3;
	}
	public void setPos3(Point pos3) {
		this.pos3 = pos3;
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
	public double getX3Speed() {
		return x3Speed;
	}
	public void setX3Speed(double x3Speed) {
		this.x3Speed = x3Speed;
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
	public double getY3Speed() {
		return y3Speed;
	}
	public void setY3Speed(double y3Speed) {
		this.y3Speed = y3Speed;
	}

	public Cube getOwner(){
		return owner;
	}
	
}
