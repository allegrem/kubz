package monster;

/**classe qui sert de modele e MonsterView
 * @author Felix
 */

import utilities.Point;
import views.monsters.MonsterView;

import gameEngine.GameEngine;

import java.util.*;

import monster.attack.AttackType;
import monster.attack.ChooseType;
import monster.move.MoveType;
import monster.move.RandomMove;

import unit.Unit;

public class Monster {
	
	/**
	 * Differents xxxxType qui permettent de generer differents types
	 * de monstres de facon modulaire et actions dans le modele
	 */
	
	private GameEngine gameEngine;
	protected AttackType attack;
	protected ChooseType choice;
	protected MoveType move;
	protected Unit cible;
	protected ArrayList<Unit> seenUnits; 
	protected Point pos;
	
	/**
	 * reference vers la vue du monstre pour pouvoir transmettre les modifications necessaires 
	 */
	protected MonsterView view;
	
	//private LifeType life;
	//private DefenceType defence;

	
	/**
	 * cree un nouveau monstre e la position (xStart,yStart)
	 * @param xStart
	 * @param yStart
	 */
	public Monster(float xStart, float yStart, GameEngine gameEngine){
		
		this.pos = new Point(xStart, yStart);
		this.cible = null;
		this.seenUnits = new ArrayList<Unit>();
		this.setGameEngine(gameEngine);
		move=new RandomMove(this,500);
		
	}
	
					/** Partie qui gere l'attaque du monstre*/
	
	
	/**
	 * Methode qui permet de determiner le potentiel de chaque unite, l'unite 
	 * ayant une caracterisque precise sera la cible du monstre (ex vie, distance)
	 */
		
	private int getPot(Unit unit){
		return choice.getPot(unit);	

	}		
	
	/**
	 * parcours la liste des units vues et en sort l'unit avec le meilleur potentiel.
	 * @return
	 */
	
	
	private void setCible(){
		Unit newCible = null ;
		int pot = Integer.MIN_VALUE;
		for(Unit unit: seenUnits){
			if(getPot(unit)>pot){
				pot = getPot(unit);
				newCible = unit;
			}
		}
		cible = newCible;
	}	
	
	public Unit getCible(){
		return cible;
	}	
	/**
	 * En attendant de coder la vue des monstres
	 * @param unitList
	 */
	private void setSeenUnits(ArrayList<Unit> unitList){		
			seenUnits = unitList;		
	}
	
	/**
	 * La composante attack va fournir une ArrayList de frequence et d'intensites qui vont 
	 * arriver sur le filtre de l'Unit
	 * 
	 * Cette methode prend en compte la distance du monstre au joueur et les degats decroient en 1/r
	 * 
	 * @param attackTable
	 */
	
	/*private void attack(ArrayList<int[]> attackTable){
		setSeenUnits(getGameEngine().getUnitList());
		setCible();
		
		if(cible != null){
			for(int i=0; i<attackTable.size();i++){
				double distance = this.pos.distanceTo(cible.getPos());
				cible.decreaseLife(cible.getOwner().getValue(attackTable.get(i)[0])*attackTable.get(i)[1]/distance);
			}
		}
		else return;
		
	}*/
	
	
					/** Actions liees a la position**/
	
	/** 
	 * lance la procedure de mouvement du monstre
	 */
 	 void move(){
		move.move();
	}
	
	/**
	 * deplacement relatif du monstre
	 * @param dx
	 * @param dy
	 */	
	public void translate(double dx, double dy) { 
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
		setSeenUnits(gameEngine.getUnitList());
		setCible();
		move();
		System.out.println(cible);
		attack.attack(cible);
		//attack(attack.result());
		//gameEngine.getDisplay().auto3D(view, gameEngine.getUnitList().get(0).getView(), 5000);
	}

	public double getSize() {
	
		return view.getSize();
	}

	public GameEngine getGameEngine() {
		return gameEngine;
	}

	public void setGameEngine(GameEngine gameEngine) {
		this.gameEngine = gameEngine;
	}

	public MonsterView getView() {
		return view;
	}

	public void setView(MonsterView view) {
		this.view = view;
	}
	
	
}
