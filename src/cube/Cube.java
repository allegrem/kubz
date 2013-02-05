package cube;

import utilities.Point;

public class Cube {	
	private short angle;
	private boolean tap;
	private byte R;
	private byte G;
	private byte B;
	private boolean[] irLed = new boolean[3];
	private byte patern;
	private short id;
	
	
	
	public void update(){
		// a remplir par GueDau
	}
	
	
	
	public short getAngle() {
		return angle;
	}
	public boolean isTap() {
		return tap;
	}
	public byte getR() {
		return R;
	}
	public byte getG() {
		return G;
	}
	public byte getB() {
		return B;
	}
	public boolean[] getIrLed() {
		return irLed;
	}
	public byte getPatern() {
		return patern;
	}		
}
