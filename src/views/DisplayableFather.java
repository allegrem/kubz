package views;

import java.util.ArrayList;

import utilities.Point;

public interface DisplayableFather extends Displayable {

	public double getX();
	public double getY();
	public ArrayList<DisplayableChild> getChildren();
	public void addChild(DisplayableChild object);
	public void removeChild(DisplayableChild child);
	public boolean collisionCanOccure(Point point, float taille);
}
