package demo;

import java.util.ArrayList;

import map.Map;
import map.MapReader;
import monster.zoo.Monster;

import org.lwjgl.util.ReadableColor;

import player.Player;
import traitementVideo.Traitement;
import utilities.Point;
import utilities.RandomPerso;
import views.staticViews.BackgroundView;
import views.staticViews.BaseView;
import wall.Wall;
import OpenGL.GLDisplay;
import base.Base;

public class Manager{

		private final int width;
		private final int height;
		private GLDisplay display;
		private Map map;

		public Manager() {
			RandomPerso.initialize();
			display = new GLDisplay(null);
			map = new Map();
			display.setMap(map);
			display.start();
			while (!display.initialized()) {
				try {
					Thread.sleep(10);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			width = display.getmapDisplay_width();
			height = display.getmapDisplay_height();
			System.out.println("Width: " + width + " Height: " + height);
			map.setWidth(width);
			map.setLength(height);
			display.setLightPlace(0.0f, (float) height / 2, 0.0f);
			//map.add(new Expension(new Point(width/3,height/3), height));
			new Disco(map,height);
			
	while (display.isAlive()) {
				try {
					Thread.sleep(100);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			
}
		
		
		
}
