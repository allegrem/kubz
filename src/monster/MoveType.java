package monster;

/**
 * Composante de Monster qui permet d'en gérer le mouvement
 */

import unit.Unit;

public abstract class MoveType {
	final Monster monster;
	final double speed;
	final int width;
	final int height;
	


	public MoveType(Monster monster, double speed) {
		super();
		this.monster = monster;
		this.speed = speed;
		width = monster.gameEngine.getWidth();
		height = monster.gameEngine.getHeight();
	}

	public void move() {		
	}
	
	/**
	 * Le monstre se déplace vers une unité à la vitesse speed
	 * Pas de condition pour se déplacer car
	 * - ne croise pas de mur (sinon ne la vois pas)
	 * - ne sort pas de la carte (sinon le joueur est hors de la acrte et c'est pas possible)
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
		if ((monster.getX()+xdir>0)&&(monster.getX()+xdir<width)
				&&(monster.getY()+ydir>0)&&(monster.getX()+xdir<height)){
			monster.getPos().move(xdir, ydir);
		}		
	}
}
