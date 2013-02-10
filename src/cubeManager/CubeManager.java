package cubeManager;

import cube.Cube;

import java.util.ArrayList;


public class CubeManager {
	
	private ArrayList<Cube> usedCubesList;
	private ArrayList<Cube> freeCubesList;

    public CubeManager(){
        /* usedCubesList is initially empty */
        this.usedCubesList = new ArrayList<Cube>();
        /* Define the freeCubesList */
        this.freeCubesList = new ArrayList<Cube>();
    }
	
	public Cube getFreeCube(){
		Cube cube = freeCubesList.remove(0);
		usedCubesList.add(cube);
		return cube;
	}
	
	public ArrayList<Cube> getAllCubes(){
		return usedCubesList;
	}

    public Cube getCube (int adress){

        Cube rep = new Cube(null);

        for (int i = 0; i < this.freeCubesList.size(); i++){
            if (freeCubesList.get(i).getID() == adress) rep = freeCubesList.get(i);
            i++;
        }

        return rep;
    }
	
    public void addFreeCube (Cube c){
    	this.freeCubesList.add(c);
    }

}
