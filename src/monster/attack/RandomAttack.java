package monster.attack;

import monster.Monster;


/**
 * Sous classe de AttackType qui cree des attaques de maniere aleatoire
 * @author Felix
 */

public class RandomAttack extends AttackType{
	private int strenght;
	
	
	public RandomAttack(Monster monster, int strenght) {
		super(monster);
		this.strenght = strenght;
	}
	
}