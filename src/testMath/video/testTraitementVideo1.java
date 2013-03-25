package testMath.video;

/**
 * 
 * @author Felix
 */

import cl.eye.GrabberShow;
import traitementVideo.Traitement;
import traitementVideo.VideoCube;


public class testTraitementVideo1 {
	

	public static void main(String[] args) {
		Traitement traitement = new Traitement(640,480);
		traitement.updateConnexe();
		GrabberShow gs = new GrabberShow();
        Thread th = new Thread(gs);
        th.start();
        try {
			Thread.sleep(500);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		int size = 0;
		while(size!=2){
			traitement.setTraitScreen(gs.getcameraScreen());
			traitement.flouMedian();
			traitement.flouMedian();
			traitement.flouMoyen();
			traitement.seuil();
			traitement.updateConnexe();
			size = traitement.getGroupesPos().size()-1;
		}
		System.out.println("Debut du Tracking");
		VideoCube cube = new VideoCube(traitement.getGroupePos(1),traitement.getGroupePos(2),null);
		// boucle d'update 
		while(true){
			traitement.setTraitScreen(gs.getcameraScreen());
			traitement.flouMedian();
			traitement.flouMedian();
			traitement.flouMoyen();
			traitement.seuil();
			traitement.updateConnexe();
			size = traitement.getGroupesPos().size()-1;
//			System.out.println("taches: " + size);
			if(size == 2){
				traitement.localSearch(cube);
			}
		}
	}
}