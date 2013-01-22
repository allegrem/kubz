package views;

import java.util.ArrayList;

import utilities.Point;

public interface DisplayableFather extends Displayable {
	/**
	 * 
	 * @return La coordonnee x du centre de l'objet
	 */
	public double getX();
	
	/**
	 * 
	 * @return  La coordonnee y du centre de l'objet
	 */
	public double getY();
	
	/**
	 * 
	 * @return La liste des "fils" de l'objet "pere"
	 */
	public ArrayList<DisplayableChild> getChildren();
	
	/**
	 * Ajouter un fils a l'objet
	 * @param object Le fils a ajouter
	 */
	public void addChild(DisplayableChild object);
	
	/**
	 * Retirer un fils a l'objet
	 * @param child Le fils a retirer
	 */
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
