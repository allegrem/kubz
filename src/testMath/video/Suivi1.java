package testMath.video;

import traitementVideo.Traitement;
import traitementVideo.VideoCube;
import cl.eye.GrabberShow;

public class Suivi1 {
	
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
			traitement.seuil();
			traitement.updateConnexe();
			size = traitement.getNcomp();
		}
		System.out.println("Creation du nouveau cube");
		VideoCube cube = new VideoCube(traitement.getGroupePos(1),traitement.getGroupePos(2),null);
		System.out.println("Debut du Tracking");
		// boucle d'update 
		while(true){
			traitement.setTraitScreen(gs.getcameraScreen());
			traitement.flouMedian();
			traitement.flouMedian();
			traitement.seuil();
			traitement.updateConnexe();
			size = traitement.getNcomp();
			if(size == 2){
				traitement.localSearch(cube);
			}
		}
	}

}
