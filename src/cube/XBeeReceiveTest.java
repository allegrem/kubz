package cube;

import cubeManager.CubeManager;

public class XBeeReceiveTest {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
        XBee xbee = new XBee(); // Create a new XBee
        
        CubeManager cubeManager = new CubeManager(); // Create a new cube manager
        
        Cube cube1 = new Cube(xbee);
        cube1.setID(45679);
        cubeManager.addFreeCube(cube1); // Add the first cube to the cube manager
        
        /*Cube cube2 = new Cube(xbee);
        cube2.setID(45675);
        cubeManager.addFreeCube(cube2); // Add the second cube to the cube manager
        
        Cube cube3 = new Cube(xbee);
        cube3.setID(45671);
        cubeManager.addFreeCube(cube3);*/ // Add the second cube to the cube manager
        
        xbee.setCubeManager(cubeManager); // Add the cube manager in the XBee
        
        xbee.start(); // Start the XBee thread
    }

}
