package cubeManager;
import cube.Cube;

import java.util.ArrayList;


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

    public Cube getCube (char adress){
        for (int i = 0; i < this.usedCubesList.size(); i++){

        }
    }
	

}
