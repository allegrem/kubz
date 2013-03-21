package monster.zoo;

/**classe qui sert de modele e MonsterView
 * @author Felix
 */

import utilities.Point;
import views.informationViews.LifeView;
import views.monsters.MonsterView;
import wall.Wall;

import gameEngine.GameEngine;

import java.util.*;

import player.unit.Unit;

import monster.DefenceType;
import monster.attack.AttackType;
import monster.attack.ChooseType;
import monster.move.MoveType;
import monster.move.RandomMove;


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
	protected double life;
	protected LifeView lifeView;
	protected MonsterView view;
	

	
	
	//private LifeType life;
	private DefenceType defence;

	
	/**
	 * cree un nouveau monstre e la position (xStart,yStart)
	 * @param xStart
	 * @param yStart
	 */
	public Monster(float xStart, float yStart, GameEngine gameEngine){
		
		defence = new DefenceType();
		this.pos = new Point(xStart, yStart);
		this.cible = null;
		this.seenUnits = new ArrayList<Unit>();
		this.setGameEngine(gameEngine);
		move=new RandomMove(this,500);
		this.life = 1;
		
	}
	
	public void increaseLife(double inc){
		life = life + inc;
		lifeView.setLife(life);
	}
	public void decreaseLife(double dec){
		life = life - dec;
		lifeView.setLife(life);
		if (life<0){
			gameEngine.getMonsterList().remove(this);
			view.removeChild(lifeView);
			gameEngine.getMap().remove(view);
			this.view = null;
		}
	}
	public void setLife(double newLife){
		life = newLife;
		lifeView.setLife(life);
	}
	public double getLife(){
		return life;
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
		updateSeenUnits();
		int pot = Integer.MIN_VALUE;
		for(Unit unit: seenUnits){
			if(getPot(unit)>pot){
				pot = getPot(unit);
				newCible = unit;
			}
		}
		cible = newCible;
	}	
	
	
	private void updateSeenUnits(){
		ArrayList<Unit> unitList = gameEngine.getUnitList();
		seenUnits = unitList;
		ArrayList<Wall> walls = gameEngine.getWalls();
		ArrayList<ArrayList<Double>> angleList = new ArrayList<ArrayList<Double>>();
		double xm = this.getPos().getX();
		double ym = this.getPos().getY();
		// ici on construit la liste des intervalle d'angles qui d�finissent les
		// murs
		for (Wall wall : walls) {
			double x1 = wall.getExtremity1().getX();
			double x2 = wall.getExtremity2().getX();
			double y1 = wall.getExtremity1().getY();
			double y2 = wall.getExtremity2().getY();
			double x1diff = x1 - xm;
			double y1diff = y1 - ym;
			double extrem1Theta = (180 * Math.atan2(y1diff, x1diff) / Math.PI) - 90;
			double x2diff = x2 - xm;
			double y2diff = y2 - ym;
			double extrem2Theta = (180 * Math.atan2(y2diff, x2diff) / Math.PI) - 90;
			ArrayList<Double> angles = new ArrayList<Double>();
			angles.add(new Double(extrem1Theta));
			angles.add(new Double(extrem2Theta));
			angleList.add(angles);
		}
		// ici on verifie que le Monster ne se situe pas dans un angle de vue
		// auquel un mur appartient 
		for (Unit unit : unitList) {
			double xu = unit.getPos().getX();
			double yu = unit.getPos().getY();
			double xdiff = xm - xu;
			double ydiff = ym - yu;
			double unitTheta = (180 * Math.atan2(ydiff, xdiff) / Math.PI) - 90;
			for (int i = 0; i < angleList.size(); i++) {
				ArrayList<Double> angles = angleList.get(i);
				Wall wall = null;
				if (((angles.get(0) < unitTheta)
						&& (angles.get(1) > unitTheta) || ((angles.get(0) > unitTheta) && (angles
						.get(1) < unitTheta))))
					wall = walls.get(i);
					System.out.println(i);
					double xp1 = wall.getExtremity1().getX();
				double yp1 = wall.getExtremity1().getY();
				double xp2 = wall.getExtremity2().getX();
				double yp2 = wall.getExtremity2().getY();
				// calcul de l'intersection entre la droite qui relie Unit a
				// Monster et du wall
				double xi = (ym - yp1 - xm * (yu - ym) / (xu - xm) + xp1
						* (yp2 - yp1) / (xp2 - xp1))
						/ ((yp2 - yp1) / (xp2 - xp1) - (yu - ym) / (xu - xm));
				double yi = ym + (xi - xm) * (yu - ym) / (xu - xm);
				if (unit.getPos().distanceTo(pos) > pos
						.distanceTo(new Point(xi, yi))) {
					seenUnits.remove(unit);
				}
			}
		}
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
	
	
					/** Actions liees a la position**/
	
	/** 
	 * lance la procedure de mouvement du monstre
	 */
 	 void move(){
		move.move();
	}
	
	public DefenceType getDefence() {
		return defence;
	}

	public void setDefence(DefenceType defence) {
		this.defence = defence;
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
		System.out.println("tata");
		setSeenUnits(gameEngine.getUnitList());
		setCible();
		move();
		attack.attack(cible);
		//attack(attack.result());
		//gameEngine.getDisplay().auto3D(view, cible.getView(), 6000);
		try {
			Thread.sleep(6000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
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
