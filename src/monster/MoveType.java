package monster;

/**
 * Composante de Monster qui permet d'en g�rer le mouvement
 * @author Felix
 */

import unit.Unit;

public class MoveType {
	final Monster monster;
	final double speed;
	


	public MoveType(Monster monster, double speed) {
		super();
		this.monster = monster;
		this.speed = speed;
	}

	public void move() {	
		randMove();
	}
	
	/**
	 * Le monstre se d�place vers une unit� � la vitesse speed
	 * @param unit
	 */
	final void moveTo(Unit unit){	
		double xdiff = unit.getPos().getX() - monster.getPos().getX();
		double ydiff = unit.getPos().getY() - monster.getPos().getY();
		double dx = speed*xdiff/Math.sqrt(xdiff*xdiff + ydiff*ydiff);
		double dy = speed*ydiff/Math.sqrt(xdiff*xdiff + ydiff*ydiff);
		monster.getPos().move(dx, dy);
	}	
	
	/** 
	 * Le monstre se d�place dans direction al�atoire de norme speed, c'est une marche  
	 * al�atoire dont l'�cart � la position initiale est en speed*sqrt(nbIterations) 
	 */		
	final void randMove(){
		double rand1 = Math.random();
		double rand2 = Math.random();
		double xdir =2*(0.5 - rand1)*speed; 	
		double ydir =2*(0.5 - rand1)*speed; 
		monster.translate((int)xdir, (int)ydir);
	}
}
