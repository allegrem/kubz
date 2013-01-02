package map2;

import java.util.ArrayList;
/**
 * Interface qui doit être implémentée par les objets observeur
 * 
 * @author paul
 *
 */
public interface Observer {

	public void updateW(ArrayList<Wall> walls);

	public void updateU(ArrayList<Unit> walls);
	
	public void updateB(ArrayList<Base> bases);

}
