package monster.attack;

/**
 * Sous classe de AttackType qui cree des attaques de maniere aleatoire
 * @author Felix
 */

import monster.zoo.Monster;

public class RandomAttack extends AttackType {

	public RandomAttack(Monster monster) {
		super(monster);
		generateAttackMelody();
	}

	@Override
	/**
	 * A chaque fois, le monstre genere une nouvelle attaque, et ce sans prendre compte le passe
	 */
	public void generateAttackMelody() {
		generateAttackMelody();
	}

}