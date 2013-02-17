package testMath;

import cube.Cube;
import cube.XBee;
import cubeManager.CubeManager;

public class TestRot {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		XBee xbee = new XBee(); // Create a new XBee
		CubeManager cubeManager = new CubeManager(); // Create a new cube
														// manager		
		int adress1 = 45679;
		Cube cube1 = new Cube(xbee);
		cube1.setID(45679);
		cubeManager.addFreeCube(cube1);
		

		xbee.setCubeManager(cubeManager); // Add the cube manager in the XBee	        
	    xbee.start(); // Start the XBee thread
		
		while(true){
			System.out.println(cubeManager.getCube(adress1).getAngle());
			try {
				Thread.sleep(10);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}

	}

}
