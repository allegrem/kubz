package main;

import map2.MapCreator;
import OpenGL.GLDisplay;
/*
 * Lancement du créateur de maps
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
