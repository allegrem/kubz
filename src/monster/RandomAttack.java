package monster;

import java.util.ArrayList;

import utilities.RandomPerso;

/**
 * Sous classe de AttackType qui crée des attaques de manière aléatoire
 * @author Felix
 */

public class RandomAttack extends AttackType{
	private int strenght;
	
	public ArrayList<int[]> result(){
		ArrayList<int[]> attackTable = new ArrayList<int[]>();
		int[] attack = {RandomPerso.entier(1000),strenght};
		attackTable.add(attack);
		return attackTable;
	}
	

}
