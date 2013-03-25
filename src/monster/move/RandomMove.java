package monster.move;

/**
 * 
 * @author Felix
 */

import monster.zoo.Monster;

public class RandomMove extends MoveType{

	public RandomMove(Monster monster, int speed) {
		super(monster, speed);
	}
	
	/**
	 * Son seul type de mouvement est de se d�placer vers une direction al�atoire
	 */
	@Override
	public void move(){
		randMove();
	}
	
}
