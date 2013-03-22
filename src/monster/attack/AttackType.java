package monster.attack;

/**
 * @author Felix
 */

import player.unit.*;
import midisynthesis.Melody;
import monster.zoo.Monster;

import views.attacks.SinusoidalAttackView;

public abstract class AttackType {
	protected Monster monster;
	protected Melody attackMelody;
	protected int power = 100; 
	protected int distance = 100;
	
	public AttackType(Monster monster) {
		this.monster = monster;
		this.attackMelody = new Melody();
		
	}


	public void attack(Unit unit) {
		if (monster.getCible() != null) {
			double oppose = -(monster.getCible().getX() - monster.getX());
			double adjacent = monster.getCible().getY() - monster.getY();
			SinusoidalAttackView attack = new SinusoidalAttackView(20, 180* Math.atan2(oppose, adjacent) / Math.PI, power,monster.getView());
			monster.getView().addChild(attack);
			
			//les degats sont calcules comme une fonction de la ressemblance entre la melody du monstre et celle du joueur
			//ici on va calculer cette ressemblance
			double dTempo = Math.exp(Math.abs(attackMelody.getTempo()-monster.getCible().getDefenceMelody().getTempo()));
			double dTune = Math.exp(Math.abs(attackMelody.getTune()-monster.getCible().getDefenceMelody().getTune()));
			double dParameter = Math.exp(Math.abs(attackMelody.getParameter()-monster.getCible().getDefenceMelody().getParameter()));
			int dInstrum;
			if(attackMelody.getInstrument().equals(monster.getCible().getDefenceMelody().getInstrument())) dInstrum = 1;
			else dInstrum = 0;
			int dPattern;
			if(attackMelody.getPattern().equals(monster.getCible().getDefenceMelody().getPattern())) dPattern = 1;
			else dPattern = 0;
			int degats = (int) ((int) power* dPattern*dInstrum*dTune*dTempo*dParameter);
			//on inflige a present les degats au joueur
			if (distance > monster.getPos().distanceTo(monster.getCible().getPos())) {
				monster.getCible().decreaseLife(degats);
			}
		}

	}

}
