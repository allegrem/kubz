package testMath.video;

/**
 * 
 * @author Felix
 */

import cl.eye.GrabberShow;
import traitementVideo.Traitement;


public class testTraitementVideo1 {
	

	public static void main(String[] args) {
		Traitement traitement = new Traitement(640,480);
		traitement.updateConnexe();
		GrabberShow gs = new GrabberShow();
        Thread th = new Thread(gs);
        th.start();
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		traitement.updateConnexe(gs.getcameraScreen());
		//VideoCube cube = new VideoCube(traitement.getGroupePos(1),traitement.getGroupePos(2),null);
		
		// boucle d'update 
		while(true){
//			traitement.setTraitScreen(gs.getcameraScreen());
//			traitement.flouMedian();
//			traitement.seuil();
//			traitement.updateConnexe();
//			System.out.println("taches: " + (traitement.getGroupesPos().size()-1));
			gs.getByteScreen();
			try {
				Thread.sleep(100);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			//traitement.localSearch(cube);
		}

	}

}
