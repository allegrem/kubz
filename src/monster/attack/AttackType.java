package monster.attack;
import java.util.ArrayList;

import player.unit.*;

import monster.Monster;

import views.attacks.SinusoidalAttackView;

public abstract class AttackType {	
	protected Monster monster;
	
	public AttackType(Monster monster){
		this.monster = monster;
	}
	
	public void attack(Unit unit){
		double oppose = -(monster.getCible().getX() -monster.getX());
		double adjacent = monster.getCible().getY() -monster.getY();
		SinusoidalAttackView attack = new SinusoidalAttackView(20,180*Math.atan2(oppose,adjacent)/Math.PI, 100, monster.getView());
		this.monster.getView().addChild(attack);
	}	
	public ArrayList<int[]> result(){
		return null;	
	}
 }
