package player.unit;

/**
 * 
 * @author Felix
 * 
 */

import java.util.ArrayList;

import midisynthesis.Melody;
import monster.zoo.Monster;
import cube.Cube;
import gameEngine.GameEngine;
import utilities.Point;
import views.CubeControlledView;
import views.informationViews.LifeView;
import views.interfaces.DisplayableFather;
import wall.Wall;
import player.*;

public class Unit extends CubeOwner {

	private double life;
	private double size;
	private Point pos = new Point(10, 10);
	private double aperture;
	private double direction;
	private Melody attackMelody;
	private Melody defenceMelody;
	private UnitState state;
	private CubeControlledView view;
	private Player owner;
	private GameEngine gameEngine;
	private int power;
	private LifeView lifeView;
	private ArrayList<Monster> seenMonsters;
	private Monster target;
	private Monster previousTarget;

	public Unit(Player owner) {
		life = 15;
		this.state = new WaitingUState();
		this.owner = owner;
		gameEngine = owner.getGameEngine();
		view = new CubeControlledView(pos);
		lifeView = new LifeView(view);
		view.addChild(lifeView);
		size = view.getSize();
		gameEngine.getMap().add(view);
		attackMelody = new Melody();
		defenceMelody = new Melody();
		seenMonsters = new ArrayList<Monster>();
		target = null;

	}

	/**
	 * Renvoie le Player auquel est lie cet Unit
	 * 
	 * @return
	 */
	public Player getOwner() {
		return owner;
	}


	/**
	 * Retourne le cube physique auquel est associ� Unit
	 * 
	 * @return
	 */
	public Cube getCube() {
		return cube;

	}

	/**
	 * Methodes relatives a la vie de Unit
	 */
	public void increaseLife(double inc) {
		life = life + inc;
		lifeView.setLife(life);
	}

	public void decreaseLife(double dec) {
		life = life - dec;
		lifeView.setLife(life);
		if (life <= 0) {
			owner.removeUnit(this);
		}
	}

	public void setLife(double newLife) {
		life = newLife;
		lifeView.setLife(life);
	}

	public double getLife() {
		return life;
	}

	/**
	 * deplacement relatif de unit
	 * 
	 * @param dx
	 * @param dy
	 */
	public void translate(int dx, int dy) {
		pos.translate(dx, dy);
		view.translate(dx, dy);
	}

	/**
	 * deplacement absolu de unit
	 * 
	 * @param x
	 * @param y
	 */
	public void setLocation(int x, int y) {
		pos.move(x, y);
		view.setLocation(x, y);
	}

	/**
	 * deplacement absolu de unit
	 * 
	 * @param p
	 */
	public void setLocation(Point p) {
		pos.setLocation(p);
		view.setLocation(p);
	}

	/** retourne la position en x,y ou Point **/
	public double getX() {
		return pos.getX();
	}

	public double getY() {
		return pos.getY();
	}

	public Point getPos() {
		return pos;
	}

	/**
	 * M�thode relatives � l'angle d'ouverture de l'attaque
	 * 
	 * @param theta
	 * @param dTheta
	 */
	public void setAperture(double theta) {
		aperture = theta;
		view.setAperture(theta);
		view.setAngle(theta);
	}

	public void rotateAperture(double dTheta) {
		if ((aperture + dTheta >= 0) && (aperture + dTheta <= 360))
			aperture = aperture + dTheta;
		view.rotateAperture(dTheta);
		view.rotate(-dTheta);
	}

	public double getAperture() {
		return aperture;
	}

	/**
	 * Methode qui permet de calculer quels sont les monsttres visibles par Unit
	 * On considere que la seule raison qu'un Monster ne soit pas visble est
	 * qu'il y ait un mur qui le s�pare de Unit
	 */
	public void updtaeSeenMonsters() {
		seenMonsters = gameEngine.getMonsterList();
		ArrayList<Monster> removeMonsters = new ArrayList<Monster>();
		ArrayList<Wall> walls = gameEngine.getWalls();
		double xm = this.getPos().getX();
		double ym = this.getPos().getY();
		//System.out.println(unitList.size());
		for (Monster monster : seenMonsters) {
			double xu = monster.getPos().getX();
			double yu = monster.getPos().getY();
			for (Wall wall : walls) {
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
				if (((xi <= Math.max(xp1, xp2))
						&& (xi >= Math.min(xp1, xp2))
						&& (pos.distanceTo(new Point(xi, yi)) < pos
								.distanceTo(monster.getPos())))) {
					removeMonsters.add(monster);
					
				}
			}
		}
		for(Monster monster : removeMonsters){
			seenMonsters.remove(monster);
				
		}
	}
	
	public void updateTarget(){
		int targetNumber = (int) (direction/40); 
		int size = seenMonsters.size();
		previousTarget = target;
		target = null;
		if (size>0){
			if (targetNumber>=0) target = seenMonsters.get(targetNumber%size);
			else target = seenMonsters.get((size-Math.abs((targetNumber%size))-1));
		}
	}

	public ArrayList<Monster> getSeenMonsters() {
		return seenMonsters;
	}

	public void setSeenMonsters(ArrayList<Monster> seenMonsters) {
		this.seenMonsters = seenMonsters;
	}

	/**
	 * Methode relatives a la direction de l'attaque
	 * 
	 * @param theta
	 * @param dTheta
	 */
	public void setDirection(double theta) {
		direction = theta;
		view.setDirection(theta);
		view.setAngle(theta);
	}

	public void rotateDirection(double dTheta) {
		direction = direction + dTheta;
		view.rotateDirection(dTheta);
		view.rotate(dTheta);
	}

	public double getDirection() {
		return direction;
	}

	public int getPower() {
		return power;
	}

	public void setPower(int power) {
		this.power = power;
	}

	/**
	 * Simple rotation qui ne modifie aucun parametre
	 * 
	 * @param dTheta
	 */
	public void rotate(double dTheta) {
		view.rotate(dTheta);
	}

	/**
	 * setters et getter de UnitState
	 */
	public void setUStateToAngle() {
		this.state = new AngleUState();
	}

	public void setUStateToDirection() {
		this.state = new DirectionUState();
	}

	public void setUStateToFrozen() {
		this.state = new FrozenUState();
	}

	public void setUStateToMoving() {
		this.state = new MovingUState();
	}

	public void setUStateToPositionError() {
		this.state = new PositionErrorUState();
	}

	public void setUStateToSelect() {
		this.state = new SelectUState();
	}

	public void setUStateToWaiting() {
		this.state = new WaitingUState();
	}

	public UnitState getState() {
		return state;
	}

	public double getSize() {

		return size;
	}

	public DisplayableFather getView() {

		return view;
	}

	public LifeView getLifeView() {
		return lifeView;
	}

	public void setLifeView(LifeView lifeView) {
		this.lifeView = lifeView;
	}

	public Melody getAttackMelody() {
		return attackMelody;
	}

	public void setAttackMelody(Melody attackMelody) {
		this.attackMelody = attackMelody;
	}

	public Melody getDefenceMelody() {
		return defenceMelody;
	}

	public void setDefenceMelody(Melody defenceMelody) {
		this.defenceMelody = defenceMelody;
	}

	public Monster getTarget() {
		return target;
	}

	public void setTarget(Monster target) {
		this.target = target;
	}

	public Monster getPreviousTarget() {
		return previousTarget;
	}

	public void setPreviousTarget(Monster previousTarget) {
		this.previousTarget = previousTarget;
	}
	
	
	
}
