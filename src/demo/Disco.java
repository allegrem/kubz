package demo;

import java.util.ArrayList;

import OpenGL.GLDisplay;

import utilities.RandomPerso;

import map.Map;

public class Disco {
	private int size;
	private Map map;
	private int nombre=5;
	private ArrayList<Tuile> tuiles=new ArrayList<Tuile>();
	private long pause=100;
	private long startingTime=0;
	private GLDisplay display;

	public Disco(GLDisplay display,Map map,int size){
		this.size=Math.round(size/nombre);
		this.map=map;
		this.display=display;
		RandomPerso.initialize();
		buildTuiles();
		while(display.isAlive()){
		moveTuiles();
		}
	}

	private void moveTuiles() {
		if(System.currentTimeMillis()-startingTime>pause){
			startingTime=System.currentTimeMillis();
			tuiles.get(RandomPerso.entier(tuiles.size())).rotate(2);
		}
		
	}

	private void buildTuiles() {
		for(int i=0;i<nombre;i++){
			for (int j=0;j<nombre;j++){
				tuiles.add(new Tuile(size,i*size,j*size));
			}
			
		}
		
		for(Tuile tuile:tuiles){
			map.add(tuile);
		}
		
	}
	

}
