package monster;

import monster.attack.ChooseDistance;
import monster.move.RandomMove;
import monster.move.RegularMove;

import org.lwjgl.util.ReadableColor;

import utilities.Point;
import views.monsters.SquareMonsterView;
import gameEngine.GameEngine;

public class SquareMonster extends Monster {

	public SquareMonster(float xStart, float yStart,ReadableColor color, GameEngine gameEngine) {
		super(xStart, yStart, gameEngine);
		this.move = new RegularMove(this, 200);
		this.choice = new ChooseDistance(this);
		view= new SquareMonsterView(new Point(xStart,yStart), color);
		gameEngine.getMap().add(view);
	}

}
