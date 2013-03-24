package testMath.video;

/**
 * Classe qui sert à calibrer les paramêtres de Traitement en montrant le nombre de composantes connnexes et le nombre de pixels au dessu du seuil
 * @author Felix
 */

import cl.eye.GrabberShow;
import traitementVideo.Traitement;

public class Calibrage1 {

	public static void main(String[] args) {
		Traitement traitement = new Traitement(640, 480);
		traitement.updateConnexe();
		GrabberShow gs = new GrabberShow();
		Thread th = new Thread(gs);
		th.start();
		try {
			Thread.sleep(500);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		// boucle d'update
		while (true) {
			traitement.setTraitScreen(gs.getcameraScreen());
			traitement.flouMedian();
			traitement.flouMedian();
			traitement.seuil();
			traitement.updateConnexe();
			System.out.println("taches: " + traitement.getNcomp());
			System.out.println("pixels: " + traitement.getNseuils());
		}
	}

}