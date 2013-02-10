package cube;

import cubeManager.CubeManager;

public class XBeeReceiveTest {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
        XBee xbee = new XBee();
        CubeManager cubeManager = new CubeManager();
        Cube cube = new Cube(xbee);
        cube.setID(45679);
        cubeManager.addFreeCube(cube);
        xbee.setCubeManager(cubeManager);
        xbee.start();

    }

}
