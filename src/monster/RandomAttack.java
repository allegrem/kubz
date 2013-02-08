package monster;

import java.util.ArrayList;
import utilities.RandomPerso;

/**
 * Sous classe de AttackType qui cr�e des attaques de mani�re al�atoire
 * @author Felix
 */

public class RandomAttack extends AttackType{
	private int strenght;
	
	public RandomAttack(int strenght){
		this.strenght = strenght;
	}
	
	/**
	 * G�n�re une attaque � une fr�quence al�atoire.
	 */
	@Override
	public ArrayList<int[]> result(){
		ArrayList<int[]> attackTable = new ArrayList<int[]>();
		int[] attack = {RandomPerso.entier(11),strenght};
		attackTable.add(attack);
		return attackTable;
	}
}