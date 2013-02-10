package monster.zoo;

import org.lwjgl.util.ReadableColor;

import utilities.Point;
import views.monsters.SquareMonsterView;
import gameEngine.GameEngine;
import monster.Monster;
import monster.attack.ChooseDistance;
import monster.attack.FixedFrenquenceAttack;
import monster.move.RegularMove;

public class AttackMonster extends Monster{

	public AttackMonster(float xStart, float yStart,ReadableColor color, GameEngine gameEngine) {
		super(xStart, yStart, gameEngine);
		this.choice = new ChooseDistance(this);
		this.attack = new FixedFrenquenceAttack(this, 6, 30);
		this.move = new RegularMove(this, 200);
		view= new SquareMonsterView(new Point(xStart,yStart), color);
		gameEngine.getMap().add(view);	
	}


}
