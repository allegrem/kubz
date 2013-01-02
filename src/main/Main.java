package main;

import map2.MapCreator;
import OpenGL.GLBaseModule;
/*
 * Lancement du créateur de maps
 * 
 */
public class Main {
	private static final int display_width=640;
	private static final int display_length=480;

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		new MapCreator(display_width,display_length);
	}

}
