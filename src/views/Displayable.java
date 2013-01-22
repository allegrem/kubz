package views;

import java.util.ArrayList;

import org.lwjgl.util.Color;
import org.lwjgl.util.ReadableColor;

import utilities.Point;

/**
 * Objet affichable
 * @author paul
 *
 */
public interface Displayable {
	/**
	 * Genere le rendu de l'objet
	 */
	public void paint();
	
	/**
	 * 
	 * @return Le temps d'affichage de l'objet
	 * Si le temps est negatif, l'objet sera 
	 * affiche indefiniement
	 */
	public int getTimeOut();
	
	/**
	 * Definir le temps d'affichage de l'objet
	 * Si le temps est negatif, l'objet sera 
	 * affiche indefiniement
	 * @param time
	 */
	public void setTimeOut(int time);
	
	/**
	 * Savoir si un point se trouve ou non a l'interieur
	 * de l'objet
	 * @param mousePoint Le point concerne
	 * @return
	 */
	public boolean isInZone(Point mousePoint);
	
	/**
	 * Definir la couleur de l'objet
	 * @param color
	 */
	public void setColor(ReadableColor color);
	
	/**
	 * 
	 * @return Une chaine de caracteres
	 * definissant l'objet
	 */
	public String getCharac();

}
