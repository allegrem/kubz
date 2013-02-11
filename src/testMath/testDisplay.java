package testMath;

import org.lwjgl.LWJGLException;
import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;

public class testDisplay {

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
		while (true){
			Display.update();
			System.out.println(Keyboard.isKeyDown(Keyboard.KEY_A));
			try {
				Thread.sleep(10);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	

	}
	
	private boolean aPressed(){
		Display.update();
		return Keyboard.isKeyDown(Keyboard.KEY_A);
	}

}
