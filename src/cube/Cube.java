package cube;

import utilities.Point;

public class Cube {
	private Point pos;
	private double XSpeed;
	private double YSpeed;
	
	
	public Cube(Point pos) {
		super();
		this.pos = pos;
		XSpeed = 0;
		YSpeed = 0;
	}
	
	
	public Point getPos() {
		return pos;
	}
	public void setPos(Point pos) {
		this.pos = pos;
	}
	public double getXSpeed() {
		return XSpeed;
	}
	public void setXSpeed(double xSpeed) {
		XSpeed = xSpeed;
	}
	public double getYSpeed() {
		return YSpeed;
	}
	public void setYSpeed(double ySpeed) {
		YSpeed = ySpeed;
	}
	
	

}
