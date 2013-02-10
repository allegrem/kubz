package monster.attack;

/**
 * Monstre qui attaque tout le temps à la même fréquence de toute sa puissance
 * @author Felix
 */
import java.util.ArrayList;

import monster.Monster;

public class FixedFrenquenceAttack extends AttackType{
	
	private int strenght;
	private int frequency;
	
	
	
	public FixedFrenquenceAttack(Monster monster, int strenght, int frequency) {
		super(monster);
		this.frequency = frequency;
		this.strenght = strenght;				
	}



	@Override
	public ArrayList<int[]> result(){
		ArrayList<int[]> attackTable = new ArrayList<int[]>();
		int[] attack = {frequency,strenght};
		attackTable.add(attack);
		return attackTable;
	}
	
}
	

