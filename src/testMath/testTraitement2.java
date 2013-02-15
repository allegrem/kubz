package testMath;

/**
 * Test à lancer pour vérifier que le repérage des groupes connexes se fait bien sur deux points l'un a cote de l'autre
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
		// test(4, 4, 0, 1, traitement);
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
				testscreen[i][j] = new VirtualPixel(false, 0, new Point(i, j));
			}
		}
		if ((x1 <= LENGHT - 1) && (y1 <= HEIGHT)) {
			testscreen[x1][y1] = new VirtualPixel(true, 0, new Point(x1, y1));
			testscreen[x1 + 1][y1] = new VirtualPixel(true, 0, new Point(
					x1 + 1, y1));
			traitement.updateConnexe(testscreen, LENGHT, HEIGHT);
			Point pos1 = traitement.getGroupePos(1);
			Point pos2 = traitement.getGroupePos(2);
			/*
			 * System.out.println(pos1.getX() + " et " + (((double)x1)+0.5));
			 * System.out.println(pos1.getY() + " et " + y1);
			 */

		} else
			System.out.println("les donnees entrées ne sont pas correctes");
	}
}
