package main;

/**
 * @author Felix
 * @author Paul
 * 
 */

import cube.XBee;
import cubeManager.CubeManager;
import OpenGL.GLDisplay;
import gameEngine.GameEngine;

public class Main {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
//		XBee xbee = new XBee(); // Create a new XBee    
//	    CubeManager cubeManager = new CubeManager(xbee); // Create a new cube manager   
//	    xbee.setCubeManager(cubeManager); // Add the cube manager in the XBee    
//	    xbee.start(); // Start the XBee thread
		GameEngine gameEngine=new GameEngine(/*cubeManager*/);
		//Starting2 starting = new Starting2();
		GLDisplay display=gameEngine.getDisplay();
		
		gameEngine.start();
		
		while(display.isAlive()){
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		gameEngine.stop();
		System.exit(0);
	}

}
