package monster.attack;

/**
 * Classe abstraite qui correspond au choix des potentiels des Unit vu par Monster
 * @author Felix
 */

import player.unit.Unit;
import monster.zoo.Monster;

public abstract class ChooseType {
	
	protected Monster monster;
		
	public ChooseType(Monster monster) {
		this.monster = monster;
	}

	public  abstract int getPot(Unit unit);
	
	
}
