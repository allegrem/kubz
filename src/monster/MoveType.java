package monster;

/**
 * Composante de Monster qui permet d'en gérer le mouvement
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
	}
	
	/**
	 * Le monstre se déplace vers une unité à la vitesse speed
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
	 * Le monstre se déplace dans direction aléatoire de norme speed, c'est une marche  
	 * aléatoire dont l'écart à la position initiale est en speed*sqrt(nbIterations) 
	 */		
	final void randMove(){
		double rand = Math.random();
		double xdir =2*(0.5 - rand)*speed; 	
		double ydir = Math.sqrt(1 - 4*(0.5-rand)*(0.5-rand))* speed;
		monster.getPos().move(xdir, ydir);
	}
}
