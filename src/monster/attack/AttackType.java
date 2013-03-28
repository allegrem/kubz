package monster.attack;

/**
 * @author Felix
 */

import player.unit.*;
import midisynthesis.Melody;
import midisynthesis.instruments.InstrumentLibrary;
import midisynthesis.patterns.MidiPatternsLibrary;
import monster.zoo.Monster;

import views.attacks.SinusoidalAttackView;

public abstract class AttackType {
	protected Monster monster;
	protected Melody attackMelody;
	protected int power = 100;
	protected int distance = 100;
	protected Unit previousTarget = null;

	public AttackType(Monster monster) {
		this.monster = monster;
		this.attackMelody = new Melody();

	}

	public void attack() {
		generateAttackMelody();
		if (monster.getCible() != null) {
			double oppose = -(monster.getCible().getX() - monster.getX());
			double adjacent = monster.getCible().getY() - monster.getY();
			SinusoidalAttackView attack = new SinusoidalAttackView(20, 180
					* Math.atan2(oppose, adjacent) / Math.PI, power,
					monster.getView());
			monster.getView().addChild(attack);

			// les degats sont calcules comme une fonction de la ressemblance
			// entre la melody du monstre et celle du joueur
			// ici on va calculer cette ressemblance
			double dTempo = Math.exp(Math.abs(attackMelody.getTempo()
					- monster.getCible().getDefenceMelody().getTempo()));
			double dTune = Math.exp(Math.abs(attackMelody.getTune()
					- monster.getCible().getDefenceMelody().getTune()));
			double dParameter = Math.exp(Math.abs(attackMelody.getParameter()
					- monster.getCible().getDefenceMelody().getParameter()));
			int dInstrum;
			if (attackMelody.getInstrument().equals(
					monster.getCible().getDefenceMelody().getInstrument()))
				dInstrum = 1;
			else
				dInstrum = 0;
			int dPattern;
			if (attackMelody.getPattern().equals(
					monster.getCible().getDefenceMelody().getPattern()))
				dPattern = 1;
			else
				dPattern = 0;
			int degats = (int) ((int) power * dPattern * dInstrum * dTune
					* dTempo * dParameter);
			// on inflige a present les degats au joueur
			if (distance > monster.getPos().distanceTo(
					monster.getCible().getPos())) {
				monster.getCible().decreaseLife(degats);
			}
		}

	}

	protected void generateAttackMelody() {
	}

	protected void generateRandomAttackMelody() {
		// generation aleatoire d'une melody
		int tune = (int) (40 + 60 * Math.random());
		int parameter = (int) (127 * Math.random());
		int tempo = (int) (40 + 120 * Math.random());
		int pattern = (int) (10 * Math.random());
		int instrum = (int) (12 * Math.random());
		attackMelody.setTune(tune);
		attackMelody.setTempo(tempo);
		attackMelody.setParameter(parameter);
		for (int i = 0; i < instrum; i++)
			try {
				attackMelody.setInstrument(InstrumentLibrary
						.getNextInstrument(attackMelody.getInstrument()));
			} catch (InstantiationException | IllegalAccessException e) {
				e.printStackTrace();
			}
		for (int j = 0; j < pattern; j++)
			try {
				attackMelody.setPattern(MidiPatternsLibrary
						.getNextPattern(attackMelody.getPattern()));
			} catch (InstantiationException | IllegalAccessException e1) {
				e1.printStackTrace();
			}
	}

}
