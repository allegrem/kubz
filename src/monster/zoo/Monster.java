package monster.zoo;

/**
 * Classe qui sert de modele e MonsterView
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
	 * Differents xxxxType qui permettent de generer differents types de
	 * monstres de facon modulaire et actions dans le modele
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
	protected DefenceType defence;

	/**
	 * cree un nouveau monstre e la position (xStart,yStart)
	 * 
	 * @param xStart
	 * @param yStart
	 */
	public Monster(float xStart, float yStart, GameEngine gameEngine) {

		defence = new DefenceType();
		this.pos = new Point(xStart, yStart);
		this.cible = null;
		this.seenUnits = new ArrayList<Unit>();
		this.setGameEngine(gameEngine);
		move = new RandomMove(this, 500);
		this.life = 10;

	}

	public void increaseLife(double inc) {
		life = life + inc;
		lifeView.setLife(life);
	}

	public void decreaseLife(double dec) {
		life = life - dec;
		lifeView.setLife(life);
		if (life < 0) {
			gameEngine.getMonsterList().remove(this);
			view.removeChild(lifeView);
			gameEngine.getMap().remove(view);
			this.view = null;
		}
	}

	public void setLife(double newLife) {
		life = newLife;
		lifeView.setLife(life);
	}

	public double getLife() {
		return life;
	}

	/** Partie qui gere l'attaque du monstre */

	/**
	 * Methode qui permet de determiner le potentiel de chaque unite, l'unite
	 * ayant une caracterisque precise sera la cible du monstre (ex vie,
	 * distance)
	 */

	private int getPot(Unit unit) {
		return choice.getPot(unit);

	}

	/**
	 * parcours la liste des units vues et en sort l'unit avec le meilleur
	 * potentiel.
	 * 
	 * @return
	 */

	private void setCible() {
		Unit newCible = null;
		updateSeenUnits();
		int pot = Integer.MIN_VALUE;
		for (Unit unit : seenUnits) {
			if (getPot(unit) > pot) {
				pot = getPot(unit);
				newCible = unit;
			}
		}
		cible = newCible;
	}

	private void updateSeenUnits() {
		seenUnits.clear();
		seenUnits.addAll(gameEngine.getUnitList());
		ArrayList<Unit> removeUnits = new ArrayList<Unit>();
		ArrayList<Wall> walls = gameEngine.getWalls();
		double xm = this.getPos().getX();
		double ym = this.getPos().getY();

		for (Unit unit : seenUnits) {
			for (Wall wall : walls) {
				// Construction de la liste des angles entre l'horizontale, le
				// montre et les extremites du mur
				double xp1 = wall.getExtremity1().getX();
				double yp1 = wall.getExtremity1().getY();
				double xp2 = wall.getExtremity2().getX();
				double yp2 = wall.getExtremity2().getY();
				double theta1 = 180 * Math.atan2(yp1 - ym, xp1 - xm) / Math.PI;
				double theta2 = 180 * Math.atan2(yp2 - ym, xp2 - xm) / Math.PI;
				double aperture = theta1 - theta2;
				// contruction de l'angle (extrem1,monstre,unit)
				double xu = unit.getX();
				double yu = unit.getY();
				double thetau = 180 * Math.atan2(yu - ym, xu - xm) / Math.PI;
				boolean inAngle = false;

				// on regarde si la droite qui va du monstre vers le joueur
				// rencontre un mur
				if ((Math.abs(aperture) < 180)
						&& (inRange(thetau, theta1, theta2))) {
					inAngle = true;
					System.out.println("tata");
				} else if ((Math.abs(aperture) > 180)
						&& (!inRange(thetau, theta1, theta2))) {
					inAngle = true;
					System.out.println("tata");

				}

				// Maintenant qu'on sait qu'il y a ou pas un mur dans la zone ou
				// est le joueur, on regarde si ce dernier est devant ou
				// derriere le mur
				if (inAngle) {
					double xi = (ym - yp1 - xm * (yu - ym) / (xu - xm) + xp1
							* (yp2 - yp1) / (xp2 - xp1))
							/ ((yp2 - yp1) / (xp2 - xp1) - (yu - ym)
									/ (xu - xm));
					double yi = ym + (xi - xm) * (yu - ym) / (xu - xm);
					if ((pos.distanceTo(new Point(xi, yi)) < pos
							.distanceTo(unit.getPos()))) {
						removeUnits.add(unit);
					}
				}
			}

		}

		// A present on enleve tous les joueurs qu'on ne voit pas de la liste
		// des joueurs vus
		for (Unit unit : removeUnits) {
			seenUnits.remove(unit);
		}

	}

	public Unit getCible() {
		return cible;
	}

	/** Actions liees a la position **/

	/**
	 * lance la procedure de mouvement du monstre
	 */
	private void move() {
		move.move();
	}
	
	private void attack(){
		attack.attack();
	}

	public DefenceType getDefence() {
		return defence;
	}

	public void setDefence(DefenceType defence) {
		this.defence = defence;
	}

	/**
	 * deplacement relatif du monstre
	 * 
	 * @param dx
	 * @param dy
	 */
	public void translate(double dx, double dy) {
		pos.translate(dx, dy);
		view.translate(dx, dy);
	}

	/**
	 * deplacement absolu du monstre
	 * 
	 * @param x
	 * @param y
	 */
	public void setLocation(int x, int y) {
		pos.move(x, y);
		view.setLocation(x, y);
	}

	/**
	 * deplacement absolu du monstre
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

	public void act() {
		setCible();
		move();
		attack();
		try {
			Thread.sleep(100);
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

	public boolean inRange(double x, double range1, double range2) {
		boolean result = false;
		if ((x >= Math.min(range1, range2)) && (x <= Math.max(range1, range2)))
			result = true;
		return result;
	}

}
