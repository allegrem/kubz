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
	int oldTap = 0;

	private int id;

    private XBee xBee;

    /* Constructor */
    public Cube(XBee currentXBee){
        this.id = 0;
        this.xBee = currentXBee;
    }

    /**
     * Method to get the actual cube angle
     * @return angle (float)
     */
	public int getAngle() {
		return this.angle;
	}
	
	//probablement pas n�cessaire
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
		boolean b = this.tap;
		this.tap = false;
		return b;
	}

    /**
     * Method to get the ID of the cube
     * @return id (short)
     */
	public int getID() {
		return this.id;

    }
	
	/* Put the value of the tap */
	public void setTap(int tap){
		if (tap != this.oldTap)
			this.tap = true;
		this.oldTap=tap;
	}
	
	/* Change the angle with the value given */
	public void setAngle (int ang){
		this.angle = ang;
	}
	
	/* Change the id with the one given */
	public void setID (int i){
		this.id = i;
	}
	
	/* Switch the RGB leds of the cube with the right colors */
	public void setRGB (int R, int G, int B, short delay) {
		byte[] mess = new byte[6];
		
		/* if the delay is wrong, maximum time */
		if (delay < 0)
			delay = 32767;
		
		mess[0]='L';
		mess[1]=(byte)R;
		mess[2]=(byte)G;
		mess[3]=(byte)B;
		mess[4]=(byte)(delay>>8);
		mess[5]=(byte)delay;
			
		this.xBee.sendTXFrame(mess, this.id);
		this.xBee.sendTXFrame(mess, this.id);
		this.xBee.sendTXFrame(mess, this.id);
		this.xBee.sendTXFrame(mess, this.id);
		this.xBee.sendTXFrame(mess, this.id);
		this.xBee.sendTXFrame(mess, this.id);
		this.xBee.sendTXFrame(mess, this.id);
		this.xBee.sendTXFrame(mess, this.id);
	}

	/* Switch the IR leds with the right pattern */
	public void setIR (byte pattern) {
		byte[] mess = new byte[2];
		
		mess[0]='I';
		mess[1]=pattern;
			
		this.xBee.sendTXFrame(mess, this.id);	
		this.xBee.sendTXFrame(mess, this.id);
		this.xBee.sendTXFrame(mess, this.id);	
		this.xBee.sendTXFrame(mess, this.id);
		this.xBee.sendTXFrame(mess, this.id);	
		this.xBee.sendTXFrame(mess, this.id);
		this.xBee.sendTXFrame(mess, this.id);	
		this.xBee.sendTXFrame(mess, this.id);
	}
	
	public void setIrOn(){
		this.setIR((byte)7);
	}
	
	public void setIrOff(){
		this.setIR((byte)0);
	}

	/* Switch the motor with the given intensity */
	public void setMotor (byte intensity) {
		byte[] mess = new byte[2];
		
		mess[0]='M';
		mess[1]=intensity;
			
		this.xBee.sendTXFrame(mess, this.id);
		this.xBee.sendTXFrame(mess, this.id);
		this.xBee.sendTXFrame(mess, this.id);
		this.xBee.sendTXFrame(mess, this.id);
		this.xBee.sendTXFrame(mess, this.id);
		this.xBee.sendTXFrame(mess, this.id);
		this.xBee.sendTXFrame(mess, this.id);
		this.xBee.sendTXFrame(mess, this.id);
	}

}

