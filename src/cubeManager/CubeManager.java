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
        
        Cube cube1 = new Cube(xbee);
        cube1.setID(45679);
        this.addFreeCube(cube1);
        
        Cube cube2 = new Cube(xbee);
        cube2.setID(45675);
        this.addFreeCube(cube2);
        
        Cube cube3 = new Cube(xbee);
        cube3.setID(45671);
        this.addFreeCube(cube3);
        
        Cube cube4 = new Cube(xbee);
        cube4.setID(14837);
        this.addFreeCube(cube4);
        
        Cube cube5 = new Cube(xbee);
        cube5.setID(53192);
        this.addFreeCube(cube5);
        
        Cube cube6 = new Cube(xbee);
        cube6.setID(15000);
        this.addFreeCube(cube6);
        
        Cube cube7 = new Cube(xbee);
        cube7.setID(15075);
        this.addFreeCube(cube7);
        
        Cube cube8 = new Cube(xbee);
        cube8.setID(35916);
        this.addFreeCube(cube8);
        
        Cube cube9 = new Cube(xbee);
        cube9.setID(45676);
        this.addFreeCube(cube9);
        
        Cube cube10 = new Cube(xbee);
        cube10.setID(14977);
        this.addFreeCube(cube10);
            
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
