package monster.attack;

/**
 * @author Felix
 */

import java.util.ArrayList;
import player.unit.*;
import synthesis.Sound;
import synthesis.fmInstruments.FmInstruments3Params;
import synthesis.fmInstruments.TwoOscFmInstrument;

import monster.Monster;

import views.attacks.SinusoidalAttackView;

public abstract class AttackType {	
	protected Monster monster;
	private final Sound sound;
	
	public AttackType(Monster monster){
		this.monster = monster;
		FmInstruments3Params instrument = TwoOscFmInstrument.getFmInstruments3Params();
		sound = new Sound(instrument, 3f);
	}
	
	public void attack(Unit unit){
		double oppose = -(monster.getCible().getX() -monster.getX());
		double adjacent = monster.getCible().getY() -monster.getY();
		SinusoidalAttackView attack = new SinusoidalAttackView(20,180*Math.atan2(oppose,adjacent)/Math.PI, 100, monster.getView());
		monster.getView().addChild(attack);
		int degats = sound.filter(monster.getCible().getOwner().getShield()).getDegats();
		monster.getCible().decreaseLife(degats);
	}	
	public ArrayList<int[]> result(){
		return null;	
	}
 }
