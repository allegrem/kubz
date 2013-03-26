package cubeManager;

import java.util.ArrayList;

import cube.Cube;
import cube.XBee;


public class CubeManager {
	
	private ArrayList<Cube> usedCubesList;
	private ArrayList<Cube> freeCubesList;
	private ArrayList<Integer> list;
	private XBee xbee;
	
    public CubeManager(XBee xbee){
        /* usedCubesList is initially empty */
        this.usedCubesList = new ArrayList<Cube>();
        /* Define the freeCubesList */
        this.freeCubesList = new ArrayList<Cube>();
        
        
        Cube cube9 = new Cube(xbee);
        cube9.setID(45676);
        this.addFreeCube(cube9);
            
    }
	
    /* Add a cube to the freeCubesList */
    public void addFreeCube (Cube c){
    	this.freeCubesList.add(c);
    }
    
    /* Add a cube to the usedCubesList */
    public void addUsedCube (Cube c){
    	this.usedCubesList.add(c);
    }
	
	/* Return the list of the cubes actually used */
	public ArrayList<Cube> getUsedCubesList(){
		return usedCubesList;
	}
	
	/* Return the list of the cubes actually free */
	public ArrayList<Cube> getFreeCubesList(){
		return freeCubesList;
	}
	
    /* Return the cube which has the address given */
    public Cube getCube (int address){
        Cube cube = new Cube(null);
        
        for (int i = 0; i < this.freeCubesList.size(); i++){
            if (freeCubesList.get(i).getID() == address) cube = freeCubesList.get(i);
        }

        for (int i = 0; i < this.usedCubesList.size(); i++){
            if (usedCubesList.get(i).getID() == address) cube = usedCubesList.get(i);
        }
        return cube;
    }
    
    /* Take a free Cube and put it on the usedCubesList */
   	public Cube switchFreeCube(){
   		Cube cube = freeCubesList.remove(0);
   		usedCubesList.add(cube);
   		return cube;
   	}    
   	
   	/* Take an used Cube and put it on the freeCubesList */
   	public Cube switchUsedCube(int addr){
   		Cube cube = new Cube(null);
        for (int i = 0; i < this.usedCubesList.size(); i++){
            if (usedCubesList.get(i).getID() == addr) {
            	cube = usedCubesList.get(i);
            	usedCubesList.remove(i);
            }
        }
   		freeCubesList.add(cube);
   		return cube;
   	}
}
