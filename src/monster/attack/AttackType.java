package monster.attack;
import java.util.ArrayList;

import monster.Monster;

import unit.*;
import views.attacks.SinusoidalAttackView;

public abstract class AttackType {	
	protected Monster monster;
	
	public AttackType(Monster monster){
		this.monster = monster;
	}
	
	public void attack(Unit unit){
		double adjacent = monster.getCible().getX() -monster.getX();
		double oppose = -(monster.getCible().getY() -monster.getY());
		double hypothenus = Math.sqrt(adjacent*adjacent + oppose*oppose);
		SinusoidalAttackView attack = new SinusoidalAttackView(20, -360*Math.atan2(oppose/hypothenus,adjacent/hypothenus)/Math.PI, 100, monster.getView());
		this.monster.getView().addChild(attack);
	}	
	public ArrayList<int[]> result(){
		return null;	
	}
 }