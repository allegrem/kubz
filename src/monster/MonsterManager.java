package monster;
import java.util.ArrayList;

public class MonsterManager {
	private ArrayList<Monster> monsterList;
	
	public void add(Monster monster){
		monsterList.add(monster);	
	}
	public void remove(Monster monster){
		monsterList.remove(monster);
	}
	public ArrayList<Monster> getMonsterList() {
		return monsterList;
	}
	public void setMonsterList(ArrayList<Monster> monsterList) {
		this.monsterList = monsterList;
	}
	
}
