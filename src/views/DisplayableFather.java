package views;

import java.util.ArrayList;

import utilities.Point;

public interface DisplayableFather extends Displayable {

	public double getX();
	public double getY();
	public ArrayList<DisplayableChild> getChildren();
	public void addChild(DisplayableChild object);
	public void removeChild(DisplayableChild child);
	
	/**
	 * Renvoie si oui ou non une collision peut se produire
	 * 
	 * @param point Centre de l'objet à analyser
	 * @param taille taille de l'objet dont le centre est point
	 * @return
	 */
	public boolean collisionCanOccure(Point point, float taille);
}
