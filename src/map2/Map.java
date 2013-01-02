package map2;

import java.util.ArrayList;

/**
 * Contient toutes les données concernant la map de jeu: map en elle même et
 * unités à afficher
 * 
 * @author paul
 * 
 */
public class Map implements Observer {
	private ArrayList<Wall> walls;
	private ArrayList<Base> bases;
	private ArrayList<Unit> units;
	private int width;
	private int length;
	private Path path ;

	public Map(ArrayList<Wall> walls, ArrayList<Unit> units,ArrayList<Base> bases, int width,
			int length) {
		this.walls = copyOfW(walls);
		this.units = copyOfU(units);
		this.bases=copyOfB(bases);
		this.width = width;
		this.length = length;
		path=new Path(width, length);
	}

	public void updateW(ArrayList<Wall> walls) {
		this.walls = copyOfW(walls);
		paintWalls();

	}

	public void updateU(ArrayList<Unit> walls) {
		this.units = copyOfU(walls);

	}
	
	public void updateB(ArrayList<Base> bases) {
		this.bases = copyOfB(bases);

	}
	
	public ArrayList<Wall> getInitialWalls(){
		return copyOfW(walls);
		
	}
	
	public ArrayList<Unit> getInitialUnits(){
		return copyOfU(units);
		
		
	}
	
	public ArrayList<Base> getInitialBases(){
		return copyOfB(bases);
		
		
	}

	private void paintPath() {
		path.paint();

	}

	private void paintWalls() {
		for (int i = 0; i < walls.size(); i++) {
			walls.get(i).paint();
		}

	}

	private void paintUnits() {
		for (int i = 0; i < units.size(); i++) {
			units.get(i).paint();
		}

	}
	
	private void paintBases() {
		for (int i = 0; i < bases.size(); i++) {
			bases.get(i).paint();
		}

	}

	public void paint() {
		paintPath();
		paintWalls();
		paintUnits();
		paintBases();

	}

	/**
	 * Copie d'une arraylist de murs
	 * 
	 * @param walls
	 *            arraylist à copier
	 * @return arraylist  copiée
	 */
	public ArrayList<Wall> copyOfW(ArrayList<Wall> walls) {
		ArrayList<Wall> wallsCopy = new ArrayList<Wall>();

		for (int i = 0; i < walls.size(); i++) {
			wallsCopy.add(walls.get(i));
		}

		return wallsCopy;
	}

	/**
	 * Copie d'une arraylist d'unités
	 * 
	 * @param walls
	 *            arraylist à copier
	 * @return arraylist copiée
	 */
	public ArrayList<Unit> copyOfU(ArrayList<Unit> units) {
		ArrayList<Unit> unitsCopy = new ArrayList<Unit>();

		for (int i = 0; i < units.size(); i++) {
			unitsCopy.add(units.get(i));
		}

		return unitsCopy;
	}
	
	public ArrayList<Base> copyOfB(ArrayList<Base> bases) {
		ArrayList<Base> basesCopy = new ArrayList<Base>();

		for (int i = 0; i < bases.size(); i++) {
			basesCopy.add(bases.get(i));
		}

		return basesCopy;
	}

}
