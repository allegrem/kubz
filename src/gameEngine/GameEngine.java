package gameEngine;

import org.lwjgl.util.ReadableColor;

import java.util.ArrayList;

import base.Base;

import OpenGL.GLDisplay;
import map2.Map;
import map2.MapReader;
import monster.Monster;
import player.*;
import player.unit.Unit;
import synthesis.Sound;
import synthesis.fmInstruments.BellInstrument;
import traitementVideo.Traitement;
import utilities.Point;
import utilities.RandomPerso;
import views.staticViews.BackgroundView;
//import wall.Wall;
import cube.Cube;

//import cubeManager.*;

public class GameEngine {
	private final int width;
	private final int height;
	public ArrayList<Monster> monsterList;
	private ArrayList<Player> playerList;
	// private ArrayList<Wall> walls;
	private ArrayList<Base> bases;
	// private CubeManager cubeManager;
	private GLDisplay display;
	private Map map;
	private MapReader reader = new MapReader("Maps/bFile.txt",
			"Maps/mFile.txt", "Maps/WFile.txt", this);
	private Traitement traitement;

	public GameEngine() {
		RandomPerso.initialize();
		display = new GLDisplay(this);
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
		map.add(new BackgroundView(width, height, 5000));
		try {
			monsterList = reader.readMonsters();
			// bases=reader.readBases();
			bases = new ArrayList<Base>();
			// walls=reader.readWalls();
			playerList = new ArrayList<Player>();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		bases.add(new Base(ReadableColor.ORANGE, this));
		playerList.add(new Player(this, bases.get(0)));
		// playerList.add(new Player(this,bases.get(0)));
		// playerList.add(new Player(this));
		act();

	}

	/**
	 * pas de liste de joueurs car un seul joueur pour le moment, de meme une
	 * seule unite (que 3 cubes pour le moment)
	 */

	public ArrayList<Unit> getUnitList() {
		ArrayList<Unit> unitList = new ArrayList<Unit>();
		for (Player player : playerList)
			unitList.add(player.getUnit());
		return unitList;
	}

	public ArrayList<Player> getPlayerList() {
		ArrayList<Player> playerList = new ArrayList<Player>();
		for (Player player : this.playerList)
			playerList.add(player);
		return playerList;
	}

	/**
	 * Methode qui lance les actions des joueurs
	 */
	public void monsterTurn() {
		for (Monster m : monsterList) {
			m.act();
		}
	}

	/**
	 * Methode qui lance les actions du(des) joueur(s)
	 */
	public void playerTurn() {
		for (Player player : playerList) {
			player.act();
		}
	}

	/**
	 * Methode qui freeze le jeu, condition de relance sur le mode normal a
	 * revoir, a lancer quand un cube disparait
	 */
	public void frozen() {
	}

	/**
	 * Mehode principale du gameEngine
	 */
	public void act() {
		while (display.isAlive()) {
			playerTurn();
			monsterTurn();

		}
	}

	public Map getMap() {
		return map;

	}

	/**
	 * Methode a appeler quand on perd un ou plusieurs cubes Tous les cubes
	 * deviennent rouge si ils ont mal places et verts si ils sont bien places
	 * Des carres rouges apparaissent sous la precedente position connue
	 * 
	 * @param owner
	 */
	public void setFrozen(Cube source) {
		boolean ok = false;
		boolean internOk = true;
		for (Player player : playerList) {
			player.getUnit().getCube().setIrOf();
			player.getUnit().getCube()
					.setRGB((byte) 127, (byte) 0, (byte) 0, (short) 500);
			player.getParameters()[0].getCube().setIrOf();
			player.getParameters()[0].getCube().setRGB((byte) 127, (byte) 0,
					(byte) 0, (short) 500);
			player.getParameters()[1].getCube().setIrOf();
			player.getParameters()[1].getCube().setRGB((byte) 127, (byte) 0,
					(byte) 0, (short) 500);
			// ici on affiche les nouvelles positions
		}
		while (!ok) {
			internOk = true;
			for (Player player : playerList) {
				boolean unitOk = checkPos(player.getUnit());
				if (unitOk)
					player.getUnit()
							.getCube()
							.setRGB((byte) 0, (byte) 127, (byte) 0, (short) 500);
				else
					player.getUnit()
							.getCube()
							.setRGB((byte) 127, (byte) 0, (byte) 0, (short) 500);
				boolean param1ok = checkPos(player.getParameters()[0]);
				if (param1ok)
					player.getParameters()[0].getCube().setRGB((byte) 0,
							(byte) 127, (byte) 0, (short) 500);
				else
					player.getParameters()[0].getCube().setRGB((byte) 127,
							(byte) 0, (byte) 0, (short) 500);
				boolean param2ok = checkPos(player.getParameters()[1]);
				if (param1ok)
					player.getParameters()[1].getCube().setRGB((byte) 0,
							(byte) 127, (byte) 0, (short) 500);
				else
					player.getParameters()[1].getCube().setRGB((byte) 0,
							(byte) 127, (byte) 0, (short) 500);
				internOk = internOk && unitOk && param1ok && param2ok;
			}
			ok = internOk;
		}

	}
	
	
	

	public ArrayList<Monster> getMonsterList() {
		return monsterList;
	}

	public void setMonsterList(ArrayList<Monster> monsterList) {
		this.monsterList = monsterList;
	}

	public int getWidth() {
		return width;
	}

	public int getHeight() {
		return height;
	}

	public int getWindowWidth() {
		return display.getDisplay_width();
	}

	public int getWindowHeight() {
		return display.getDisplay_height();
	}

	public GLDisplay getDisplay() {
		return display;
	}

	private boolean checkPos(CubeOwner cubeOwner) {
		cubeOwner.getCube().setIrOn();
		traitement.updateConnexe(null); // int�gration avec la cam �
										// regler pour recuperer l'image
		ArrayList<Point> currentPositions = new ArrayList<Point>();
		for (int i = 1; i <= 3; i++) {
			currentPositions.add(traitement.getGroupePos(i));
		}
		boolean result = currentPositions.containsAll(cubeOwner.getVCube()
				.getPos());
		// la on verifie que le traitement voit bien le cube
		cubeOwner.getCube().setIrOf();
		return result;
	}
}
