package testMath.video;

/**
 * Test qui donne le pourcentage de fois ou le nombre de taches trouvees est bon
 * @author Felix
 */

import traitementVideo.Traitement;
import cl.eye.GrabberShow;

public class Calibrage2 {
	
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
		double good = 0;
		double iter = 0;
		// boucle d'update 
		while(true){
			iter++;
			traitement.setTraitScreen(gs.getcameraScreen());
			traitement.flouMedian();
			traitement.flouMedian();
			traitement.seuil();
			traitement.updateConnexe();
			if(traitement.getNcomp()==2) good++;
			System.out.println("pourcentage de r�ussite " + 100*good/iter);
		}

	}

}
