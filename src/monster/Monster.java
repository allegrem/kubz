package monster;

import generalite.Point;
import views.*;

import java.util.*;
import unit.Unit;

public class Monster {
	
	private AttackType attack;
	private DefenceType defence;
	private MoveType move;
	private LifeType life;
	private Unit cible;
	private ArrayList<Unit> seenUnits; 
	private Point pos;
	private int speed;
	
	public Monster(int xStart, int yStart){
		this.pos = new Point(xStart, yStart);
		this.cible = null;
		this.seenUnits = new ArrayList<Unit>();
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
		attack.attack(unit);
	}	
	private void setSeenUnits(ArrayList<Unit> unitList){
		for(Unit unit : unitList){
			Point unitPos = unit.getPos();
			//impléneter la boucle sur la position des murs
			seenUnits.add(unit);
		}
	}
	public void move(){
		move.move();
	}	
	public void attacks(){
		if(cible != null){
			attacks(cible);
		}
		else return;
	}
	public Point getPos(){
		return pos;
	}
}
