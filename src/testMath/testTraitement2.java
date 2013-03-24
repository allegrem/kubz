package testMath;

/**
 * Test à lancer pour vérifier que le repérage des groupes connexes se fait bien sur deux points l'un a cote de l'autre
 * @author Felix
 */

import traitementVideo.Traitement;
import traitementVideo.VirtualPixel;
import utilities.Point;

public class testTraitement2 {

	/**
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		Traitement traitement = new Traitement();

		testCoteACote(10, 10, 4, 5, traitement);
		testDessusDessous(10, 10, 4, 5, traitement);
	}

	/**
	 * Permet de creer un tableau de VirtualPixel de taille LENGHT*HEIGHT, met
	 * un pixel allumé a la position (x,y) Elle permet de verifier que avec un
	 * pixel allume on tombe bien sur un seul et unique groupe a la bonne
	 * position
	 * 
	 * @param LENGHT
	 * @param HEIGHT
	 * @param x
	 * @param y
	 * @param traitement
	 */
	public static void testCoteACote(int LENGHT, int HEIGHT, int x1, int y1,
			Traitement traitement) {
		VirtualPixel[][] testscreen = new VirtualPixel[LENGHT][HEIGHT];
		for (int i = 0; i < LENGHT; i++) {
			for (int j = 0; j < HEIGHT; j++) {
				testscreen[i][j] = new VirtualPixel(false, 0, new Point(i, j),(byte) 0);
			}
		}
		if ((x1 <= LENGHT - 1) && (y1 <= HEIGHT)) {
			testscreen[x1][y1] = new VirtualPixel(true, 0, new Point(x1, y1),(byte) 0);
			testscreen[x1 + 1][y1] = new VirtualPixel(true, 0, new Point(x1 + 1, y1),(byte) 0);
			traitement.updateConnexe(testscreen, LENGHT, HEIGHT);
			Point pos1 = traitement.getGroupePos(1);
			if((pos1.getX()==(double)(x1+0.5))&&(pos1.getY()==(double)y1)){
				System.out.println("le test est reussis");
			}
			else System.out.println("le test est rate");

		} else
			System.out.println("les donnees entrées ne sont pas correctes");
	}
	
	
	/**
	 * Test qui cree un tableau avec deux pixels allumes l'un au dessus de l'autre
	 * @param LENGHT
	 * @param HEIGHT
	 * @param x1
	 * @param y1
	 * @param traitement
	 */
	public static void testDessusDessous(int LENGHT, int HEIGHT, int x1, int y1,
			Traitement traitement) {
		VirtualPixel[][] testscreen = new VirtualPixel[LENGHT][HEIGHT];
		for (int i = 0; i < LENGHT; i++) {
			for (int j = 0; j < HEIGHT; j++) {
				testscreen[i][j] = new VirtualPixel(false, 0, new Point(i, j),(byte) 0);
			}
		}
		if ((x1 <= LENGHT) && (y1 <= HEIGHT - 1)) {
			testscreen[x1][y1] = new VirtualPixel(true, 0, new Point(x1, y1),(byte) 0);
			testscreen[x1][y1 + 1] = new VirtualPixel(true, 0, new Point(x1, y1 + 1),(byte) 0);
			traitement.updateConnexe(testscreen, LENGHT, HEIGHT);
			Point pos1 = traitement.getGroupePos(1);
			if((pos1.getX()==(double)(x1))&&(pos1.getY()==(double)(y1+0.5))){
				System.out.println("le test est reussis");
			}
			else System.out.println("le test est rate");

		} else
			System.out.println("les donnees entrées ne sont pas correctes");
	}
}
