package gameEngine;
import java.awt.Toolkit;
import java.util.ArrayList;

import base.Base;

import OpenGL.GLDisplay;
import map2.Map;
import map2.MapReader;
import monster.Monster;
import player.*;
import unit.Unit;
import utilities.RandomPerso;
import views.staticViews.BackgroundView;
import wall.Wall;
import cube.Cube;
import cubeManager.*;

public class GameEngine {
	public int getWidth() {
		return width;
	}

	public int getHeight() {
		return height;
	}


	private final int width;
	private final int height;
	private ArrayList<Monster> monsterList;
	private ArrayList<Player> playerList;
	private ArrayList<Wall> walls;
	private ArrayList<Base> bases;
	private CubeManager cubeManager;
	private GLDisplay display;
	private Map map;
	private MapReader reader=new MapReader("Maps/bFile.txt","Maps/mFile.txt","Maps/WFile.txt",this);
	private boolean quit;
	
	public GameEngine(){
		RandomPerso.initialize();
		display=new GLDisplay();
		map= new Map();
		display.setMap(map);
		display.start();
		while(!display.initialized()){
			try {
				Thread.sleep(10);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		width=display.getmapDisplay_width();
		height=display.getmapDisplay_height();
		map.setWidth(width);
		map.setLength(height);	
		display.setLightPlace(0.0f,(float)height/2,0.0f);
		//display.mode3D();
		map.add(new BackgroundView(width,height,100));
		try {
			monsterList=reader.readMonsters();
			bases=reader.readBases();
			walls=reader.readWalls();
			playerList=new ArrayList<Player>();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		playerList.add(new Player(this));
		playerList.add(new Player(this));
		playerList.add(new Player(this));
		while(display.isAlive()){
			playerTurn();
		
		}
	}
	
	/**
	 * pas de liste de joueurs car un seul joueur pour le moment,
	 * de m�me une seule unit� (Que 3 cubes pour le moment
	 */
	
	public ArrayList<Unit> getUnitList(){
		ArrayList<Unit> unitList= new ArrayList<Unit>();
		for (Player player : playerList)
			unitList.add(player.getUnit());
		return unitList;
	}
	
	/**
	 * M�thode qui lance les actions des joueurs 
	 */
	public void monsterTurn(){
		for(Monster m: monsterList){
			m.act();
		}
	}
	
	/**
	 * M�thode qui lance les actions du(des) joueur(s)
	 */
	public void playerTurn(){
		for(Player player: playerList){
			player.act();
		}	 
	}
	
	/**
	 * M�thode qui freeze le jeu, condition de relance sur le mode normal � revoir, � lancer quand un cube disparait
	 */
	public void frozen(){
	}
	
	/**
	 * M�thode principale du gameEngine
	 */
	public void act(){
		while(!quit){
			playerTurn();
			monsterTurn();
		}
	}
	
	public Map getMap(){
		return map;
		
	}
	

	public void setFrozen(Cube owner) {
		// TODO Auto-generated method stub
		
	}

}
