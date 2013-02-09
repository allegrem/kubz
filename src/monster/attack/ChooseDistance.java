package monster.attack;

/**
 * Classe qui correspond au choix des potentiels des Unit vu par Monster
 * Ici le pot correspond juste a la distance de Unit a Monster
 */

import monster.Monster;
import unit.Unit;

public class ChooseDistance extends ChooseType{
	

	public ChooseDistance(Monster monster) {
		super(monster);
	}

	@Override
	public int getPot(Unit unit){
		return (int) monster.getPos().distanceTo(unit.getPos());
	}
}
