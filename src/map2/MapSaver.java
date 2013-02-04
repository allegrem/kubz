package map2;

import java.io.PrintWriter;
import java.util.ArrayList;

import views.interfaces.Displayable;
import views.interfaces.DisplayableFather;
import views.monsters.MonsterView;
import views.staticViews.BaseView;
import views.staticViews.WallView;

/**
 * Cette classe permet d'enregistrer l'etat des Bases et des Unites des monstres,
 * dans deux fichiers separes.
 * @author valeh
 */
public class MapSaver {
	private ArrayList<BaseView> bases = new ArrayList<BaseView>();
	private ArrayList<WallView> walls = new ArrayList<WallView>();
	private ArrayList<MonsterView> monsters = new ArrayList<MonsterView>();
	private String bFileName,mFileName,wFileName; //pour bases et monstres et murs resp.

	public MapSaver(String bFileName, String mFileName, String wFileName){
		this.bFileName = bFileName;
		this.mFileName = mFileName;
		this.wFileName = wFileName;
	}
	
	/**
	 * Enregistre tous les objets crees dans des fichiers
	 * @param map
	 */
	public void saveToFile(Map map){
		ArrayList<DisplayableFather> displayables = map.getObjects();
		for (Displayable disp : displayables){
			if (disp instanceof BaseView)
				bases.add( (BaseView) disp );
			else if (disp instanceof MonsterView)
				monsters.add( (MonsterView) disp );
			else if (disp instanceof WallView)
				walls.add( (WallView)disp );
		}
		saveBases(bases);
		saveMonsters(monsters); 
		saveWalls(walls);
	}
	
	/**
	 * Enregistrement des bases
	 * @param bases
	 */
	private void saveBases(ArrayList<BaseView> bases){
		PrintWriter pw = null;
		try {
			pw = new PrintWriter(bFileName);
			pw.print(bases.size());
			pw.println();
			for (BaseView base : bases) {
				pw.print(base.getCharac());
				pw.println();
			}
		} catch (Exception e) {
			System.out.println("ERROR");
		} finally {
			if (pw != null) {
				try {
					pw.close();
				} catch (Exception e) {
				}
			}
		}	
	}
	
	/**
	 * Enregistrement des monstres
	 * @param monsters
	 */
	private void saveMonsters(ArrayList<MonsterView> monsters){
		PrintWriter pw = null;
		try {
			pw = new PrintWriter(mFileName);
			pw.print(monsters.size());
			pw.println();
			for (MonsterView monsterView : monsters) {
				pw.print(monsterView.getCharac());
				pw.println();
			}
		} catch (Exception e) {
			System.out.println("ERROR");
		} finally {
			if (pw != null) {
				try {
					pw.close();
				} catch (Exception e) {
				}
			}
		}	
	}
	
	/**
	 * Enregistrement des murs
	 * @param walls
	 */
	private  void saveWalls(ArrayList<WallView> walls) {
		PrintWriter pw = null;
		try {
			pw = new PrintWriter(wFileName);
			pw.print(walls.size());
			pw.println();
			for (WallView wall : walls) {
				pw.print(wall.getCharac());
				pw.println();
			}
		} catch (Exception e) {
			System.out.println("ERROR");
		} finally {
			if (pw != null) {
				try {
					pw.close();
				} catch (Exception e) {
				}
			}
		}
	}
}
	
