package monster.attack;

/**
 * Classe qui correspond au choix des potentiels des Unit vu par Monster
 * Ici le pot correspond juste a la vie de Unit
 * @author Felix
 * 
 */

import player.unit.Unit;
import monster.zoo.Monster;

public class ChooseLife extends ChooseType{

	public ChooseLife(Monster monster) {
		super(monster);
	}
	
	@Override
	/**
	 * Le critere pour le choix de la cibble est ici uniquement la vie, le joueur le plus faible sera la cible
	 */
	public int getPot(Unit unit){
		return (int)  unit.getLife();
	}
}
