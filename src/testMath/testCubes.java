package testMath;

import org.lwjgl.LWJGLException;
import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;

import synthesis.Sound;
import synthesis.fmInstruments.FmInstruments3Params;
import synthesis.fmInstruments.TwoOscFmInstrument;
import synthesis.fmInstruments.WoodInstrument;
import cube.Cube;
import cube.XBee;
import cubeManager.CubeManager;

public class testCubes {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		try {
			Display.setDisplayModeAndFullscreen(new DisplayMode(400,400));
			Display.create();
		} catch (LWJGLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		XBee xbee = new XBee(); // Create a new XBee
		CubeManager cubeManager = new CubeManager(); // Create a new cube
														// manager
		int adress1 = 45679;
		Cube cube1 = new Cube(xbee);
		cube1.setID(45679);
		cubeManager.addFreeCube(cube1); // Add the first cube to the cube
										// manager
		int adress2 = 45675;
		Cube cube2 = new Cube(xbee);
		cube2.setID(45675);
		cubeManager.addFreeCube(cube2); // Add the second cube to the cube
										// manager
		xbee.setCubeManager(cubeManager); // Add the cube manager in the XBee
		xbee.start(); // Start the XBee thread
		Sound sound = new Sound(WoodInstrument.getFmInstruments3Params(),
				3f);

		while (true) {
			if (aPressed()){
				FmInstruments3Params instrument = (FmInstruments3Params) sound
						.getInstrument();
				instrument.changeParams(cubeManager.getCube(adress1)
						.getAngleChange(), cubeManager.getCube(adress2)
						.getAngleChange(), 2);
				sound.playToSpeakers();
				try {
					Thread.sleep(3000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				
			}
			try {
				Thread.sleep(10);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

	private static boolean aPressed(){
		Display.update();
		return Keyboard.isKeyDown(Keyboard.KEY_A);
}

}
