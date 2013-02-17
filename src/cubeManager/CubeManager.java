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
	
    /* Take a free Cube and put it on the usedCubesList */
	public Cube getFreeCube(){
		Cube cube = freeCubesList.remove(0);
		usedCubesList.add(cube);
		return cube;
	}
	
	/* Return the list of the cubes actually used */
	public ArrayList<Cube> getAllCubes(){
		return usedCubesList;
	}

	/* Return the cube which has the address given */
    public Cube getCube (int adress){

        Cube rep = new Cube(null);

        for (int i = 0; i < this.freeCubesList.size(); i++){
            if (freeCubesList.get(i).getID() == adress) rep = freeCubesList.get(i);
        }

        return rep;
    }
	
    /* Add a cube to the freeCubesList */
    public void addFreeCube (Cube c){
    	this.freeCubesList.add(c);
    }

}
