package monster.attack;

import monster.zoo.Monster;


/**
 * Sous classe de AttackType qui cree des attaques de maniere aleatoire
 * @author Felix
 */

public class RandomAttack extends AttackType{
	
	
	public RandomAttack(Monster monster) {
		super(monster);
	}
	
}