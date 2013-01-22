package monster;

import generalite.Point;

import java.util.*;
import unit.Unit;

public class Monster {
	
	private Unit cible;
	private ArrayList<Unit> seenUnits; 
	private Point pos;
	private int speed;
	
	public Monster(int xStart, int yStart){
		this.pos = new Point(xStart, yStart);
		this.cible = null;
		this.seenUnits = new ArrayList<Unit>();
	}
	
	private void moveTo(Unit unit){	
		int xdiff = unit.getPos().getX() - this.pos.getX();
		int ydiff = unit.getPos().getY() - this.pos.getY();
		double dx = speed*xdiff/Math.sqrt(xdiff*xdiff + ydiff*ydiff);
		double dy = speed*ydiff/Math.sqrt(xdiff*xdiff + ydiff*ydiff);
		pos.move(dx, dy);
	}
	
	private void setPotList(){
		for(Unit unit : seenUnits){
			setPot(unit);
		}
	}
	
	private void setPot(Unit unit){		
	}
	
	private Unit getHighestpot(){
		return null;
	}
	
	private void setCible(){
		this.cible = getHighestpot();
	}
	
	public Unit getCible(){
		return cible;
	}
	
	private void attacks(Unit unit){
	}
	
	private void setSeenUnits(ArrayList<Unit> unitList){
		for(Unit unit : unitList){
			Point unitPos = unit.getPos();
			//impléneter la boucle sur la position des murs
			seenUnits.add(unit);
		}
	}
	
	public void move(){
		if (cible!=null){
			moveTo(cible);
		}
		else{
			double rand = Math.random();
			double xdir =2*(0.5 - rand)*speed;  /* direction aléatoire de norme speed, c'est une marche 
			aléatoire dont l'écart à la position initiale est en speed*sqrt(nbIterations) */			
			double ydir = Math.sqrt(1 - 4*(0.5-rand)*(0.5-rand))* speed;
			pos.move(xdir, ydir);
			
		}
	}
	
	public void attacks(){
		if(cible != null){
			attacks(cible);
		}
		else return;
	}

}
