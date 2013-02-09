package cube;

//import Xbee library

import cubeManager.CubeManager;

public class XBeeReceiveTest {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
        XBee xbee = new XBee();
        xbee.setCubeManager(new CubeManager());    //À COMPLÉTER
        xbee.start();

    }

}
