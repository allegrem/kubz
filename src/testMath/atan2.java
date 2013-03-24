package testMath;

import java.util.ArrayList;

import traitementVideo.VirtualPixel;



public class atan2 {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		int a = -1;
		int b = 10;
		if (a>=0)
		System.out.println("test circulaire : " + a%b);
		else
		System.out.println("test circulaire : " + ((a%b +b)%b));
		
		int dist = 7;
		ArrayList<ArrayList<VirtualPixel>> points = new ArrayList<ArrayList<VirtualPixel>>();
		points.ensureCapacity((int) Math.sqrt(2 * dist * dist));
		System.out.println(points.size());
		
		System.out.print(1);
		System.out.print(1);
		
		/*for(int i=0; i<-2;i++){
			System.out.println("iteration n°" + i );
		}
		
		System.out.println("debut boucle");
		for(int i=0; i<10000;i++){}
		System.out.println("fin boucle");
		
		for(int i=0; i<10000;i++){
			System.out.println(i);
		}*/
		

	}

}
