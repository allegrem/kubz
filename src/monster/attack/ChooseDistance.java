package monster.attack;

/**
 * Classe qui correspond au choix des potentiels des Unit vu par Monster
 * Ici le pot correspond juste a la distance de Unit a Monster
 * @author Felix
 */

import player.unit.Unit;
import monster.zoo.Monster;

public class ChooseDistance extends ChooseType {

	public ChooseDistance(Monster monster) {
		super(monster);
	}

	@Override
	/**
	 * Le critere pour le choix de la cible est ici uniquement la distance, le joueur le plus proche sera la cible 
	 */
	public int getPot(Unit unit) {
		return (int) -monster.getPos().distanceTo(unit.getPos());
	}
}