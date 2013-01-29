package monster;

/**classe qui sert de modèle à MonsterView
 * @author Felix
 */

import utilities.Point;
import views.*;

import gameEngine.GameEngine;

import java.util.*;
import unit.Unit;

public class Monster {
	
	/**
	 * Différents xxxxType qui permettent de générer différents types
	 * de monstres de façon modulaire 
	 */
	
	private GameEngine gameEngine;
	private AttackType attack;
	private DefenceType defence;
	private MoveType move;
	private LifeType life;
	private Unit cible;
	private ArrayList<Unit> seenUnits; 
	private Point pos;
	
	/**
	 * référence vers la vue du monstre pour pouvoir transmettre les modifications nécessaires 
	 */
	private MonsterView view;
	
	/**
	 * crée un nouveau monstre à la position (xStart,yStart)
	 * @param xStart
	 * @param yStart
	 */
	public Monster(int xStart, int yStart, GameEngine gameEngine){
		
		this.pos = new Point(xStart, yStart);
		this.cible = null;
		this.seenUnits = new ArrayList<Unit>();
		
	}
	
					/** Partie qui gère l'attaque du monstre*/
	
	
	/**
	 * Méthode qui permet de déterminer le potentiel de chaque unité, l'unité 
	 * ayant une caractérisque précise sera la cible du monstre (ex vie, distance)
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
			//impléneter la boucle sur la position des murs
			seenUnits.add(unit);
		}
		
	}
	
	/**
	 * La composante attack va fournir une ArrayList de fréquence et d'intensités qui vont 
	 * arriver sur le filtre de l'Unit
	 * @param attackTable
	 */
	
	private void attack(ArrayList<int[]> attackTable){
		setSeenUnits(gameEngine.getUnitList());
		setPotList();
		setCible();
		
		if(cible != null){
			for(int i=0; i<attackTable.size();i++){
				cible.decreaseLife(cible.getValue(attackTable.get(i)[0])*attackTable.get(i)[1]);
			}
		}
		else return;
		
	}
	
	
					/** Actions liées à la position**/
	
	/** 
	 * lance la procédure de mouvement du monstre
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
