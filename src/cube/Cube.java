package cube;

import java.lang.Short;
import java.util.concurrent.locks.ReentrantLock;

public class Cube{

	/**
	 * Attribute of cube
	 */
	private short angle;
	private boolean tap;

	private char id;

    private XBee xBee;

    ReentrantLock mutex = new ReentrantLock(true);
    String RString, GString, BString, irPatternString, motorPatternString;

    public Cube(XBee currentXBee){
        this.id = 0;
        this.xBee = currentXBee;
    }

	/**
	 * Method to set the RGB color of the LEDS
	 * @param : The 3 short that control R,G and B color.
	 */
	public void setRGB(short R, short G, short B){
        mutex.lock();

        try {
            RString = ((Short)R).toString();
            GString = ((Short)G).toString();
            BString = ((Short)B).toString();
            xBee.setDataSend("L" + RString + GString + BString + "\n");
        } finally {
            mutex.unlock();
        }
	}

    /**
     * Method to switch on or off the IR LEDS using the pattern given
     * @param : byte, the 3 last bit determined witch LEDS are switch on.
     */
	private void setIR(Byte pattern){
        mutex.lock();

        try {
            irPatternString = pattern.toString();
            xBee.setDataSend("I" + irPatternString + "\n");
        } finally {
            mutex.unlock();
        }
    }
	

	public void setIrOn(){
		setIR((byte)7);
	}
	public void setIrOf(){
		setIR((byte)0);
	}

    /**
     * Method to switch on the Motor using the pattern given
     * @param : byte
     */
	public void setMotor(byte pattern) {
        mutex.lock();

        try {
            motorPatternString = ((Byte)pattern).toString();
            xBee.setDataSend("M" + motorPatternString + "\n");
        } finally {
            mutex.unlock();
        }
	}

    /**
     * Method to get the actual cube angle
     * @return angle (float)
     */
	public float getAngle() {
		return angle;
	}

    /**
     * Method to get the actual state of the tap
     * @return tap (boolean)
     */
	public boolean getTap() {
		return tap;
	}

    /**
     * Method to get the ID of the cube
     * @return id (short)
     */
	public char getID() {
		return id;

    }

}

