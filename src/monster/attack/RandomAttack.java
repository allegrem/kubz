package monster.attack;

/**
 * 
 * @author Felix
 */

import player.unit.Unit;
import midisynthesis.instruments.InstrumentLibrary;
import midisynthesis.patterns.MidiPatternsLibaray;
import monster.zoo.Monster;

/**
 * Sous classe de AttackType qui cree des attaques de maniere aleatoire
 * 
 * @author Felix
 */

public class RandomAttack extends AttackType {

	public RandomAttack(Monster monster) {
		super(monster);
		generateAttackMelody();
	}

	@Override
	public void generateAttackMelody() {
		generateAttackMelody();
	}

}