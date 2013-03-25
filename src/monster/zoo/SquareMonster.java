package monster.zoo;

/**
 * 
 * @author Felix
 * @author Paul
 */

import midisynthesis.patterns.MidiPattern1;
import monster.attack.ChooseDistance;
import monster.attack.FixedFrenquenceAttack;
import monster.move.RegularMove;
import org.lwjgl.util.ReadableColor;

import utilities.Point;
import views.informationViews.LifeView;
import views.monsters.SquareMonsterView;
import gameEngine.GameEngine;

public class SquareMonster extends Monster {

	public SquareMonster(float xStart, float yStart,ReadableColor color, GameEngine gameEngine) {
		super(xStart, yStart, gameEngine);
		this.choice = new ChooseDistance(this);
		this.attack = new FixedFrenquenceAttack(this, 1, 1, 1, new MidiPattern1(), new midisynthesis.instruments.Bell());
		this.move = new RegularMove(this, 200);
		view= new SquareMonsterView(new Point(xStart,yStart), color);
		gameEngine.getMap().add(view);	
		lifeView=new LifeView(view);
		view.addChild(lifeView);
	}
}
