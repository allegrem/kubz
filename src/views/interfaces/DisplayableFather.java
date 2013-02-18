package views.interfaces;

import java.util.ArrayList;

import utilities.Point;

/**
 * Objet affichable de type "Pere":
 * on peut lui attacher des fils
 * @author paul
 *
 */
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
	 * 
	 * @return L'orientation de l'objet
	 */
	public double getAngle();

	/**
	 * 
	 * @return La taille de l'objet
	 */
	public double getSize();

	/**
	 * 
	 * @return Hauteur de l'objet
	 */
	public double getHeight();

	public void setUnTracked(boolean b);
}
