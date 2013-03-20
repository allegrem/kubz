package testMath.video;

/**
 * 
 * @author Felix
 */

import java.lang.annotation.Target;

import cl.eye.GrabberShow;
import traitementVideo.Traitement;
import traitementVideo.VideoCube;
import traitementVideo.VirtualPixel;

public class testTraitementVideo1 {
	

	public static void main(String[] args) {
		int iter = 0;
		Traitement traitement = new Traitement();
		GrabberShow gs = new GrabberShow(traitement);
        Thread th = new Thread(gs);
        th.start();
		traitement.updateConnexe(gs.getcameraScreen());
		try {
			Thread.sleep(100);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		//VideoCube cube = new VideoCube(traitement.getGroupePos(1),traitement.getGroupePos(2),null);
		
		// boucle d'update 
		while(true){
			traitement.setTraitScreen(gs.getcameraScreen());
			traitement.flouGaussien();
			traitement.seuil();
			traitement.updateConnexe();
//			System.out.println(iter);
//			iter++;
			//System.out.println(traitement.getGroupesPos().size());
			//traitement.localSearch(cube);
		}

	}

}
