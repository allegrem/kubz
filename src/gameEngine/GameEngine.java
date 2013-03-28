package gameEngine;

/**
 * @author Felix
 * @author Paul
 * 
 */

import java.util.ArrayList;

import map.Map;
import map.MapReader;
import monster.zoo.Monster;
import org.lwjgl.util.ReadableColor;

import cl.eye.GrabberShow;
import player.CubeOwner;
import player.Player;
import player.unit.Unit;
import traitementVideo.Traitement;
import traitementVideo.VideoCube;
import utilities.Point;
import utilities.RandomPerso;
import views.staticViews.BackgroundView;
import views.staticViews.BaseView;
import wall.Wall;
import OpenGL.GLDisplay;
import OpenGL.KeyboardManager;
import OpenGL.Starting2;
import base.Base;
import cube.Cube;
import cubeManager.CubeManager;

public class GameEngine extends Thread {

	private final int width;
	private final int height;
	public ArrayList<Monster> monsterList;
	private ArrayList<Player> playerList;
	private ArrayList<Wall> walls;
	private ArrayList<Base> bases;
	private GLDisplay display;
	private Map map;
	private MapReader reader = new MapReader("Maps/bFile.txt",
			"Maps/mFile.txt", "Maps/WFile.txt", this);
//	private Traitement traitement;
//	private CubeManager cubeManager;
	private ArrayList<VideoCube> cubeList = new ArrayList<VideoCube>();
//	private final GrabberShow gs;

