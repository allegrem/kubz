package wall;
import java.util.ArrayList;

public class WallManager {
	private ArrayList<Wall> wallList;
	
		
	public WallManager(ArrayList<Wall> wallList) {
		this.wallList = wallList;
	}
	public void add(Wall e){
		wallList.add(e);
	}
	public void remove(Wall e){
		wallList.remove(e);
	}
	public ArrayList<Wall> getWallList() {
		return wallList;
	}
	public void setWallList(ArrayList<Wall> wallList) {
		this.wallList = wallList;
	}	
}
