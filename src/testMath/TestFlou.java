package testMath;

/**
 * 
 * @author Felix
 */

import traitementVideo.Traitement;
import traitementVideo.VirtualPixel;
import utilities.Point;

public class TestFlou {

	public static void main(String[] args) {
		Traitement traitement = new Traitement();

		test(10, 10, 4, 5, traitement);
		//test(4, 4, 0, 1, traitement);
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
	public static void test(int LENGHT, int HEIGHT, int x, int y,
			Traitement traitement) {
		VirtualPixel[][] testscreen = new VirtualPixel[LENGHT][HEIGHT];
		for (int i = 0; i < LENGHT; i++) {
			for (int j = 0; j < HEIGHT; j++) {
				testscreen[i][j] = new VirtualPixel(false, 0, new Point(i, j),(byte) 0);
			}
		}
		if ((x <= LENGHT) && (y <= HEIGHT)) {
			testscreen[x][y].setIntensite((byte) 100);
			traitement.setTraitScreen(testscreen);
			traitement.flouGaussien(10,10);
			/*
			 * Point pos = traitement.getGroupePos(1); if ((x == (int)
			 * pos.getX()) && (y == (int) pos.getY())) {
			 * System.out.println("le test est réussis"); } else {
			 * System.out.println("le test est raté");
			 * System.out.println(pos.getX() + " au lieu de :" + x);
			 * System.out.println(pos.getY() + " au lieu de :" + y); } } else
			 * System.out.println("les donnees entrées ne sont pas correctes");
			 */
		}
	}
}
