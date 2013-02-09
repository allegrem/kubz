package monster;

import monster.move.RandomMove;
import gameEngine.GameEngine;

public class RandMoveMonster extends Monster {

	public RandMoveMonster(float xStart, float yStart, GameEngine gameEngine) {
		super(xStart, yStart, gameEngine);
		this.move = new RandomMove(this, 20);
	}


}
