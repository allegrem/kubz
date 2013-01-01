package map2;

import java.util.ArrayList;

public interface Observer {

	public void updateW(ArrayList<Wall> walls);

	public void updateU(ArrayList<Unit> walls);

}