	public GameEngine(/*CubeManager cubeManager*/) {
		RandomPerso.initialize();
//		this.cubeManager = cubeManager;
//		gs = new GrabberShow();
//        Thread th = new Thread(gs);
//        th.start();
//        traitement = new Traitement((gs.Right-gs.Left-1),(gs.Bottom-gs.Top-1));
        try {
			Thread.sleep(500);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
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
		//map.add(new Starting2());
		try {
			monsterList = reader.readMonsters();
			// bases=reader.readBases();
			bases = new ArrayList<Base>();
		    walls=reader.readWalls();
		    System.out.println("Taille de walls " + walls.size());
			playerList = new ArrayList<Player>();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		//init();
		
		
		bases.add(new Base(ReadableColor.ORANGE,BaseView.BAS,this));
//		bases.add(new Base(ReadableColor.BLUE,BaseView.GAUCHE,this));
//		bases.add(new Base(ReadableColor.GREEN,BaseView.DROITE,this));
		playerList.add(new Player(this, bases.get(0), 45679, 15000, 15075/*, cubeList.get(0), cubeList.get(1), cubeList.get(2)*/));
//		playerList.add(new Player(this, bases.get(1), 45675, 45671, 14837/*, cubeList.get(3), cubeList.get(4), cubeList.get(5)*/));
//		playerList.add(new Player(this, bases.get(2), 53192, 35916, 45676));


	}

	/**
	 * pas de liste de joueurs car un seul joueur pour le moment, de meme une
	 * seule unite (que 3 cubes pour le moment)
	 */

	public ArrayList<Unit> getUnitList() {
		ArrayList<Unit> unitList = new ArrayList<Unit>();
		for (Player player : playerList)
			unitList.addAll(player.getUnitList());
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
	 * @throws IllegalAccessException 
	 * @throws InstantiationException 
	 */
	public void playerTurn() throws InstantiationException, IllegalAccessException {
		for (Player player : playerList) {
			player.act();
		}
	}

	public void removePlayer(Player player) {
		playerList.remove(player);
		if (playerList.isEmpty()) {
			// quand tous les joueurs sont morts on affiche "game over"
			display.print(width / 2, height / 2, ReadableColor.RED,
					" Game Over !");
			// tant que l'on appuie pas sur la touche q le jeu tourne en boucle
			while (!KeyboardManager.qKey) {
				try {
					Thread.sleep(10);
				} catch (InterruptedException e) { // automatiquement
					e.printStackTrace();
				}
			}
			System.exit(0);

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
	public void run() {
		while (display.isAlive()) {
			try {
				playerTurn();
			} catch (InstantiationException | IllegalAccessException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
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
			for (Unit unit : player.getUnitList()) {
				unit.getCube().setIrOff();
				unit.getCube().setRGB((byte) 127, (byte) 0, (byte) 0,
						(short) 500);
			}
			player.getParameters()[0].getCube().setIrOff();
			player.getParameters()[0].getCube().setRGB((byte) 127, (byte) 0,
					(byte) 0, (short) 500);
			player.getParameters()[1].getCube().setIrOff();
			player.getParameters()[1].getCube().setRGB((byte) 127, (byte) 0,
					(byte) 0, (short) 500);
		}
		while (!ok) {
			internOk = true;
			/*
			 * on verifie que les elements lies a chaque Player sont bien a leur
			 * place
			 */
			for (Player player : playerList) {
				boolean unitsOk = true;
				/*
				 * on verifie que chacune des Unit de player sont � leur place
				 */
				for (Unit unit : player.getUnitList()) {
					boolean unitOk = checkPos(unit);
					if (unitOk)
						unit.getCube().setRGB((byte) 0, (byte) 127, (byte) 0,
								(short) 500);
					else
						unit.getCube().setRGB((byte) 127, (byte) 0, (byte) 0,
								(short) 500);
					unitsOk = unitsOk && unitOk; // si une des unit de player
													// n'est pas a sa place,
													// alors tout se bloque
				}
				/*
				 * on verifie que les Parameter de player sont bien a leur place
				 */
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
				internOk = internOk && unitsOk && param1ok && param2ok;
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
		// regler pour recuperer l'image
		ArrayList<Point> currentPositions = new ArrayList<Point>();
		for (int i = 1; i <= 3; i++) {
			currentPositions.add(traitement.getGroupePos(i));
		}
		boolean result = currentPositions.containsAll(cubeOwner.getVCube()
				.getPos());
		// la on verifie que le traitement voit bien le cube
		cubeOwner.getCube().setIrOff();
		return result;
	}
	
	public void updatePos(){
		
	}
	
	public final ArrayList<Wall> getWalls(){
		return walls;
	}

	public final CubeManager getCubeManager() {
		return cubeManager;
	}

	public final void setCubeManager(CubeManager cubeManager) {
		this.cubeManager = cubeManager;
	}
	
	public final void updateImage(){
		traitement.setTraitScreen(gs.getcameraScreen());
		traitement.flouMedian();
		traitement.flouMedian();
		traitement.seuil();
		traitement.updateConnexe();
		for (VideoCube vc : cubeList){
			traitement.localSearch(vc);
		}
	}
	
	public void init(){
		ArrayList<Integer> idList = new ArrayList<Integer>();
		idList.add(new Integer(45679));
		idList.add(new Integer(15000));
		idList.add(new Integer(15075));
		idList.add(new Integer(45675));
		idList.add(new Integer(45671));
		idList.add(new Integer(14837));
        try {
			Thread.sleep(500);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		int size = 0;		
		int i = 1;
		int j = 1;
		for (Integer id:idList){
			size = 0;
			//tres moche mais on a une merde avec les ack, des messages sont perdus
			cubeManager.getCube(id).setIR((byte) 5);
			cubeManager.getCube(id).setIR((byte) 5);
			cubeManager.getCube(id).setIR((byte) 5);
			cubeManager.getCube(id).setIR((byte) 5);
			cubeManager.getCube(id).setIR((byte) 5);		
			while(size!=2){
				traitement.setTraitScreen(gs.getcameraScreen());
				traitement.flouMedian();
				traitement.flouMedian();
				traitement.seuil();
				traitement.updateConnexe();
				size = traitement.getNcomp();
			}
			System.out.println("Creation du cube" + (i%3 +1) + " du joueur " + j);
			VideoCube cube = new VideoCube(traitement.getGroupePos(1),traitement.getGroupePos(2),null);
			cubeList.add(cube);
			//tres moche mais on a une merde avec les ack, des messages sont perdus
			cubeManager.getCube(id).setIR((byte) 0);
			cubeManager.getCube(id).setIR((byte) 0);
			cubeManager.getCube(id).setIR((byte) 0);
			cubeManager.getCube(id).setIR((byte) 0);
			cubeManager.getCube(id).setIR((byte) 0);
			i++;
			if (i%3==0) j++;
			}	
		
	}
	

	public Traitement getTraitement() {
		return traitement;
	}

	
}
