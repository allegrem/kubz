package monster.attack;

/**
 * Classe abstraite qui correspond au choix des potentiels des Unit vu par Monster
 */

import monster.Monster;
import unit.Unit;

public abstract class ChooseType {
	
	protected Monster monster;
		
	public ChooseType(Monster monster) {
		this.monster = monster;
	}

	public int getPot(Unit unit){
		return 0;
	}
	
}
