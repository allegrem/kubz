package testMath;

/**
 * 
 * @author Felix
 */

import traitementVideo.Traitement;
import traitementVideo.VideoCube;
import traitementVideo.VirtualPixel;

public class testTraitementVideo1 {

	public static void main(String[] args) {
		int HEIGHT = 480;
		int LENGHT = 480;
		Traitement traitement = new Traitement();
		VirtualPixel[][] testscreen = new VirtualPixel[LENGHT][HEIGHT];
		traitement.updateConnexe(testscreen);
		VideoCube cube = new VideoCube(traitement.getGroupePos(1),traitement.getGroupePos(2),null);
		
		// boucle d'update 
		while(true){
			traitement.updateConnexe(testscreen);
			traitement.localSearch(cube);
		}

	}

}
