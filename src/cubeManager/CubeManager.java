package cubeManager;
import java.util.ArrayList;
import cube.*;


public class CubeManager {
	
	private ArrayList<Cube> usedCubesList;
	private ArrayList<Cube> freeCubesList;
	
	
	public Cube getFreeCube(){
		Cube cube = freeCubesList.remove(0);
		usedCubesList.add(cube);
		return cube;
	}
	
	public ArrayList<Cube> getAllCubes(){
		return usedCubesList;
	}
	

}
