package videoprocessing;

import traitementVideo.Traitement;
import cl.eye.GrabberShow;

public class MainTraitementImage {
	   public static void main(String[] args) {
		   System.out.println("Starting test grabber");
	        GrabberShow gs = new GrabberShow(new Traitement());
	        Thread th = new Thread(gs);
	        th.start();
	    }
}
