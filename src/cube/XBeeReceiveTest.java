package cube;

import java.util.ArrayList;

import cubeManager.CubeManager;

public class XBeeReceiveTest {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
        XBee xbee = new XBee(); // Create a new XBee
        
        CubeManager cubeManager = new CubeManager(xbee); // Create a new cube manager
    
        xbee.setCubeManager(cubeManager); // Add the cube manager in the XBee
        
        xbee.start(); // Start the XBee thread
    }

}
