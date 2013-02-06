package cube;

import utilities.Point;
//import Xbee library
import com.rapplogic.xbee.api.XBee;
import com.rapplogic.xbee.api.XBeeAddress64;
import com.rapplogic.xbee.api.XBeeException;
import com.rapplogic.xbee.api.zigbee.ZNetTxRequest;
import com.rapplogic.xbee.util.ByteUtils;


public class Cube {	
	/**
	 * Attribute of cube
	 */
	private short angle;
	private boolean tap;
	private short id;
	
	/**
	 * Method to set the RGB color of the LEDS
	 * @parameters : chart of 
	 */
	public void setRGB(){
		
	}
	
	public void setIR() {
		
	}
	
	public void setMotor() {
		
	}
	
	public short getAngle() {
		return angle;
	}
	
	public boolean getTap() {
		return tap;
	}
	
	public short getID() {
		return id;
	}
}
