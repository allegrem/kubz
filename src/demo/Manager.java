package demo;


import map.Map;
import utilities.Point;
import utilities.RandomPerso;
import OpenGL.GLDisplay;

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
			map.setWidth(width);
			map.setLength(height);
			display.setLightPlace(0.0f, (float) height / 2, 0.0f);
			
			Expension expension=new Expension(new Point(width/2,height/2), height);
			//map.add(expension);
			//expension.start();
			
			new Disco(display,map,height);
			
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
