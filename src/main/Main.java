package main;

import OpenGL.GLDisplay;
import gameEngine.GameEngine;

public class Main {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		GameEngine gameEngine=new GameEngine();
		GLDisplay display=gameEngine.getDisplay();
		gameEngine.start();p
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
