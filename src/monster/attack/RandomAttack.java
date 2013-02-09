package monster.attack;

import java.util.ArrayList;

import utilities.RandomPerso;

/**
 * Sous classe de AttackType qui cree des attaques de maniere aleatoire
 * @author Felix
 */

public class RandomAttack extends AttackType{
	private int strenght;
	
	public RandomAttack(int strenght){
		this.strenght = strenght;
	}
	
	/**
	 * Genere une attaque a une frequence aleatoire.
	 */
	@Override
	public ArrayList<int[]> result(){
		ArrayList<int[]> attackTable = new ArrayList<int[]>();
		int[] attack = {RandomPerso.entier(11),strenght};
		attackTable.add(attack);
		return attackTable;
	}
}