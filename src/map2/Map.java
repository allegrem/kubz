package map2;

import java.util.ArrayList;
import java.util.Observable;

/**
 * Contient toutes les données concernant la map de jeu:
 * map en elle même et unités à afficher
 * 
 * @author paul
 *
 */
public class Map implements Observer{
	private ArrayList<Wall> walls;
	private ArrayList<Unit> units;
	
	public Map(ArrayList<Wall> walls,ArrayList<Unit> units){
		this.walls=copyOfW(walls);
		this.units=copyOfU(units);
		
	}
	
	
	public void updateW(ArrayList<Wall> walls) {
		this.walls=copyOfW(walls);
		
	}






	
	public void updateU(ArrayList<Unit> walls) {
		this.units=copyOfU(walls);
		
	}
	
	
	
	
	/**
	 * Copie d'une arraylist de murs
	 * 
	 * @param walls arraylist à copier
	 * @return arraylist copiée
	 */
	public ArrayList<Wall> copyOfW(ArrayList<Wall> walls){
		ArrayList<Wall> wallsCopy= new ArrayList<Wall>();
		
		for(int i=0;i<walls.size();i++){
			wallsCopy.add(walls.get(i));
		}
		
		return wallsCopy;
	}
	
	/**
	 * Copie d'une arraylist d'unités
	 * 
	 * @param walls arraylist à copier
	 * @return arraylist copiée
	 */
	public ArrayList<Unit> copyOfU(ArrayList<Unit> units){
		ArrayList<Unit> unitsCopy= new ArrayList<Unit>();
		
		for(int i=0;i<units.size();i++){
			unitsCopy.add(units.get(i));
		}
		
		return unitsCopy;
	}
	
	
	
}
