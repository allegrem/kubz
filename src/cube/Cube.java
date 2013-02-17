package cube;

import java.lang.Short;
import java.util.concurrent.locks.ReentrantLock;

/* XXX TODO : Add mutex to the communication */

public class Cube{

	/**
	 * Attribute of cube
	 */
	private int angle;
	private boolean tap;
	int lastAngle;

	private int id;

    private XBee xBee;

    /* Constructor */
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
	
	/* Switch all the ir leds on */
	public void setIrOn(){
        /* Type byte is signed */
		setIR((byte)(-121));
	}
	
	/* Switch all the ir leds off */
	public void setIrOff(){
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
	
	public int getAngleChange(){
		int retour = lastAngle - angle;
		lastAngle = angle;
		return retour;
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
	
	/* Change the angle with the value given */
	public void setAngle (int ang){
		this.angle = ang;
	}
	
	/* Change the id with the one given */
	public void setID (int i){
		this.id = i;
	}

}

