package monster.move;

/**
 * Composante de Monster qui permet d'en g�rer le mouvement
 */

import player.unit.Unit;
import monster.zoo.Monster;

public abstract class MoveType {
	final Monster monster;
	final int speed;
	final int width;
	final int height;

	public MoveType(Monster monster, int speed) {
		super();
		this.monster = monster;
		this.speed = speed;
		width = monster.getGameEngine().getWidth();
		height = monster.getGameEngine().getHeight();
	}

	public void move() {

	}

	/**
	 * Le monstre se deplace vers une unit� � la vitesse speed Pas de
	 * condition pour se d�placer car - ne croise pas de mur (sinon ne la vois
	 * pas) - ne sort pas de la carte (sinon le joueur est hors de la acrte et
	 * c'est pas possible)
	 * 
	 * @param unit
	 */
	final void moveTo(Unit unit) {
		double dx;
		double dy;
		double xdiff = unit.getPos().getX() - monster.getPos().getX();
		double ydiff = unit.getPos().getY() - monster.getPos().getY();
		double d = Math.sqrt(xdiff * xdiff + ydiff * ydiff);
		double dmonster = (monster.getView().getSize() * Math.sqrt(2) / 2);
		double dunit = unit.getView().getSize() * Math.sqrt(2) / 2;
		double theta = Math.atan2(ydiff, xdiff);
		double distance = d-dunit-dmonster;
		if (distance >= speed ) {
			dx = speed * xdiff / d;
			dy = speed * ydiff / d;
		} else {
			dx = distance*Math.cos(theta);
			dy = distance*Math.sin(theta);
		}
		monster.translate(dx, dy);

	}

	/**
	 * Le monstre se d�place dans direction al�atoire de norme speed, c'est
	 * une marche al�atoire dont l'�cart � la position initiale est en
	 * speed*sqrt(nbIterations)
	 */
	final void randMove() {
		double rand = 2 * Math.PI * Math.random();
		double xdir = speed * Math.cos(rand);
		double ydir = speed * Math.sin(rand);
		System.out.println(xdir + "  " + ydir);
		double size = monster.getSize() * Math.sqrt(2) / 2;
		if ((monster.getX() + size + xdir) > 0
				&& (monster.getX() + size + xdir) < width
				&& (monster.getY() + size + ydir) > 0
				&& (monster.getY() + size + ydir) < height) {
			monster.translate(xdir, ydir);
		}
	}
}
