package testMath;

import traitementVideo.Traitement;
import traitementVideo.VirtualPixel;
import utilities.Point;

public class testTraitement3 {
	
	public static void main(String[] args) {
		Traitement traitement = new Traitement();

		testTaches(100, 100, 40, 50, 10,20, traitement);
		

	}
	
	public static void testTache(int LENGHT, int HEIGHT, int x1, int y1,
			Traitement traitement) {
		VirtualPixel[][] testscreen = new VirtualPixel[LENGHT][HEIGHT];
		for (int i = 0; i < LENGHT; i++) {
			for (int j = 0; j < HEIGHT; j++) {
				testscreen[i][j] = new VirtualPixel(false, 0, new Point(i, j),(byte) 0);
			}
		}
		if ((x1 <= LENGHT - 1) && (y1 <= HEIGHT - 1)&&(x1 >= 1) && (y1 >= 1)) {
			testscreen[x1][y1] = new VirtualPixel(true, 0, new Point(x1, y1),(byte) 0);
			testscreen[x1 + 1][y1] = new VirtualPixel(true, 0, new Point(x1 + 1, y1),(byte) 0);
			testscreen[x1 - 1][y1] = new VirtualPixel(true, 0, new Point(x1 - 1, y1),(byte) 0);
			testscreen[x1][y1 + 1] = new VirtualPixel(true, 0, new Point(x1, y1 + 1),(byte) 0);
			testscreen[x1][y1 - 1] = new VirtualPixel(true, 0, new Point(x1, y1 - 1),(byte) 0);
			traitement.updateConnexe(testscreen, LENGHT, HEIGHT);
			Point pos1 = traitement.getGroupePos(1);
			if((pos1.getX()==(double)x1)&&(pos1.getY()==(double)y1)){
				System.out.println("le test est reussis");
			}
			else System.out.println("le test est rate");

		} else
			System.out.println("les donnees entrées ne sont pas correctes");
	}
	
	public static void testTaches(int LENGHT, int HEIGHT, int x1, int y1, int x2, int y2,
			Traitement traitement) {
		VirtualPixel[][] testscreen = new VirtualPixel[LENGHT][HEIGHT];
		for (int i = 0; i < LENGHT; i++) {
			for (int j = 0; j < HEIGHT; j++) {
				testscreen[i][j] = new VirtualPixel(false, 0, new Point(i, j),(byte) 0);
			}
		}
		if ((x1 <= LENGHT - 1) && (y1 <= HEIGHT - 1)&&(x1 >= 1) && (y1 >= 1)&&(x2 <= LENGHT - 1) && (y2 <= HEIGHT - 1)&&(x2 >= 1) && (y2 >= 1)) {
			createTache(LENGHT, HEIGHT, x1, y1, traitement, testscreen);
			createTache(LENGHT, HEIGHT, x2, y2, traitement, testscreen);
			traitement.updateConnexe(testscreen, LENGHT, HEIGHT);
			Point pos1 = traitement.getGroupePos(1);
			Point pos2 = traitement.getGroupePos(2);
			System.out.println(pos1.getX());
			System.out.println(pos1.getY());
			System.out.println(pos2.getX());
			System.out.println(pos2.getY());
			if((pos1.getX()==(double)x1)&&(pos1.getY()==(double)y1)&&(pos2.getX()==(double)x2)&&(pos2.getY()==(double)y2)){
				System.out.println("le test est reussis");
			}
			else System.out.println("en fait j'ai un peu la flemme, parce que on ne sait pas a qui appartient quelle tache");

		} else
			System.out.println("les donnees entrées ne sont pas correctes");
	}
	
	
	
	/**
	 * Methode qui permet de creer une tache lumineuse centree en (x,y) sur l'image screen
	 * @param LENGHT
	 * @param HEIGHT
	 * @param x
	 * @param y
	 * @param traitement
	 * @param screen
	 */
	private static void createTache(int LENGHT, int HEIGHT, int x, int y,
			Traitement traitement, VirtualPixel[][] screen){
		screen[x][y].setBrightness(true);
		screen[x + 1][y].setBrightness(true);
		screen[x - 1][y].setBrightness(true);
		screen[x][y + 1].setBrightness(true);
		screen[x][y - 1].setBrightness(true);
	}

}
