package testMath;

import traitementVideo.Traitement;
import traitementVideo.VirtualPixel;

public class testTraitement4 {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}
	
	private static void createTache(int LENGHT, int HEIGHT, int x, int y,
			Traitement traitement, VirtualPixel[][] screen){
		screen[x][y].setBrightness(true);
		screen[x + 1][y].setBrightness(true);
		screen[x - 1][y].setBrightness(true);
		screen[x][y + 1].setBrightness(true);
		screen[x][y - 1].setBrightness(true);
	}

}
