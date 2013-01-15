package views;

import java.util.ArrayList;

public interface DisplayableFather extends Displayable {

	public double getX();
	public double getY();
	public ArrayList<DisplayableChild> getChildren();
	public void addChild(DisplayableChild object);
	public void removeChild(DisplayableChild child);
}
