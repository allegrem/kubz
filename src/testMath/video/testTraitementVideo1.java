package testMath.video;

/**
 * 
 * @author Felix
 */

import cl.eye.GrabberShow;
import traitementVideo.Traitement;
import traitementVideo.VideoCube;
import traitementVideo.VirtualPixel;

public class testTraitementVideo1 {
	
	GrabberShow gs;

	public static void main(String[] args) {
		GrabberShow gs = new GrabberShow();
        Thread th = new Thread(gs);
        th.start();
		Traitement traitement = new Traitement();
		traitement.updateConnexe(gs.getcameraScreen());
		try {
			Thread.sleep(100);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		VideoCube cube = new VideoCube(traitement.getGroupePos(1),traitement.getGroupePos(2),null);
		
		// boucle d'update 
		while(true){
			traitement.updateConnexe(gs.getcameraScreen());
			traitement.localSearch(cube);
		}

	}

}
