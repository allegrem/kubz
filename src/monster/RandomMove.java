package monster;

public class RandomMove extends MoveType{

	public RandomMove(Monster monster, double speed) {
		super(monster, speed);
		// TODO Auto-generated constructor stub
	}
	
	/**
	 * Son seul type de mouvement est de se déplacer vers une direction aléatoire
	 */
	public void move(){
		randMove();
	}
	
}
