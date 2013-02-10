package monster.zoo;

import monster.Monster;
import monster.attack.ChooseDistance;
import monster.attack.FixedFrenquenceAttack;
import monster.move.RegularMove;

import org.lwjgl.util.ReadableColor;

import utilities.Point;
import views.monsters.SquareMonsterView;
import gameEngine.GameEngine;

public class TriangleMonster extends Monster {

	public TriangleMonster(float xStart, float yStart,ReadableColor color,GameEngine gameEngine) {
		super(xStart, yStart, gameEngine);
		this.choice = new ChooseDistance(this);
		this.attack = new FixedFrenquenceAttack(this, 6);
		this.move = new RegularMove(this, 200);
		view= new SquareMonsterView(new Point(xStart,yStart), color);
		gameEngine.getMap().add(view);
	}

}