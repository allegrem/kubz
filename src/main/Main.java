package main;

import map2.MapCreator;
import OpenGL.GLDisplay;
/**
 * Lancement du createur de maps ou de lecteur de maps.
 * A n'utiliser que pour les tests
 * 
 */
public class Main {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		new MapCreator(MapCreator.create_mode);
		
		//new MapCreator(MapCreator.read_mode);
	}

}
