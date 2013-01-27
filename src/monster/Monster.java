package monster;

/**classe qui sert de mod�le � MonsterView
 * @author Felix
 */

import utilities.Point;
import views.*;

import java.util.*;
import unit.Unit;

public class Monster {
	
	/**
	 * Diff�rents xxxxType qui permettent de g�n�rer diff�rents types
	 * de monstres de fa�on modulaire 
	 */
	private AttackType attack;
	private DefenceType defence;
	private MoveType move;
	private LifeType life;
	private Unit cible;
	private ArrayList<Unit> seenUnits; 
	private Point pos;
	
	/**
	 * r�f�rence vers la vue du monstre pour pouvoir transmettre les modifications n�cessaires 
	 */
	private MonsterView view;
	
	/**
	 * cr�e un nouveau monstre � la position (xStart,yStart)
	 * @param xStart
	 * @param yStart
	 */
	public Monster(int xStart, int yStart){
		
		this.pos = new Point(xStart, yStart);
		this.cible = null;
		this.seenUnits = new ArrayList<Unit>();
		
	}
	
					/** Partie qui g�re l'attaque du monstre*/
	
	
	/**
	 * M�thode qui permet de d�terminer le potentiel de chaque unit�, l'unit� 
	 * ayant une caract�risque pr�cise sera la cible du monstre (ex vie, distance)
	 */
	
	private void setPotList(){
		
		for(Unit unit : seenUnits){
			setPot(unit);
		}
		
	}	
	private void setPot(Unit unit){		
	}		
	
	/**
	 * parcours la liste des units vues et en sort l'unit avec le meilleur potentiel.
	 * @return
	 */
	
	private Unit getBetterPot(){
		return null;
	}
	private void setCible(){
		this.cible = getBetterPot();
	}	
	public Unit getCible(){
		return cible;
	}		
	private void setSeenUnits(ArrayList<Unit> unitList){
		
		for(Unit unit : unitList){
			Point unitPos = unit.getPos();
			//impl�neter la boucle sur la position des murs
			seenUnits.add(unit);
		}
		
	}
	
	public void attacks(){
		
		if(cible != null){
			attack.attack(cible);
		}
		else return;
		
	}
	
	
					/** Actions li�es � la position**/
	
	/** 
	 * lance la proc�dure de mouvement du monstre
	 */
 	 void move(){
		move.move();
	}
	
	/**
	 * deplacement relatif du monstre
	 * @param dx
	 * @param dy
	 */	
	public void translate(int dx, int dy) { 
		pos.translate(dx, dy);
		view.translate(dx, dy);
	}
	
	/**
	 * deplacement absolu du monstre
	 * @param x
	 * @param y
	 */	
	public void setLocation(int x, int y) {		
		pos.move(x, y);
		view.setLocation(x,y);
	}
	
	/**
	 * deplacement absolu du monstre
	 * @param p
	 */
	public void setLocation(Point p) {
		pos.setLocation(p);
		view.setLocation(p);
	}
	
	/** retourne la position en x,y ou Point**/
	public double getX(){
		return pos.getX();
	}
	public double getY(){
		return pos.getY();
	}
	public Point getPos(){
		return pos;
	}
}
