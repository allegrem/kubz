package monster.attack;

/**
 * @author Felix
 */

import player.unit.*;
import synthesis.Sound;
import synthesis.fmInstruments.FmInstruments3Params;
import synthesis.fmInstruments.WoodInstrument;

import midisynthesis.Melody;
import monster.zoo.Monster;

import views.attacks.SinusoidalAttackView;

public abstract class AttackType {
	protected Monster monster;
	protected Melody melody;
	protected int power = 100; 
	protected int distance = 100;
	
	public AttackType(Monster monster) {
		this.monster = monster;
		FmInstruments3Params instrument = WoodInstrument
				.getFmInstruments3Params();
		instrument.random();
		this.melody = new Melody();
	}


	public void attack(Unit unit) {
		if (monster.getCible() != null) {
			double oppose = -(monster.getCible().getX() - monster.getX());
			double adjacent = monster.getCible().getY() - monster.getY();
			SinusoidalAttackView attack = new SinusoidalAttackView(20, 180* Math.atan2(oppose, adjacent) / Math.PI, power,monster.getView());
			monster.getView().addChild(attack);
			double dTempo = Math.exp(Math.abs(melody.getTempo()-monster.getCible().getMelody().getTempo()));
			double dTune = Math.exp(Math.abs(melody.getTune()-monster.getCible().getMelody().getTune()));
			double dParameter = Math.exp(Math.abs(melody.getParameter()-monster.getCible().getMelody().getParameter()));
			int dInstrum;
			if(melody.getInstrument().equals(monster.getCible().getMelody().getInstrument())) dInstrum = 1;
			else dInstrum = 0;
			int dPattern;
			if(melody.getPattern().equals(monster.getCible().getMelody().getPattern())) dPattern = 1;
			else dPattern = 0;
			int degats = (int) ((int) power* dPattern*dInstrum*dTune*dTempo*dParameter);
			//sound.playToSpeakers(); //A NE PAS ENLEVER QUAND JE FAIS MON NAZI SUR LES COMMENTAIRES
			if (distance > monster.getPos().distanceTo(monster.getCible().getPos())) {
				monster.getCible().decreaseLife(degats);
			}
		}

	}

}
