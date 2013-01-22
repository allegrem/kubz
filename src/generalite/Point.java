package generalite;

public class Point {
	
	private int x;
	private int y;
	private double xd;
	private double yd;
	
		
	public Point(int x, int y) {
		
		this.x = x;
		this.y = y;
	}
	
	public int getX() {
		return (int) xd;
	}
	public int getY() {
		return (int) yd;
	}
	public void setX(int x) {
		this.x = x;
	}
	public void setY(int y) {
		this.y = y;
	}
	
	public void move(double dx, double dy){
		xd = xd + dx;
		yd = yd + dy;		
	}
	
	
	
}
