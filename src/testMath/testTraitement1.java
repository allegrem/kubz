package testMath;
/**
 * Test à lancer pour vérifier que le repérage des groupes connexes se fait bien sur un point
 */
import traitementVideo.Traitement;
import traitementVideo.VirtualPixel;
import utilities.Point;

public class testTraitement1 {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		Traitement traitement = new Traitement();
		
		test(10,10,4,5,traitement);
	}
	
	private static void test(int LENGHT, int HEIGHT, int x, int y, Traitement traitement){
		VirtualPixel[][] testscreen = new VirtualPixel[LENGHT][HEIGHT]; 
		for (int i=0;i<LENGHT;i++){
			for (int j=0;j<HEIGHT;j++){
				testscreen[i][j] = new VirtualPixel(false, 0, new Point(i, j));
			}
		}
		if ((x<=LENGHT)&&(y<=HEIGHT)){
			testscreen[x][y] = new VirtualPixel(true, 0, new Point(LENGHT,HEIGHT));
			traitement.updateConnexe(testscreen, LENGHT, HEIGHT);
			Point pos = traitement.getGroupePos(1);
			if ((x==(int) pos.getX())&&(y == (int )pos.getY())){
				System.out.println("le test est réussis");
				System.out.println(pos.getX());
				System.out.println(pos.getY());
			}
			else {
				System.out.println("le test est raté");	
				System.out.println(pos.getX());
				System.out.println(pos.getY());
			}
		}		
		else System.out.println("les donnees entrées ne sont pas correctes");
	}
}
