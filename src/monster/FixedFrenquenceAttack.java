package monster;

/**
 * Monstre qui attaque tout le temps à la même fréquence de toute sa puissance
 * @author Felix
 */
import java.util.ArrayList;

public class FixedFrenquenceAttack {
	
	private int strenght;
	private int frequency;
	
	public FixedFrenquenceAttack(int frequency, int strenght){
		this.frequency = frequency;
		this.strenght = strenght;
	}
	
	public ArrayList<int[]> result(){
		ArrayList<int[]> attackTable = new ArrayList<int[]>();
		int[] attack = {frequency,strenght};
		attackTable.add(attack);
		return attackTable;
	}
	
}
	

