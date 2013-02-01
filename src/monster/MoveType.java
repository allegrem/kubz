package monster;

import unit.Unit;

public class MoveType {
	final Monster monster;
	final int speed;
	


	public MoveType(Monster monster, int speed) {
		super();
		this.monster = monster;
		this.speed = speed;
	}

	public void move() {		
	}
	
	final void moveTo(Unit unit){	
		int xdiff = unit.getPos().getX() - monster.getPos().getX();
		int ydiff = unit.getPos().getY() - monster.getPos().getY();
		double dx = speed*xdiff/Math.sqrt(xdiff*xdiff + ydiff*ydiff);
		double dy = speed*ydiff/Math.sqrt(xdiff*xdiff + ydiff*ydiff);
		monster.getPos().move(dx, dy);
	}	
	final void randMove(){
		double rand = Math.random();
		double xdir =2*(0.5 - rand)*speed;  /* direction al�atoire de norme speed, c'est une marche 
		al�atoire dont l'�cart � la position initiale est en speed*sqrt(nbIterations) */			
		double ydir = Math.sqrt(1 - 4*(0.5-rand)*(0.5-rand))* speed;
		monster.getPos().move(xdir, ydir);
	}
}
