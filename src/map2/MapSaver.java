package map2;

import java.io.PrintWriter;
import java.util.ArrayList;

import views.BaseView;
import views.Displayable;
import views.MonsterView;
import views.WallView;

/**
 * Cette classe permet d'enregistrer l'etat des Bases et des Unites des monstres,
 * dans deux fichiers separes.
 * @author valeh
 */
public class MapSaver {
	private Map map;
	private ArrayList<BaseView> bases = new ArrayList<BaseView>();
	private ArrayList<WallView> walls = new ArrayList<WallView>();
	private ArrayList<MonsterView> monsters = new ArrayList<MonsterView>();
	private String bFileName,mFileName; //pour bases et monstres resp.

	public MapSaver(String bFileName, String mFileName){
		this.bFileName = bFileName;
		this.mFileName = mFileName;
	}
	
	public void saveToFile(Map map){
		ArrayList<Displayable> displayables = map.getObjects();
		for (Displayable disp : displayables){
			if (disp instanceof BaseView)
				bases.add((BaseView) disp);
			else if (disp instanceof MonsterView)
				monsters.add((MonsterView) disp);
		}
		saveBases(bases);
		//saveMonsters(monsters); a comppleter... pas le temps la tout de suite!!
	}
	
	private void saveBases(ArrayList<BaseView> bases){
		/*PrintWriter pw = null;
		try{
			pw = new PrintWriter(bfileName);
			
		} idem que ci-dessus!!
	}*/
	
}
}
	
