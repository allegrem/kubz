package cube;

import com.rapplogic.xbee.api.XBeeException;

//import Xbee library
import cube.XBeeReceive;

public class XBeeReceiveTest {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		try {
			XBeeReceive xbee = new XBeeReceive();
		} catch (XBeeException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

}
