package monster;

/**classe qui sert de mod�le � MonsterView
 * @author Felix
 */

import utilities.Point;
import views.*;
import views.monsters.MonsterView;

import gameEngine.GameEngine;

import java.util.*;
import unit.Unit;

public class Monster {
	
	/**
	 * Diff�rents xxxxType qui permettent de g�n�rer diff�rents types
	 * de monstres de fa�on modulaire 
	 */
	
	protected GameEngine gameEngine;
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
	protected MonsterView view;
	
	/**
	 * cr�e un nouveau monstre � la position (xStart,yStart)
	 * @param xStart
	 * @param yStart
	 */
	public Monster(float xStart, float yStart, GameEngine gameEngine){
		
		this.pos = new Point(xStart, yStart);
		this.cible = null;
		this.seenUnits = new ArrayList<Unit>();
		this.gameEngine=gameEngine;
		
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
	
	/**
	 * La composante attack va fournir une ArrayList de fr�quence et d'intensit�s qui vont 
	 * arriver sur le filtre de l'Unit
	 * 
	 * Cette m�thode prend en compte la distance du monstre au joueur et les d�gats d�croient en 1/r
	 * 
	 * @param attackTable
	 */
	
	private void attack(ArrayList<int[]> attackTable){
		setSeenUnits(gameEngine.getUnitList());
		setPotList();
		setCible();
		
		if(cible != null){
			for(int i=0; i<attackTable.size();i++){
				double distance = this.pos.distanceTo(cible.getPos());
				cible.decreaseLife(cible.getOwner().getValue(attackTable.get(i)[0])*attackTable.get(i)[1]/distance);
			}
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
	
	public void act(){
		move();
		attack(attack.result());
	}
}
