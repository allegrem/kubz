package testMath.video;

import traitementVideo.Traitement;
import cl.eye.GrabberShow;

public class CalibragePlus {
	
	public static void main(String[] args) {
		Traitement traitement = new Traitement(640,480);
		GrabberShow gs = new GrabberShow();
        Thread th = new Thread(gs);
        th.start();
        try {
			Thread.sleep(500);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		double trop = 0;
		double iter = 0;
		// boucle d'update 
		while(true){
			iter++;
			traitement.setTraitScreen(gs.getcameraScreen());
			traitement.flouMedian();
			traitement.flouMedian();
			traitement.seuil();
			traitement.updateConnexe();
			if(traitement.getNcomp()>2) trop++;
			System.out.println("pourcentage où trop " + 100*trop/iter);
		}
	}

}
