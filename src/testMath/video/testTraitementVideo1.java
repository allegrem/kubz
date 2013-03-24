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
//		while(size!=2){
////			traitement.updateConnexe(gs.getcameraScreen());
//			traitement.setTraitScreen(gs.getcameraScreen());
//			traitement.flouMedian();
////			traitement.flouMedian();
//			traitement.seuil();
//			traitement.updateConnexe();
//			size = traitement.getGroupesPos().size()-1;
//			System.out.println("taches boucle: " + size);
//		}
//		System.out.println("Debut du Tracking");
//		VideoCube cube = new VideoCube(traitement.getGroupePos(1),traitement.getGroupePos(2),null);
//		double good = 0;
//		double iter = 0;
		// boucle d'update 
		while(true){
//			iter++;
			traitement.setTraitScreen(gs.getcameraScreen());
			traitement.flouMedian();
//			traitement.flouMedian();
			traitement.seuil();
			traitement.updateConnexe();
			size = traitement.getGroupesPos().size()-1;
			System.out.println("taches: " + size);
			if(size == 2){
//				traitement.localSearch(cube);
//				good++;
//				System.out.println("Posistion du premier point: " + (int) traitement.getGroupePos(1).getX() + " " + (int) traitement.getGroupePos(1).getY());
//				System.out.println("Posistion du premier point: " + (int) traitement.getGroupePos(2).getX() + " " + (int) traitement.getGroupePos(2).getY());
			}
			//traitement.localSearch(cube);
//			System.out.println(good/iter);
		}

	}
	


}
