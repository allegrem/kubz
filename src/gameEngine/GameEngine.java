package gameEngine;
import java.util.ArrayList;

import Base.Base;
import OpenGL.GLDisplay;
import Wall.Wall;

import map2.Map;
import map2.MapReader;
import monster.Monster;
import player.*;
import views.staticViews.BackgroundView;
import cubeManager.*;

public class GameEngine {
	private final int width=500;
	private final int height=500;
	private ArrayList<Monster> monsters;
	private ArrayList<Wall> walls;
	private ArrayList<Base> bases;
	private CubeManager cubeManager;
	private GLDisplay display;
	private Map map;
	private MapReader reader=new MapReader("map/bases","map/monsters","map/walls");
	
	public GameEngine(){
		map= new Map(width,height);
		display=new GLDisplay(width,height,map);
		display.start();
		map.add(new BackgroundView(width,height));
		/* Valeh doit modifier MapReader pour que ca marche
		monsters=reader.readMonsters();
		bases=reader.readBases();
		walls=reader.readWalls();
		*/
	}

}
