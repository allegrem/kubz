package map2;

import java.util.ArrayList;
/**
 * Classe dont doivent dériver les objets observables
 * 
 * @author paul
 *
 */
public class Observable {
	private ArrayList<Map> observers =new ArrayList<Map>();
	private boolean changedU=false;
	private boolean changedW=false;
	private boolean changedB=false;
	
	public void addObserver(Map map){
		observers.add(map);
		
	}
	
	public void setChangedU(){
		changedU=true;
	}
	
	public void setChangedB(){
		changedB=true;
	}
	
	public void setChangedW(){
		changedW=true;
	}
	
	public void notifyObserversU(ArrayList<Unit> units) {
		if (changedU) {
			changedU = false;
			for (Map map : observers) {
				map.updateU(units);
			}
		}
	}
	
	public void notifyObserversB(ArrayList<Base> bases) {
		if (changedB) {
			changedB = false;
			for (Map map : observers) {
				map.updateB(bases);
			}
		}
	}
	
	public void notifyObserversW(ArrayList<Wall> walls) {
		if (changedW) {
			changedW = false;
			for (Map map : observers) {
				map.updateW(walls);
			}
		}
	}
	

}
