package monster;

public class RandomMove extends MoveType{

	public RandomMove(Monster monster, double speed) {
		super(monster, speed);
		// TODO Auto-generated constructor stub
	}
	
	/**
	 * Son seul type de mouvement est de se d�placer vers une direction al�atoire
	 */
	public void move(){
		randMove();
	}
	
}
