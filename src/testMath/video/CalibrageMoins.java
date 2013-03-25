package testMath.video;

import traitementVideo.Traitement;
import cl.eye.GrabberShow;

public class CalibrageMoins {
	
	public static void main(String[] args) {
		GrabberShow gs = new GrabberShow();
        Thread th = new Thread(gs);
        th.start();
		Traitement traitement = new Traitement((gs.Right-gs.Left-1),(gs.Bottom-gs.Top-1));
        try {
			Thread.sleep(500);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		double moins = 0;
		double iter = 0;
		// boucle d'update 
		while(true){
			iter++;
			traitement.setTraitScreen(gs.getcameraScreen());
			traitement.flouMedian();
			traitement.flouMedian();
			traitement.seuil();
			traitement.updateConnexe();
			if(traitement.getNcomp()<2) moins++;
			System.out.println("pourcentage où moins " + 100*moins/iter);
		}
	}

}
