package cube;

import utilities.Point;

public class Cube {	
	private float angle;
	private boolean tap;
	private int R;
	private int G;
	private int B;
	private boolean[] irLed = new boolean[3];
	private int patern;
	private float id;
	
	
	
	public void update(){
		// a remplir par GuéDau
	}
	
	
	
	public float getAngle() {
		return angle;
	}
	public boolean isTap() {
		return tap;
	}
	public int getR() {
		return R;
	}
	public int getG() {
		return G;
	}
	public int getB() {
		return B;
	}
	public boolean[] getIrLed() {
		return irLed;
	}
	public int getPatern() {
		return patern;
	}		
}
