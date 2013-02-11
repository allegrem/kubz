package cube;

import java.lang.Short;
import java.util.concurrent.locks.ReentrantLock;

public class Cube{

	/**
	 * Attribute of cube
	 */
	private int angle;
	private boolean tap;

	private int id;

    private XBee xBee;

    public Cube(XBee currentXBee){
        this.id = 0;
        this.xBee = currentXBee;
    }

	/**
	 * Method to set the RGB color of the LEDS
	 * @param : The 3 short that control R,G and B color, and the short for the delay.
	 */
	public void setRGB(byte R, byte G, byte B, short delay){
		String message = "L" + R + G + B + delay; 
		// XXX TODO :xBee.sendRXFrame(message);		  
	}

    /**
     * Method to switch on or off the IR LEDS using the pattern given
     * @param : byte, the 3 last bit determined witch LEDS are switch on.
     */
	private void setIR(byte pattern){
		String message = "I" + pattern;
		//XXX TODO : xBee.sendRXFrame(message);
    }
	

	public void setIrOn(){
        /* Type byte is signed */
		setIR((byte)(-121));
	}
	public void setIrOf(){
        /* Type byte is signed */
		setIR((byte)(-128));
	}

    /**
     * Method to switch on the Motor using the pattern given
     * @param : byte
     */
	public void setMotor(byte pattern) {
         String message = "M" + pattern;
         // XXX TODO : xBee.sendRXFrame(message);
	}

    /**
     * Method to get the actual cube angle
     * @return angle (float)
     */
	public int getAngle() {
		return this.angle;
	}

    /**
     * Method to get the actual state of the tap
     * @return tap (boolean)
     */
	public boolean getTap() {
		return this.tap;
	}

    /**
     * Method to get the ID of the cube
     * @return id (short)
     */
	public int getID() {
		return this.id;

    }
	
	public void setAngle (int ang){
		this.angle = ang;
	}
	
	public void setID (int i){
		this.id = i;
	}

}

