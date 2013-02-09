package monster;

import monster.attack.ChooseDistance;
import monster.move.RandomMove;
import monster.move.RegularMove;

import org.lwjgl.util.ReadableColor;

import utilities.Point;
import views.monsters.TriangleMonsterView;
import gameEngine.GameEngine;

public class TriangleMonster extends Monster {

	public TriangleMonster(float xStart, float yStart,ReadableColor color,GameEngine gameEngine) {
		super(xStart, yStart, gameEngine);
		this.move = new RegularMove(this, 200);
		this.choice = new ChooseDistance(this);
		view= new TriangleMonsterView(new Point(xStart,yStart), color);
		gameEngine.getMap().add(view);
	}

}