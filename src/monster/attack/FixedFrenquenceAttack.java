package monster.attack;

/**
 * Monstre qui attaque tout le temps � la m�me fr�quence de toute sa puissance
 * @author Felix
 */

import monster.zoo.Monster;

public class FixedFrenquenceAttack extends AttackType {


	public FixedFrenquenceAttack(Monster monster) {
		super(monster);
		generateRandomAttackMelody();
	}

	@Override
	/**
	 * La melody n'est jamais modifiee
	 */
	protected void generateAttackMelody() {
		// TODO Auto-generated method stub
		
	}
	
	

}
