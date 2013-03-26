package testMath.video;

import traitementVideo.Traitement;
import cl.eye.GrabberShow;

public class Calibrage3 {
	
	public static void main(String[] args) {
		int size;
		GrabberShow gs = new GrabberShow();
        Thread th = new Thread(gs);
        th.start();
		Traitement traitement = new Traitement((gs.Right-gs.Left-1),(gs.Bottom-gs.Top-1));
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
			traitement.seuil();
			traitement.updateConnexe();
			size =  traitement.getNcomp() + 1;
			for(int i=1; i<size; i++){
				if(i!=size-1)
				System.out.print(" tache " + i + " : x=" + (int) traitement.getGroupePos(i).getX() + " , y=" + (int) traitement.getGroupePos(i).getY());
				else System.out.println(" tache " + i + " : x=" + (int) traitement.getGroupePos(i).getX() + " , y=" + (int) traitement.getGroupePos(i).getY());
			}
//			if (traitement.getNcomp()==2){
//				System.out.println("Position du cube : x=" + (traitement.getGroupePos(1).getX()+traitement.getGroupePos(2).getX())/2 + " y=" + (traitement.getGroupePos(1).getY()+traitement.getGroupePos(2).getY())/2 );
//			}
			
		}

	}

}
