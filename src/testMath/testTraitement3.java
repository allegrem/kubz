package testMath;

import traitementVideo.Traitement;
import traitementVideo.VirtualPixel;
import utilities.Point;

public class testTraitement3 {
	
	public static void main(String[] args) {
		Traitement traitement = new Traitement();

		testTache(10, 10, 4, 5, traitement);

	}
	
	public static void testTache(int LENGHT, int HEIGHT, int x1, int y1,
			Traitement traitement) {
		VirtualPixel[][] testscreen = new VirtualPixel[LENGHT][HEIGHT];
		for (int i = 0; i < LENGHT; i++) {
			for (int j = 0; j < HEIGHT; j++) {
				testscreen[i][j] = new VirtualPixel(false, 0, new Point(i, j));
			}
		}
		if ((x1 <= LENGHT - 1) && (y1 <= HEIGHT - 1)&&(x1 >= 1) && (y1 >= 1)) {
			testscreen[x1][y1] = new VirtualPixel(true, 0, new Point(x1, y1));
			testscreen[x1 + 1][y1] = new VirtualPixel(true, 0, new Point(x1 + 1, y1));
			testscreen[x1 - 1][y1] = new VirtualPixel(true, 0, new Point(x1 - 1, y1));
			testscreen[x1][y1 + 1] = new VirtualPixel(true, 0, new Point(x1, y1 + 1));
			testscreen[x1][y1 - 1] = new VirtualPixel(true, 0, new Point(x1, y1 - 1));
			traitement.updateConnexe(testscreen, LENGHT, HEIGHT);
			Point pos1 = traitement.getGroupePos(1);
			if((pos1.getX()==(double)x1)&&(pos1.getY()==(double)y1)){
				System.out.println("le test est reussis");
			}
			else System.out.println("le test est rate");

		} else
			System.out.println("les donnees entrées ne sont pas correctes");
	}

}
