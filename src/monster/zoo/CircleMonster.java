package monster.zoo;

/**
 * Monstre qui attaque le joueur avec le moins de vie en optimisant son atttaque au cours du temps
 * @author Felix
 * @author Paul
 */

import monster.RandomDefence;
import monster.attack.ChooseLife;
import monster.attack.SmartAttack;
import monster.move.RegularMove;
import org.lwjgl.util.ReadableColor;
import utilities.Point;
import views.informationViews.LifeView;
import views.monsters.CircleMonsterView;
import gameEngine.GameEngine;

public class CircleMonster extends Monster {

	public CircleMonster(float xStart, float yStart,ReadableColor color,GameEngine gameEngine) {
		super(xStart, yStart, gameEngine);
		this.choice = new ChooseLife(this);
		this.defence = new RandomDefence();
		this.attack = new SmartAttack(this);
		this.move = new RegularMove(this, 200);
		view= new CircleMonsterView(new Point(xStart,yStart), color);
		gameEngine.getMap().add(view);	
		lifeView=new LifeView(view);
		view.addChild(lifeView);
	}

}
