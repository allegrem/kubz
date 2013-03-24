package testMath.video;

import traitementVideo.Traitement;
import cl.eye.GrabberShow;

public class Calibrage3 {
	
	public static void main(String[] args) {
		int size;
		Traitement traitement = new Traitement(640,480);
		GrabberShow gs = new GrabberShow();
        Thread th = new Thread(gs);
        th.start();
        try {
			Thread.sleep(500);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		// boucle d'update 
		while(true){
			traitement.setTraitScreen(gs.getcameraScreen());
			traitement.flouMedian();
			traitement.flouMedian();
//			traitement.flouGaussien();
			traitement.seuil();
			traitement.updateConnexe();
			size =  traitement.getNcomp() + 1;
			for(int i=1; i<size; i++){
				if(i!=size-1)
				System.out.print(" tache " + i + " : x=" + (int) traitement.getGroupePos(i).getX() + " , y=" + (int) traitement.getGroupePos(i).getY());
				else System.out.println(" tache " + i + " : x=" + (int) traitement.getGroupePos(i).getX() + " , y=" + (int) traitement.getGroupePos(i).getY());
			}
		}

	}

}
